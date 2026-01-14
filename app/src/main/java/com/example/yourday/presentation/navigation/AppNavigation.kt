package com.example.yourday.presentation.navigation

import androidx.compose.runtime.Composable
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.example.yourday.presentation.favorite.FavViewModel
import com.example.yourday.presentation.favorite.FavoritesScreen
import com.example.yourday.presentation.home.HomeScreen
import com.example.yourday.presentation.home.HomeViewModel

sealed class Screen(val route: String){
    object Home: Screen("home")
    object Favorite: Screen("favorite")
}
@Composable
fun AppNavigation(navController: NavHostController) {
    NavHost(navController = navController, startDestination = Screen.Home.route) {
        composable(Screen.Home.route) {
            val viewModel: HomeViewModel = hiltViewModel()
            HomeScreen(navController,viewModel)
        }
        composable(Screen.Favorite.route) {
            val viewModel: FavViewModel = hiltViewModel()
            FavoritesScreen(navController,viewModel)
        }

    }

}