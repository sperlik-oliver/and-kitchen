package tech.sperlikoliver.and_kitchen

import androidx.compose.foundation.background
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import tech.sperlikoliver.and_kitchen.View.MealPlanner
import tech.sperlikoliver.and_kitchen.View.Recipes
import tech.sperlikoliver.and_kitchen.View.ShoppingList
import androidx.compose.runtime.*
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.navigation.NavController
import com.google.accompanist.systemuicontroller.rememberSystemUiController
import com.google.firebase.auth.FirebaseAuth
import tech.sperlikoliver.and_kitchen.View.Scaffold.Login
import tech.sperlikoliver.and_kitchen.View.Theme.And_kitchenTheme


@Composable
fun KitchenApp(){

    // System components color setup
    val systemUiController = rememberSystemUiController()
    if (isSystemInDarkTheme()) {
        systemUiController.setSystemBarsColor(
            color = Color.Black
        )
    } else {
        systemUiController.setSystemBarsColor(
            color = Color.White
        )
    }

    // Navigation setup
    val navController = rememberNavController();
    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentRoute = navBackStackEntry?.destination?.route

    // Start destination setup for login
    var startDestination = if (FirebaseAuth.getInstance().currentUser != null) {
        "recipes"
    } else {
        "login"
    }

    And_kitchenTheme {
        Scaffold(
            topBar = { TopBarView(navController = navController, currentRoute = currentRoute)},
            bottomBar = { BottomBarView(navController = navController, currentRoute = currentRoute)},
        ) {
            NavHost(navController = navController, startDestination = startDestination) {
                composable("login") { Login(navController = navController) }
                composable("recipes") { Recipes(navController = navController) }
                composable("shoppingList") { ShoppingList(navController = navController) }
                composable("mealPlanner") { MealPlanner(navController = navController) }
                composable("settings") { Settings(navController = navController) }
            }
        }
    }
}



