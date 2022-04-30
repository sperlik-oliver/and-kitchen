package tech.sperlikoliver.and_kitchen

import androidx.compose.foundation.background
import androidx.compose.material.FabPosition
import androidx.compose.material.FloatingActionButton
import androidx.compose.material.Icon
import androidx.compose.material.Scaffold
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
import tech.sperlikoliver.and_kitchen.View.Scaffold.Base.FloatingActionButtonWrapper
import tech.sperlikoliver.and_kitchen.View.ShoppingList
import androidx.compose.runtime.*

@Composable
fun KitchenApp () {
//    var fabPressed by remember {mutableStateOf(false)}
//    val onPressedChange = { value : Boolean -> fabPressed = value}
    val navController = rememberNavController();
    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentRoute = navBackStackEntry?.destination?.route
    Scaffold(
        topBar = { TopBarView(navController = navController, currentRoute = currentRoute)},
        bottomBar = { BottomBarView(navController = navController, currentRoute = currentRoute)},
//        floatingActionButtonPosition = FabPosition.End,
//        floatingActionButton = { FloatingActionButtonWrapper(navController = navController, fabPressed = fabPressed, onPressedChange = onPressedChange) }
    ) {
        NavHost(navController = navController, startDestination = "recipes") {
            composable("recipes") { Recipes(navController = navController)}
            composable("shoppingList") { ShoppingList(navController = navController) }
            composable("mealPlanner") { MealPlanner(navController = navController)}
            composable("settings") { Settings(navController = navController) }
        }
    }



}