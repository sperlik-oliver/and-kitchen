package tech.sperlikoliver.and_kitchen.ViewModel

import android.util.Log
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import tech.sperlikoliver.and_kitchen.Model.Domain.Ingredient
import tech.sperlikoliver.and_kitchen.Model.Domain.ShoppingListItem
import tech.sperlikoliver.and_kitchen.Model.IRepository
import tech.sperlikoliver.and_kitchen.Model.Repository
import java.beans.PropertyChangeListener

class ShoppingListViewModel: ViewModel() {
    private val repository : Repository = Repository()
    private val _shoppingListFlow = MutableStateFlow<List<ShoppingListItem>>(emptyList())
    val shoppingList : StateFlow<List<ShoppingListItem>> get() = _shoppingListFlow


    fun setShoppingList (shoppingList : List<ShoppingListItem>) {
        _shoppingListFlow.value = shoppingList
    }
    init{
        repository.addPropertyChangeListener(PropertyChangeListener {
            event -> setShoppingList(event.newValue as List<ShoppingListItem>); Log.i("View model updating shopping list", "update")
        })
    }


    fun deleteShoppingListItem(shoppingListItem: ShoppingListItem) {
        repository.deleteShoppingListItem(shoppingListItem)
    }

    fun createShoppingListItem(shoppingListItem: ShoppingListItem) {
        repository.createShoppingListItem(shoppingListItem)
    }

    fun editShoppingListItem(shoppingListItem: ShoppingListItem){
        repository.editShoppingListItem(shoppingListItem)
        }

}

