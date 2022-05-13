package tech.sperlikoliver.and_kitchen

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.graphics.Color
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.google.accompanist.systemuicontroller.rememberSystemUiController
import com.google.firebase.auth.FirebaseAuth
import tech.sperlikoliver.and_kitchen.Model.App.AnonymousAuth
import tech.sperlikoliver.and_kitchen.View.App.Navigation.NavHostWrapper
import tech.sperlikoliver.and_kitchen.View.App.Scaffold.FAB
import tech.sperlikoliver.and_kitchen.View.Theme.And_kitchenTheme


@Composable
fun KitchenApp(){

//     System components color setup
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

    // Start destination setup
    var startDestination = if (FirebaseAuth.getInstance().currentUser != null || AnonymousAuth.get()) {
        "recipes"
    } else {
        "login"
    }


    And_kitchenTheme {
        Scaffold(
            topBar = { TopBarView(navController = navController, currentRoute = currentRoute)},
            bottomBar = { BottomBarView(navController = navController, currentRoute = currentRoute)},
            floatingActionButton = { FAB(navController = navController, currentRoute = currentRoute)}
        ) {
            NavHostWrapper(navController = navController, startDestination = startDestination)
        }
    }
}





