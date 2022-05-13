package tech.sperlikoliver.and_kitchen.ViewModel.ShoppingList

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import tech.sperlikoliver.and_kitchen.Model.Firebase.Entity.ShoppingListItem
import tech.sperlikoliver.and_kitchen.Model.Interface.IShoppingListRepository
import tech.sperlikoliver.and_kitchen.Model.Proxy.ShoppingListRepositoryProxy
import java.beans.PropertyChangeListener

private const val TAG = "ShoppingListViewModel"

class ShoppingListViewModel: ViewModel() {

    private val repository : ShoppingListRepositoryProxy = ShoppingListRepositoryProxy()

    private val _shoppingListFlow = MutableStateFlow<MutableList<ShoppingListItem>>(mutableListOf())
    val shoppingList : StateFlow<MutableList<ShoppingListItem>> get() = _shoppingListFlow
    private fun setShoppingList (shoppingList : MutableList<ShoppingListItem>) {
        _shoppingListFlow.value = shoppingList
    }

    init{
        addShoppingListListener()
        repository.getShoppingList()
    }

    private fun addShoppingListListener(){
        repository.addPropertyChangeListener(PropertyChangeListener {
                event ->
            if(event.propertyName == "shopping_list") {
                setShoppingList(event.newValue as MutableList<ShoppingListItem>)
            }
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

