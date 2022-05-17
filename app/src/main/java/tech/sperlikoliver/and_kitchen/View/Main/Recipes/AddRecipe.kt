package tech.sperlikoliver.and_kitchen.View.Main.Recipes

import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import tech.sperlikoliver.and_kitchen.Model.Firebase.Entity.Recipe
import tech.sperlikoliver.and_kitchen.ViewModel.Recipes.AddRecipeViewModel

@Composable
fun AddRecipe(navController: NavController){
    val viewModel : AddRecipeViewModel = remember {
        AddRecipeViewModel()
    }


    var nameField = remember { mutableStateOf("") }
    var categoryField = remember{ mutableStateOf("") }
    var descriptionField = remember{ mutableStateOf("") }
    var ingredientsField = remember{ mutableStateOf("") }
    var directionsField = remember{ mutableStateOf("") }
    var addedIngredients = remember { mutableStateListOf<String>() }
    var dialog = remember { mutableStateOf(false) }
    var randomRecipe = viewModel.generatedRecipe.collectAsState()

    Column(
        modifier = Modifier
            .padding(horizontal = 15.dp, vertical = 15.dp)
            .padding(bottom = 60.dp)
            .fillMaxSize(1f)
        ,verticalArrangement = Arrangement.spacedBy(10.dp)) {

        if(dialog.value){
            AlertDialog(onDismissRequest = { /*TODO*/ },
                dismissButton = {
                    Row(horizontalArrangement = Arrangement.Center, modifier = Modifier.fillMaxWidth(1f)){
                        Button(onClick = {dialog.value = false}) {
                            Text("No".uppercase())
                        }
                    }
                },
                confirmButton = {
                    Row(horizontalArrangement = Arrangement.Center, modifier = Modifier.fillMaxWidth(1f)){
                        Button(onClick = {viewModel.createRecipe(randomRecipe.value);navController.navigate("recipes")}){
                            Text("I'm feeling lucky".uppercase())
                        }
                    } },
                title = { Row(horizontalArrangement = Arrangement.Center, modifier = Modifier.fillMaxWidth(1f)){ Text("Are you sure?".uppercase()) } })
        }


        Column(Modifier.weight(2f)) {
            Row(verticalAlignment = Alignment.CenterVertically){
                Row(horizontalArrangement = Arrangement.Start, modifier = Modifier.weight(2f)){
                    ViewRecipeTitle(title = "name")
                }
                Row(horizontalArrangement = Arrangement.End, modifier = Modifier.weight(2f)){
                    Button(onClick ={ dialog.value=true; viewModel.getRandomRecipe()}){
                        Text("Generate Random".uppercase())
                    }
                }

            }

            Column {
                TextField(value = nameField.value, onValueChange = {nameField.value = it}, placeholder = { Text("Burger") }, modifier = Modifier.fillMaxWidth())
            }
        }
        Column(Modifier.weight(2f)) {
            ViewRecipeTitle(title = "category")
            Column {
                TextField(value = categoryField.value, onValueChange = {categoryField.value = it}, placeholder = { Text("Sandwiches") }, modifier = Modifier.fillMaxWidth())
            }
        }
        Column(Modifier.weight(2f)){
            ViewRecipeTitle(title = "description")
            Column {
                TextField(value = descriptionField.value, onValueChange = {descriptionField.value = it}, placeholder = { Text("Tasty burger") }, modifier = Modifier.fillMaxWidth())
            }
        }
        Column(Modifier.weight(3f)) {
            ViewRecipeTitle(title = "ingredients")
            Column{
                TextField(
                    value = ingredientsField.value,
                    onValueChange = {ingredientsField.value = it},
                    placeholder = { Text("Mayo") },
                    modifier = Modifier.fillMaxWidth(),
                    trailingIcon = {
                        IconButton(onClick = { addedIngredients.add(ingredientsField.value);ingredientsField.value = "" }) {
                        Icon(Icons.Filled.Add, "Add Ingredient")
                    }
                    })
                Row(Modifier.horizontalScroll(rememberScrollState()), horizontalArrangement = Arrangement.SpaceBetween) {
                    var c = 1
                    for (ingredient in addedIngredients) {
                        Button(
                            onClick = { addedIngredients.remove(ingredient) },
                            colors = ButtonDefaults.buttonColors(backgroundColor = MaterialTheme.colors.background)
                        ) {
                            Text("$c. $ingredient")
                            c++
                        }

                    }
                }
            }
        }
        Column(Modifier.weight(2f)) {
            ViewRecipeTitle(title = "directions")
            Column {
                TextField(value = directionsField.value, onValueChange = {directionsField.value = it}, placeholder = { Text("Fry meat, toast bun, assemble") }, modifier = Modifier.fillMaxWidth())
            }
        }

        Column(Modifier.weight(1f)) {
            Column {
                Button(onClick = {
                    val recipe = Recipe(
                        name = nameField.value,
                        category = categoryField.value,
                        description = descriptionField.value,
                        ingredients = addedIngredients,
                        directions = directionsField.value
                    )
                    viewModel.createRecipe(recipe)
                    navController.navigateUp()
                }) {
                    Text("Submit")
                }
            }
        }

    }
}