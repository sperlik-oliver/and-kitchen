package tech.sperlikoliver.and_kitchen.View.App.Scaffold

import androidx.compose.material.FloatingActionButton
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.runtime.Composable
import androidx.navigation.NavController

@Composable
fun FAB(navController: NavController, currentRoute : String?){
    if (currentRoute == "recipes"){
        FloatingActionButton(onClick = { navController.navigate("addRecipe") }, backgroundColor = MaterialTheme.colors.primary) {
            Icon(imageVector = Icons.Filled.Add, contentDescription = "Add Recipe")
        }
    }
    if (currentRoute == "mealPlanner"){
        FloatingActionButton(onClick = { navController.navigate("addMealPlannerEntry") }, backgroundColor = MaterialTheme.colors.primary) {
            Icon(imageVector = Icons.Filled.Add, contentDescription = "Add Recipe")
        }
    }
}