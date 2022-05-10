package tech.sperlikoliver.and_kitchen.ViewModel

import androidx.lifecycle.ViewModel
import com.google.common.collect.ImmutableList
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import tech.sperlikoliver.and_kitchen.Model.Domain.ShoppingListItem
import tech.sperlikoliver.and_kitchen.Model.Repository.Implementation.ShoppingListRepositoryImpl
import tech.sperlikoliver.and_kitchen.Model.Repository.Interface.IShoppingListRepository
import java.beans.PropertyChangeListener

class ShoppingListViewModel: ViewModel() {

    private val shoppingListRepositoryImpl : IShoppingListRepository = ShoppingListRepositoryImpl()

    private val _shoppingListFlow = MutableStateFlow<MutableList<ShoppingListItem>>(mutableListOf())

    val shoppingList : StateFlow<MutableList<ShoppingListItem>> get() = _shoppingListFlow


    private fun setShoppingList (shoppingList : MutableList<ShoppingListItem>) {
        _shoppingListFlow.value = shoppingList
    }

    init{
        addShoppingListListener()
    }

    private fun addShoppingListListener(){
        shoppingListRepositoryImpl.addPropertyChangeListener(PropertyChangeListener {
                event -> setShoppingList(event.newValue as MutableList<ShoppingListItem>)
        })
    }


    fun deleteShoppingListItem(shoppingListItem: ShoppingListItem) {
        shoppingListRepositoryImpl.deleteShoppingListItem(shoppingListItem)
    }

    fun createShoppingListItem(shoppingListItem: ShoppingListItem) {
        shoppingListRepositoryImpl.createShoppingListItem(shoppingListItem)
    }

    fun editShoppingListItem(shoppingListItem: ShoppingListItem){
        shoppingListRepositoryImpl.editShoppingListItem(shoppingListItem)
        }

}

