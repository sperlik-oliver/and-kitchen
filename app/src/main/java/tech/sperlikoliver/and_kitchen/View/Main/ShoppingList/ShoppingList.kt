package tech.sperlikoliver.and_kitchen.View

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.navigation.NavController
import tech.sperlikoliver.and_kitchen.Model.Firebase.Entity.ShoppingListItem

import androidx.compose.foundation.lazy.items
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import tech.sperlikoliver.and_kitchen.ViewModel.ShoppingList.ShoppingListViewModel


@Composable
fun ShoppingList (navController: NavController) {




    var addingItemField by remember {
        mutableStateOf("")
    }

    val viewModel: ShoppingListViewModel = remember {
        ShoppingListViewModel()
    }

    val shoppingList = viewModel.shoppingList.collectAsState()

Column (modifier = Modifier.fillMaxSize(1f)){
    Row(modifier = Modifier.fillMaxWidth(1f).padding(top = 10.dp)) {
        OutlinedTextField(
            label = {Text("New Item")},
            modifier = Modifier.fillMaxWidth(1f),
            value = addingItemField,
            onValueChange = { newValue-> addingItemField = newValue },
            maxLines = 1,
            trailingIcon = {IconButton(onClick = { viewModel.createShoppingListItem(ShoppingListItem(name = addingItemField)); addingItemField = ""}) {
                Icon(Icons.Filled.Add, "Add Item", tint = MaterialTheme.colors.primary)
            }}

        )
    }


    ShoppingListLazyCol(shoppingList = shoppingList.value, completed = false, viewModel = viewModel)
    ShoppingListLazyCol(shoppingList = shoppingList.value, completed = true, viewModel = viewModel)


    }

}

@Composable
private fun ShoppingListLazyCol(shoppingList : MutableList<ShoppingListItem>, completed : Boolean, viewModel: ShoppingListViewModel){
    var showList : Boolean by remember { mutableStateOf(true) }
    val titleText = if (completed) { "Completed" } else { "Pending" }
    Column {
        Row(verticalAlignment = Alignment.CenterVertically, modifier = Modifier.padding(start = 20.dp, top = 20.dp)) {
            Text(
                text = titleText,
                color = MaterialTheme.colors.primary,
                fontWeight = FontWeight.Bold
            )

                IconButton(onClick = { showList = !showList }) {
                    if (!showList) {
                        Icon(Icons.Filled.KeyboardArrowRight, "Show list", tint = MaterialTheme.colors.primary)
                    } else {
                        Icon(Icons.Filled.KeyboardArrowDown, "Hide list", tint = MaterialTheme.colors.primary)
                    }
                }
        }

if (showList) {
    if (!completed) {
        LazyColumn(modifier = Modifier.padding(horizontal = 7.dp)) {
            items(shoppingList) { item ->
                if (!item.completed) {
                    ShoppingListItem(item, viewModel)
                }
            }
        }
    } else {
        LazyColumn(modifier = Modifier.padding(7.dp)) {
            items(shoppingList) { item ->
                if (item.completed) {
                    ShoppingListItem(item, viewModel)
                }
            }
        }
    }
}
    }
}

@Composable
private fun ShoppingListItem(item: ShoppingListItem, viewModel: ShoppingListViewModel) {
    Row (verticalAlignment = Alignment.CenterVertically, modifier = Modifier.fillMaxWidth()) {
        Row (verticalAlignment = Alignment.CenterVertically, horizontalArrangement = Arrangement.Start, modifier = Modifier.weight(4f)){
            IconButton(onClick =
            {
                viewModel.editShoppingListItem(item)

            }){
                if (!item.completed) {
                    Icon(Icons.Filled.CheckCircle, "Complete Item", tint = MaterialTheme.colors.primary)
                } else {
                    Icon(Icons.Filled.Close, "Undo Complete Item", tint = MaterialTheme.colors.primary)
                }
            }
            Text(text = item.name)
        }
        Row (horizontalArrangement = Arrangement.End, modifier = Modifier.weight(1f)){
            IconButton(onClick = { viewModel.deleteShoppingListItem(item) }) {
                Icon(Icons.Filled.Delete, "Delete Item", tint = MaterialTheme.colors.secondary)
            }
        }
    }

}


