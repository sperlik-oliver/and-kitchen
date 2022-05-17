package tech.sperlikoliver.and_kitchen.View.Main.Recipes

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import tech.sperlikoliver.and_kitchen.ViewModel.Recipes.ViewRecipeViewModel

@Composable
fun ViewRecipe(navController: NavController, recipeId : String){
    val viewModel : ViewRecipeViewModel = remember {
        ViewRecipeViewModel(recipeId)
    }
    viewModel.getRecipe(recipeId)
    val recipe = viewModel.recipe.collectAsState()
    Column(
        modifier = Modifier
            .padding(horizontal = 15.dp, vertical = 15.dp)
            .padding(bottom = 50.dp)
        ,verticalArrangement = Arrangement.spacedBy(10.dp)) {

        Column(Modifier.weight(1.3f)) {
            ViewRecipeTitle(title = "name")
            Column(Modifier.verticalScroll(rememberScrollState())) {
                Text(recipe.value.name)
            }
        }
        Column(Modifier.weight(1.3f)) {
            ViewRecipeTitle(title = "category")
            Column() {
                Text(recipe.value.category)
            }
        }
        Column(Modifier.weight(4f)){
            ViewRecipeTitle(title = "description")
            Column(Modifier.verticalScroll(rememberScrollState())) {
                Text(recipe.value.description)
            }
        }
        Column(Modifier.weight(4f)) {
            var c = 1
            ViewRecipeTitle(title = "ingredients")
            Column(
                Modifier.verticalScroll(rememberScrollState())
            ) {
                for (ingredient in recipe.value.ingredients) {
                    Text("$c. $ingredient")
                    c++
                }
            }
        }
        Column(Modifier.weight(4f)) {
            ViewRecipeTitle(title = "directions")
            Column(
                Modifier.verticalScroll(rememberScrollState())
            ) {
                Text(recipe.value.directions)
            }
        }

    }

}