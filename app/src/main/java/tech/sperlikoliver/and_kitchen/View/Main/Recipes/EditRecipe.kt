package tech.sperlikoliver.and_kitchen.View.Main.Recipes

import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import tech.sperlikoliver.and_kitchen.Model.Firebase.Entity.Recipe
import tech.sperlikoliver.and_kitchen.ViewModel.Recipes.EditRecipeViewModel


@Composable
fun EditRecipe (navController: NavController, recipeId : String) {

    var help = remember { mutableStateOf(false) }

    val viewModel: EditRecipeViewModel = remember {
        EditRecipeViewModel(recipeId)
    }


    val nameField = viewModel.nameField.collectAsState()
    val categoryField = viewModel.categoryField.collectAsState()
    val descriptionField = viewModel.descriptionField.collectAsState()
    val ingredientsField = viewModel.ingredientsField.collectAsState()
    val directionsField = viewModel.directionsField.collectAsState()
    val addedIngredients = viewModel.addedIngredients.collectAsState()


    Column(
        modifier = Modifier
            .padding(horizontal = 15.dp, vertical = 15.dp)
            .padding(bottom = 60.dp)
            .fillMaxSize(1f), verticalArrangement = Arrangement.spacedBy(10.dp)
    ) {

        Column(Modifier.weight(2f)) {
            ViewRecipeTitle(title = "name")
            Column {
                TextField(
                    value = nameField.value,
                    onValueChange = { viewModel.setNameField(it) },
                    modifier = Modifier.fillMaxWidth()
                )
            }
        }
        Column(Modifier.weight(2f)) {
            ViewRecipeTitle(title = "category")
            Column {
                TextField(
                    value = categoryField.value,
                    onValueChange = { viewModel.setCategoryField(it) },
                    modifier = Modifier.fillMaxWidth()
                )
            }
        }
        Column(Modifier.weight(2f)) {
            ViewRecipeTitle(title = "description")
            Column {
                TextField(
                    value = descriptionField.value,
                    onValueChange = { viewModel.setDescriptionField(it) },
                    modifier = Modifier.fillMaxWidth()
                )
            }
        }
        Column(Modifier.weight(3f)) {
            ViewRecipeTitle(title = "ingredients")
            Column {
                TextField(
                    value = ingredientsField.value,
                    onValueChange = { viewModel.setIngredientsField(it) },
                    modifier = Modifier.fillMaxWidth(),
                    trailingIcon = {
                        IconButton(onClick = {
                            addedIngredients.value.add(ingredientsField.value);viewModel.setAddedIngredients(
                            addedIngredients.value
                        );viewModel.setIngredientsField("")
                        }) {
                            Icon(Icons.Filled.Add, "Add Ingredient")
                        }
                    })

                Row(
                    Modifier.horizontalScroll(rememberScrollState()),
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    var c = 1
                    if (help.value || !help.value) {
                        for (ingredient in addedIngredients.value) {
                            Button(
                                onClick = {
                                    addedIngredients.value.remove(ingredient);
                                    viewModel.setAddedIngredients(addedIngredients.value)
                                    help.value = !help.value
                                },
                                colors = ButtonDefaults.buttonColors(backgroundColor = MaterialTheme.colors.background)
                            ) {
                                Text("$c. $ingredient")
                                c++
                            }

                        }
                    }
                }
            }
        }
        Column(Modifier.weight(2f)) {
            ViewRecipeTitle(title = "directions")
            Column {
                TextField(
                    value = directionsField.value,
                    onValueChange = { viewModel.setDirectionsField(it) },
                    placeholder = { Text("Fry meat, toast bun, assemble") },
                    modifier = Modifier.fillMaxWidth()
                )
            }
        }

        Column(Modifier.weight(1f)) {
            Column {
                Button(onClick = {
                    val recipe = Recipe(
                        id = recipeId,
                        name = nameField.value,
                        category = categoryField.value,
                        description = descriptionField.value,
                        ingredients = addedIngredients.value,
                        directions = directionsField.value
                    )
                    viewModel.editRecipe(recipe)
                    navController.navigateUp()
                }) {
                    Text("Submit")
                }
            }
        }

    }

}