package com.example.yourday.presentation.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.yourday.model.FavQuote
import com.example.yourday.model.Quote
import com.example.yourday.usecase.GetRandomQuotesCase
import com.example.yourday.usecase.InsertFavUseCase
import com.example.yourday.utils.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject


data class QuoteUiState(
    val quote: Quote? = null,
    val isLoading: Boolean = false,
    val error: String? = null
)
@HiltViewModel
class HomeViewModel @Inject constructor(
    private val getRandomQuotesCase: GetRandomQuotesCase,
    private val insertFavUseCase: InsertFavUseCase
): ViewModel() {

    private val _state = MutableStateFlow(QuoteUiState())
    val state: StateFlow<QuoteUiState> = _state

    fun fetchQuote() {
        viewModelScope.launch {
            getRandomQuotesCase().collect { result ->
                when(result) {
                    is Resource.Loading -> {
                        _state.value = QuoteUiState(isLoading = true)
                    }
                    is Resource.Success -> {
                        _state.value = QuoteUiState(quote = result.data)
                    }
                    is Resource.Error -> {
                        _state.value = QuoteUiState(error = result.message)
                    }
                }

            }

        }
    }

    fun addToFavorites(quote: Quote) {
        viewModelScope.launch {
            val myFavquote = FavQuote(
                text = quote.text,
                author = quote.author ?: "Unknown"
            )
            insertFavUseCase(myFavquote)
        }

    }

}