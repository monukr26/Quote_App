package com.example.yourday.presentation.favorite

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.yourday.model.FavQuote
import com.example.yourday.usecase.DeleteFavUseCase
import com.example.yourday.usecase.GetFavUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import javax.inject.Inject


@HiltViewModel
class FavViewModel @Inject constructor(
    private val deleteFavUseCase: DeleteFavUseCase,
    private val getFavUseCase: GetFavUseCase,
):ViewModel() {

    val favQuotes = getFavUseCase()
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(500), emptyList())

    fun deleteFavQuote(quote: FavQuote) {
        viewModelScope.launch {
            deleteFavUseCase(quote)
        }

    }
}