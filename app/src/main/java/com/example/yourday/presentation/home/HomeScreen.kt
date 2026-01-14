package com.example.yourday.presentation.home

import android.content.Intent
import android.widget.Toast
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.FavoriteBorder
import androidx.compose.material.icons.filled.Refresh
import androidx.compose.material.icons.filled.Share
import androidx.compose.material.icons.outlined.Favorite
import androidx.compose.material.icons.outlined.FavoriteBorder
import androidx.compose.material.icons.outlined.Share
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.example.yourday.R
import com.example.yourday.model.Quote


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen(navController: NavController,viewModel: HomeViewModel = hiltViewModel()) {

    val sate by viewModel.state.collectAsState()
    val context = LocalContext.current

    LaunchedEffect(Unit) {
        viewModel.fetchQuote()
    }
    Scaffold (
        topBar = {
            CenterAlignedTopAppBar(
                modifier = Modifier.padding(12.dp),
                title = {
                    Text(
                        text = "YourDay",
                        style = MaterialTheme.typography.titleLarge.copy(fontSize = 20.sp),
                        fontWeight = FontWeight.Bold
                    )
                },

                actions = {
                    IconButton(onClick = {
                        navController.navigate("favorite")
                    } ) {
                        Icon(
                            imageVector = Icons.Default.Favorite,
                            contentDescription = "Favorite Icon",
                            tint = Color.Red,
                            modifier = Modifier.size(36.dp)
                        )

                    }
                }
            )
        },
        bottomBar = {
            Box (
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(32.dp),
                contentAlignment = Alignment.Center

            ) {
                Button(
                    onClick = { viewModel.fetchQuote() },
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(56.dp),
                    shape = RoundedCornerShape(28.dp),
                    colors = ButtonDefaults.buttonColors(Color(0xFF00B4DB))
                ) {
                    Icon(Icons.Default.Refresh, contentDescription = "Refresh")
                    Spacer(Modifier.width(8.dp))
                    Text(text = "Refresh",color = Color.White, fontWeight = FontWeight.W500)
                }


            }

        }

    ) { paddingVal ->

        Box (
            modifier = Modifier.padding(paddingVal)
                .fillMaxSize()
                .padding(16.dp),
            contentAlignment = Alignment.Center
        ) {
            when {
                sate.isLoading -> CircularProgressIndicator()
                sate.error != null -> Text(text = sate.error?: "Unknown Error",color = Color.Red)

                sate.quote != null ->{

                    QuotesCard(
                        key = sate.quote?.text ?: "empty",
                        quoteText = sate.quote?.text?: "",
                        author = sate.quote?.author?: "Unknown",
                        onShareClick = {ShareQuote(context, quote = sate.quote!!) },
                        onFavoriteClick = { viewModel.addToFavorites(sate.quote!!)}
                    )
                }
            }
        }

    }
}

@Composable
fun QuotesCard (
    key: Any,
    quoteText: String,
    author: String,
    onShareClick: () -> Unit,
    onFavoriteClick: () -> Unit

) {

    var isFav by remember(key) { mutableStateOf(false) }
    val context = LocalContext.current
    val gradient = Brush.linearGradient(
        colors = listOf(
            Color(0xFFF3B391),
            Color(0xFFD99282)
        )

    )
    Card (
        modifier = Modifier
            .fillMaxWidth()
            .height(460.dp)
            .padding(24.dp),
        shape = RoundedCornerShape(20.dp),
        elevation = CardDefaults.cardElevation(defaultElevation = 6.dp)

    ) {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(gradient)
        ) {

                Column(
                    modifier = Modifier.align(Alignment.Center).padding(12.dp),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Text(
                        text = "❝",
                        color = Color(0x33FFFFFF),
                        fontSize = 36.sp,
                    )

                    Text(
                        text = "“$quoteText”",
                        fontSize = 20.sp,
                        textAlign = TextAlign.Center,
                        fontStyle = FontStyle.Italic,
                        color = Color.Black
                    )

                    Spacer(modifier = Modifier.height(16.dp))

                    Text(
                        text = "- $author",
                        fontSize = 16.sp,
                        color = Color.Gray,
                        style = MaterialTheme.typography.labelMedium
                    )
                }


                Row(
                    modifier = Modifier.fillMaxWidth().padding(16.dp)
                        .align(Alignment.BottomCenter),
                    horizontalArrangement = Arrangement.SpaceBetween

                ) {

                    IconButton(
                        onClick = onShareClick,
                        modifier = Modifier
                            .background(Color(0x22FFFFFF), CircleShape)

                    ) {
                        Icon(
                            imageVector = Icons.Outlined.Share,
                            contentDescription = "Share",
                            tint = Color(0xFF2E2E2E)
                        )

                    }

                    IconButton(
                        onClick = {
                            isFav = !isFav
                            onFavoriteClick()
                            Toast.makeText(context, "Added to favorites ❤️", Toast.LENGTH_SHORT)
                                .show()
                        },
                        modifier = Modifier
                            .background(Color(0x22FFFFFF), CircleShape)
                    ) {
                        Icon(
                            imageVector = if (isFav) Icons.Outlined.Favorite else Icons.Outlined.FavoriteBorder,
                            contentDescription = "Share Quote",
                            tint = if (isFav) MaterialTheme.colorScheme.error else MaterialTheme.colorScheme.onSurface,

                        )
                    }
                }

        }


    }
}



//@Composable
//@Preview(showBackground = true, showSystemUi = true)
//fun HomeScreenPreview() {
//    HomeScreen()
//}