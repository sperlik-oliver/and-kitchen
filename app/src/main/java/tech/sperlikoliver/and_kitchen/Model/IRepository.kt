package tech.sperlikoliver.and_kitchen.Model

import tech.sperlikoliver.and_kitchen.Model.Domain.ShoppingListItem

interface IRepository {



    fun getAllShoppingListItems () : List<ShoppingListItem>
    fun deleteShoppingListItem (shoppingListItem: ShoppingListItem)
    fun createShoppingListItem (shoppingListItem: ShoppingListItem)
}