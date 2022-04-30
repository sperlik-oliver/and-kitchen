package tech.sperlikoliver.and_kitchen.View

import android.app.Activity
import android.content.Context
import android.content.ContextWrapper
import android.service.controls.ControlsProviderService.TAG
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import tech.sperlikoliver.and_kitchen.Model.Domain.ShoppingListItem
import tech.sperlikoliver.and_kitchen.ViewModel.ShoppingListViewModel
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.*
import androidx.compose.ui.platform.LocalContext
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.Observer
import androidx.compose.runtime.*

@Composable
fun ShoppingList (navController: NavController, viewModel: ShoppingListViewModel = ShoppingListViewModel()) {

    val viewModel: ShoppingListViewModel = remember {
        ShoppingListViewModel()
    }

    val shoppingList = viewModel.shoppingList.collectAsState()


    LazyColumn {
        items(shoppingList.value){
            item -> ShoppingListItem(shoppingListItem = item)
        }
    }


}

@Composable
fun ShoppingListItem(shoppingListItem: ShoppingListItem) {

      Text(text = shoppingListItem.name)

}


