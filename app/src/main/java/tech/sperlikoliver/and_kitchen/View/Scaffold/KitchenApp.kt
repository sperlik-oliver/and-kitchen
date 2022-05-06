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

@Composable
fun KitchenApp () {
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

    val navController = rememberNavController();
    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentRoute = navBackStackEntry?.destination?.route
    Scaffold(
        topBar = { TopBarView(navController = navController, currentRoute = currentRoute)},
        bottomBar = { BottomBarView(navController = navController, currentRoute = currentRoute)},
    ) {
        NavHost(navController = navController, startDestination = "recipes") {
            composable("recipes") { Recipes(navController = navController)}
            composable("shoppingList") { ShoppingList(navController = navController) }
            composable("mealPlanner") { MealPlanner(navController = navController)}
            composable("settings") { Settings(navController = navController) }
        }
    }
}

@Composable
fun NavigationIcon(navController : NavController, currentRoute : String?, navigationRoute : String, contentDescription: String, icon : ImageVector, modifier: Modifier = Modifier.scale(1f)){
    IconButton(onClick = { navController.navigate(navigationRoute) }, modifier = modifier) {
        if (currentRoute == navigationRoute){
            Icon(icon, contentDescription, tint = MaterialTheme.colors.primary, modifier = Modifier.scale(1.2f) )
        }else {
            Icon(icon, contentDescription)
        }
    }
}