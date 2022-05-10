package tech.sperlikoliver.and_kitchen.ViewModel

import androidx.lifecycle.ViewModel
import com.google.common.collect.ImmutableList
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import tech.sperlikoliver.and_kitchen.Model.Domain.ShoppingListItem
import tech.sperlikoliver.and_kitchen.Model.Repository.ShoppingListRepositoryImpl
import java.beans.PropertyChangeListener

class ShoppingListViewModel: ViewModel() {
    private val shoppingListRepositoryImpl : ShoppingListRepositoryImpl = ShoppingListRepositoryImpl()
    private val _shoppingListFlow = MutableStateFlow<ImmutableList<ShoppingListItem>>(ImmutableList.of())
    val shoppingList : StateFlow<ImmutableList<ShoppingListItem>> get() = _shoppingListFlow


    fun setShoppingList (shoppingList : ImmutableList<ShoppingListItem>) {
        _shoppingListFlow.value = shoppingList
    }
    init{
        shoppingListRepositoryImpl.addPropertyChangeListener(PropertyChangeListener {
            event -> setShoppingList(event.newValue as ImmutableList<ShoppingListItem>)
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

