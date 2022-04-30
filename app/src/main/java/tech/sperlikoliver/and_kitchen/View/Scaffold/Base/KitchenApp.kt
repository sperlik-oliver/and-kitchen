package tech.sperlikoliver.and_kitchen

import androidx.compose.material.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import tech.sperlikoliver.and_kitchen.View.MealPlanner
import tech.sperlikoliver.and_kitchen.View.Recipes
import tech.sperlikoliver.and_kitchen.View.ShoppingList

@Composable
fun KitchenApp () {
    val navController = rememberNavController();
    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentRoute = navBackStackEntry?.destination?.route
    Scaffold(
        topBar = { TopBarView(navController = navController, currentRoute = currentRoute)},
        bottomBar = { BottomBarView(navController = navController, currentRoute = currentRoute)}
    ) {
        NavHost(navController = navController, startDestination = "recipes") {
            composable("recipes") { Recipes(navController = navController)}
            composable("shoppingList") { ShoppingList(navController = navController) }
            composable("mealPlanner") { MealPlanner(navController = navController)}
            composable("settings") { Settings(navController = navController) }
        }
    }



}