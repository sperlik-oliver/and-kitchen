package tech.sperlikoliver.and_kitchen.View

import android.app.Activity
import android.content.Context
import android.content.ContextWrapper
import android.service.controls.ControlsProviderService.TAG
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import tech.sperlikoliver.and_kitchen.Model.Domain.ShoppingListItem
import tech.sperlikoliver.and_kitchen.ViewModel.ShoppingListViewModel
import androidx.compose.foundation.lazy.items
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.runtime.*
import androidx.compose.ui.platform.LocalContext
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.Observer
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clipToBounds
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.google.common.collect.ImmutableList


@Composable
fun ShoppingList (navController: NavController, viewModel: ShoppingListViewModel = ShoppingListViewModel()) {

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
fun ShoppingListLazyCol(shoppingList : ImmutableList<ShoppingListItem>, completed : Boolean, viewModel: ShoppingListViewModel){
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
fun ShoppingListItem(item: ShoppingListItem, viewModel: ShoppingListViewModel) {
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


