package com.example.yourday.presentation.favorite


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
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.FavoriteBorder
import androidx.compose.material.icons.filled.Share
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CardElevation
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.Divider
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.example.yourday.R
import com.example.yourday.model.FavQuote
import com.example.yourday.presentation.home.ShareQuote


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun FavoritesScreen(navController: NavController,viewModel: FavViewModel = hiltViewModel()) {

    val favorites = viewModel.favQuotes.collectAsState()
    val context = LocalContext.current

    Scaffold (
        topBar = {
            CenterAlignedTopAppBar(
                title = {
                    Text("Favorites Quotes", fontWeight = FontWeight.Bold)
                },
                navigationIcon = {
                    IconButton(onClick = {
                        navController.navigate("home")
                    } ) {
                        Icon(
                            imageVector = Icons.Default.ArrowBack,
                            contentDescription = "Back Icon",
                            tint = Color.Black
                        )
                    }
                }
            )

        }

    ) { paddingVal ->

        if(favorites.value.isEmpty()) {
            Box(
                modifier = Modifier.fillMaxSize().padding(paddingVal),
                contentAlignment = Alignment.Center
            ) {
                Text("No Favorite Quotes yet! \n Please add Favorite")
            }
        } else {
            LazyColumn(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(paddingVal)
                    .padding(16.dp),
                verticalArrangement = Arrangement.spacedBy(10.dp)
            ) {
                items(
                    count = favorites.value.size,
                    key = { index -> favorites.value[index].id }
                ) { index ->
                    val quote = favorites.value[index]

                    FavoriteItem(
                        quote = quote,
                        onShareClick = { ShareQuote(context, quote) },
                        onDeleteClick = { viewModel.deleteFavQuote(quote) }
                    )
                }
            }
        }

    }

}

@Composable
fun FavoriteItem(
    quote: FavQuote,
    onShareClick: () -> Unit = {},
    onDeleteClick: () -> Unit = {}
) {
    val context = LocalContext.current
    val gradient = Brush.verticalGradient(
        colors = listOf(
            Color(0xFFE3BBAA),
            Color(0xFFB89A8E)
        )
    )


    Card(
        modifier = Modifier
            .fillMaxWidth()
            .height(220.dp)
            .background(Color.Transparent),
        shape = RoundedCornerShape(16.dp),
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
    ) {
        Box(
            modifier = Modifier.fillMaxSize()
                .background(gradient)
                .padding(20.dp)
        ) {
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.spacedBy(12.dp),
                modifier = Modifier.fillMaxWidth()
            ) {
                Text(text = "“${quote.text}”",
                    fontStyle = FontStyle.Italic,
                    fontSize = 16.sp,
                    textAlign = TextAlign.Center,
                    color = Color.Black)

                Spacer(Modifier.height(4.dp))

                Text(text = "— ${quote.author.uppercase()}",
                    fontWeight = FontWeight.SemiBold,
                    fontSize = 14.sp,
                    color = Color(0xFF1E1E1E)
                )

            }

            Spacer(Modifier.height(32.dp))

            Row(
                modifier = Modifier.fillMaxWidth()
                    .align(Alignment.BottomEnd),
                horizontalArrangement = Arrangement.End,
                verticalAlignment = Alignment.CenterVertically


            ) {
                IconButton(
                    onClick = {
                        onShareClick()
                    }
                ) {
                    Icon(
                        imageVector =
                            Icons.Default.Share,
                        contentDescription = "Share Quote",
                        tint = Color.Black
                    )
                }

                IconButton(
                    onClick = {
                        onDeleteClick()
                        Toast.makeText(context, "Removed from favorites ❤️", Toast.LENGTH_SHORT)
                            .show()
                    }
                ) {
                    Icon(
                        imageVector = Icons.Default.Favorite,
                        contentDescription = "Share Quote",
                        tint = MaterialTheme.colorScheme.error
                    )
                }
            }

        }
    }

}