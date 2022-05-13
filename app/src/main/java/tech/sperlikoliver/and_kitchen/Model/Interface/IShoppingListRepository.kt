package tech.sperlikoliver.and_kitchen.Model.Interface

import tech.sperlikoliver.and_kitchen.Model.Firebase.Entity.ShoppingListItem
import tech.sperlikoliver.and_kitchen.Model.App.PropertyChangeAware

interface IShoppingListRepository : PropertyChangeAware{

    fun getShoppingList()
    fun createShoppingListItem(shoppingListItem: ShoppingListItem)
    fun editShoppingListItem(shoppingListItem: ShoppingListItem)
    fun deleteShoppingListItem(shoppingListItem: ShoppingListItem)
}