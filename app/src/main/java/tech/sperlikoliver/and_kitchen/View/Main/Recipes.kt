package tech.sperlikoliver.and_kitchen.View

import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.*
import androidx.compose.material.ButtonDefaults.buttonColors
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Edit
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import tech.sperlikoliver.and_kitchen.Model.Domain.Repository.Recipe
import tech.sperlikoliver.and_kitchen.ViewModel.RecipesViewModel


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
fun Recipe(recipe : Recipe, viewModel : RecipesViewModel, navController: NavController) {
    val id = recipe.id
    Row(verticalAlignment = Alignment.CenterVertically, horizontalArrangement = Arrangement.Start, modifier = Modifier.fillMaxWidth()) {

            Row(verticalAlignment = Alignment.CenterVertically, horizontalArrangement = Arrangement.Start, modifier = Modifier.weight(6.5f)){
                    IconButton(onClick = { navController.navigate("editRecipe/$id")}) {
                        Icon(Icons.Filled.Edit, "Edit Recipe", tint = MaterialTheme.colors.primary)
                    }
                    Button(onClick = {navController.navigate("viewRecipe/$id")}) {
                        Text(text = recipe.name, modifier=Modifier.fillMaxWidth())
                    }
                }
            Row(verticalAlignment = Alignment.CenterVertically, horizontalArrangement = Arrangement.End, modifier = Modifier.weight(1f)){
                    IconButton(onClick = { viewModel.deleteRecipe(recipe) }) {
                        Icon(Icons.Filled.Delete, "Delete Recipe", tint = MaterialTheme.colors.secondary)
                    }
                }
        }

}

@Composable
fun ViewRecipe(navController: NavController, recipeId : String = ""){
    val viewModel : RecipesViewModel = remember {
        RecipesViewModel()
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

@Composable
fun ViewRecipeTitle(title : String){
    Text(title.uppercase(), fontWeight = FontWeight.Bold, color = MaterialTheme.colors.primary, )
}

@Composable
fun AddRecipe(navController: NavController){

    val viewModel : RecipesViewModel = remember {
        RecipesViewModel()
    }

    var nameField = remember { mutableStateOf("") }
    var categoryField = remember{ mutableStateOf("") }
    var descriptionField = remember{ mutableStateOf("") }
    var ingredientsField = remember{ mutableStateOf("") }
    var directionsField = remember{ mutableStateOf("") }
    var addedIngredients = remember { mutableStateListOf<String>() }

    Column(
        modifier = Modifier
            .padding(horizontal = 15.dp, vertical = 15.dp)
            .padding(bottom = 60.dp)
            .fillMaxSize(1f)
        ,verticalArrangement = Arrangement.spacedBy(10.dp)) {

        Column(Modifier.weight(2f)) {
            ViewRecipeTitle(title = "name")
            Column {
                TextField(value = nameField.value, onValueChange = {nameField.value = it}, placeholder = {Text("Burger")}, modifier =Modifier.fillMaxWidth())
            }
        }
        Column(Modifier.weight(2f)) {
            ViewRecipeTitle(title = "category")
            Column {
                TextField(value = categoryField.value, onValueChange = {categoryField.value = it}, placeholder = {Text("Sandwiches")}, modifier = Modifier.fillMaxWidth())
            }
        }
        Column(Modifier.weight(2f)){
            ViewRecipeTitle(title = "description")
            Column {
                TextField(value = descriptionField.value, onValueChange = {descriptionField.value = it}, placeholder = {Text("Tasty burger")}, modifier = Modifier.fillMaxWidth())
            }
        }
        Column(Modifier.weight(3f)) {
            ViewRecipeTitle(title = "ingredients")
            Column{
                TextField(
                    value = ingredientsField.value,
                    onValueChange = {ingredientsField.value = it},
                    placeholder = {Text("Mayo")},
                    modifier = Modifier.fillMaxWidth(),
                    trailingIcon = {IconButton(onClick = { addedIngredients.add(ingredientsField.value);ingredientsField.value = "" }) {
                        Icon(Icons.Filled.Add, "Add Ingredient")
                }})
                Row(Modifier.horizontalScroll(rememberScrollState()), horizontalArrangement = Arrangement.SpaceBetween) {
                    var c = 1
                    for (ingredient in addedIngredients) {
                        Button(
                            onClick = { addedIngredients.remove(ingredient) },
                            colors = buttonColors(backgroundColor = MaterialTheme.colors.background)
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
                TextField(value = directionsField.value, onValueChange = {directionsField.value = it}, placeholder = {Text("Fry meat, toast bun, assemble")}, modifier =Modifier.fillMaxWidth())
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

@Composable
fun EditRecipe (navController: NavController, recipeId : String) {

    var help = remember { mutableStateOf(false) }

    val viewModel: RecipesViewModel = remember {
        RecipesViewModel(recipeId)
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
                                colors = buttonColors(backgroundColor = MaterialTheme.colors.background)
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


