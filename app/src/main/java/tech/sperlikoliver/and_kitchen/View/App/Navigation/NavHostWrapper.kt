package tech.sperlikoliver.and_kitchen.View.App.Navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import tech.sperlikoliver.and_kitchen.Settings
import tech.sperlikoliver.and_kitchen.View.Main.MealPlanner.AddMealPlannerEntry
import tech.sperlikoliver.and_kitchen.View.Main.MealPlanner.EditMealPlannerEntry
import tech.sperlikoliver.and_kitchen.View.Main.MealPlanner.MealPlanner
import tech.sperlikoliver.and_kitchen.View.Main.MealPlanner.ViewMealPlannerEntry
import tech.sperlikoliver.and_kitchen.View.Main.Recipes.AddRecipe
import tech.sperlikoliver.and_kitchen.View.Main.Recipes.EditRecipe
import tech.sperlikoliver.and_kitchen.View.Main.Recipes.Recipes
import tech.sperlikoliver.and_kitchen.View.Main.Recipes.ViewRecipe
import tech.sperlikoliver.and_kitchen.View.App.Scaffold.Login
import tech.sperlikoliver.and_kitchen.View.ShoppingList
@Composable
fun NavHostWrapper(navController : NavHostController, startDestination : String) {
    NavHost(navController = navController, startDestination = startDestination) {
        composable("login") { Login(navController = navController) }
        composable("recipes") { Recipes(navController = navController) }
        composable("shoppingList") { ShoppingList(navController = navController) }
        composable("mealPlanner") { MealPlanner(navController = navController) }
        composable("settings") { Settings(navController = navController) }
        composable(route = "viewRecipe/{id}", arguments = listOf(
            navArgument("id") {
                type = NavType.StringType
            }
        )) {
            ViewRecipe(navController = navController, recipeId = it.arguments?.getString("id")!!)
        }
        composable("editRecipe/{id}", arguments = listOf(
            navArgument("id") {
                type = NavType.StringType
            }
        )) {
            EditRecipe(navController = navController, recipeId = it.arguments?.getString("id")!!)
        }
        composable("addRecipe") { AddRecipe(navController) }
        composable("editMealPlannerEntry/{id}", arguments = listOf(
            navArgument("id") {
                type = NavType.StringType
            }
        )) {
            EditMealPlannerEntry(
                navController = navController,
                mealPlannerEntryId = it.arguments?.getString("id")!!
            )
        }
        composable("viewMealPlannerEntry/{id}", arguments = listOf(
            navArgument("id") {
                type = NavType.StringType
            }
        )) {
            ViewMealPlannerEntry(
                navController = navController,
                mealPlannerEntryId = it.arguments?.getString("id")!!
            )
        }
        composable("addMealPlannerEntry") { AddMealPlannerEntry(navController) }
    }
}