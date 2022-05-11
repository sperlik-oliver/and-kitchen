package tech.sperlikoliver.and_kitchen.Model.Repository.Interface

import tech.sperlikoliver.and_kitchen.Model.Domain.ShoppingListItem
import tech.sperlikoliver.and_kitchen.Model.Utility.PropertyChangeAware

interface IShoppingListRepository : PropertyChangeAware {

    fun getShoppingList()
    fun createShoppingListItem(shoppingListItem: ShoppingListItem)
    fun editShoppingListItem(shoppingListItem: ShoppingListItem)
    fun deleteShoppingListItem(shoppingListItem: ShoppingListItem)
}