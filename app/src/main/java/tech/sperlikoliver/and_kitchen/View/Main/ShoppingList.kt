package tech.sperlikoliver.and_kitchen.View

import android.app.Activity
import android.content.Context
import android.content.ContextWrapper
import android.service.controls.ControlsProviderService.TAG
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import tech.sperlikoliver.and_kitchen.Model.Domain.ShoppingListItem
import tech.sperlikoliver.and_kitchen.ViewModel.ShoppingListViewModel
import androidx.compose.foundation.lazy.items
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material.icons.filled.Delete
import androidx.compose.runtime.*
import androidx.compose.ui.platform.LocalContext
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.Observer
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clipToBounds
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import tech.sperlikoliver.and_kitchen.View.Scaffold.Base.FloatingActionButtonWrapper

@Composable
fun ShoppingList (navController: NavController, viewModel: ShoppingListViewModel = ShoppingListViewModel()) {

    var addingItemField by remember {
        mutableStateOf("")
    }

    val viewModel: ShoppingListViewModel = remember {
        ShoppingListViewModel()
    }

    val shoppingList = viewModel.shoppingList.collectAsState()

Column(){

    Row {

        TextField(
            value = addingItemField,
            onValueChange = { newValue-> addingItemField = newValue }
        )
        IconButton(onClick = { viewModel.createShoppingListItem(ShoppingListItem(name = addingItemField)); addingItemField = ""}) {
            Icon(Icons.Filled.Add, "Add Item")
        }
    }
    LazyColumn (modifier = Modifier
        .padding(24.dp)
        .fillMaxSize(1f)) {
        items(shoppingList.value){
                item -> if(!item.completed) {ShoppingListItem(shoppingListItem = item, viewModel = viewModel)}
        }
    }

//    LazyColumn (modifier = Modifier
//        .padding(24.dp)
//        .fillMaxSize(1f)) {
//        items(shoppingList.value){
//                item -> if(item.completed) {ShoppingListItem(shoppingListItem = item, viewModel = viewModel)}
//        }
//    }



    }

}




@Composable
fun ShoppingListItem(shoppingListItem: ShoppingListItem, viewModel: ShoppingListViewModel) {
    var itemCompleted by remember { mutableStateOf(shoppingListItem.completed) }
    Row (verticalAlignment = Alignment.CenterVertically, modifier = Modifier.fillMaxWidth()) {
        Row (horizontalArrangement = Arrangement.Start, modifier = Modifier.weight(4f)){
            IconButton(onClick =
            {
                itemCompleted = !itemCompleted
                shoppingListItem.completed = itemCompleted
                viewModel.editShoppingListItem(shoppingListItem)
            }){
                Icon(Icons.Filled.CheckCircle, "Complete Item")
            }
            Text(text = shoppingListItem.name)
            if(itemCompleted){
                Text(text = "completed")
            }
        }
        Row (horizontalArrangement = Arrangement.End, modifier = Modifier.weight(1f)){
            IconButton(onClick = { viewModel.deleteShoppingListItem(shoppingListItem) }) {
                Icon(Icons.Filled.Delete, "Delete Item")
            }
        }
    }

}


