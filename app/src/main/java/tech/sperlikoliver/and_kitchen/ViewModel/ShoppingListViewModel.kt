package tech.sperlikoliver.and_kitchen.ViewModel

import android.util.Log
import androidx.lifecycle.ViewModel
import com.google.common.collect.ImmutableList
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import tech.sperlikoliver.and_kitchen.Model.Domain.ShoppingListItem
import tech.sperlikoliver.and_kitchen.Model.Repository.Implementation.ShoppingListRepositoryImpl
import tech.sperlikoliver.and_kitchen.Model.Repository.Interface.IShoppingListRepository
import java.beans.PropertyChangeListener

private const val TAG = "ShoppingListViewModel"

class ShoppingListViewModel: ViewModel() {

    private val repository : IShoppingListRepository = ShoppingListRepositoryImpl()

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
                event -> setShoppingList(event.newValue as MutableList<ShoppingListItem>)
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

