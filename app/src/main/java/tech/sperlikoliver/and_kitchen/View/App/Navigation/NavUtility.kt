package tech.sperlikoliver.and_kitchen.View.App.Navigation

import androidx.navigation.NavController
import androidx.navigation.NavOptions

object NavUtility {
    fun SetPopUpToNav(route : String, navController : NavController){
        val navBuilder = NavOptions.Builder()
        val navOptions : NavOptions = navBuilder.setPopUpTo(route, true).build()
        navController.navigate(route, navOptions)
    }
}