package tech.sperlikoliver.and_kitchen.View.Main.Recipes

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Edit
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import tech.sperlikoliver.and_kitchen.Model.Firebase.Entity.Recipe
import tech.sperlikoliver.and_kitchen.ViewModel.Recipes.RecipesViewModel

@Composable
fun Recipes(navController: NavController){
    val viewModel: RecipesViewModel = remember {
        RecipesViewModel()
    }

    val recipes = viewModel.recipes.collectAsState()

    LazyColumn(modifier = Modifier.padding(7.dp)) {
        items(recipes.value) { recipe ->
            Recipe(recipe, viewModel, navController)
        }
    }
}

@Composable
private fun Recipe(recipe : Recipe, viewModel : RecipesViewModel, navController: NavController) {
    val id = recipe.id
    Row(verticalAlignment = Alignment.CenterVertically, horizontalArrangement = Arrangement.Start, modifier = Modifier.fillMaxWidth()) {

        Row(verticalAlignment = Alignment.CenterVertically, horizontalArrangement = Arrangement.Start, modifier = Modifier.weight(6.5f)){
            IconButton(onClick = { navController.navigate("editRecipe/$id")}) {
                Icon(Icons.Filled.Edit, "Edit Recipe", tint = MaterialTheme.colors.primary)
            }
            Button(onClick = {navController.navigate("viewRecipe/$id")}) {
                Text(text = recipe.name, modifier= Modifier.fillMaxWidth())
            }
        }
        Row(verticalAlignment = Alignment.CenterVertically, horizontalArrangement = Arrangement.End, modifier = Modifier.weight(1f)){
            IconButton(onClick = { viewModel.deleteRecipe(recipe) }) {
                Icon(Icons.Filled.Delete, "Delete Recipe", tint = MaterialTheme.colors.secondary)
            }
        }
    }

}