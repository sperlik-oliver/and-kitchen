package tech.sperlikoliver.and_kitchen.Model.Room.Repository

import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import tech.sperlikoliver.and_kitchen.Model.Firebase.Entity.ShoppingListItem
import tech.sperlikoliver.and_kitchen.Model.Interface.IShoppingListRepository
import tech.sperlikoliver.and_kitchen.Model.Room.Entity.ShoppingListItemRoom
import tech.sperlikoliver.and_kitchen.Model.Room.Database.KitchenDatabase
import java.beans.PropertyChangeSupport

class ShoppingListRepositoryRoom : IShoppingListRepository {

    override val propertyChangeSupport: PropertyChangeSupport = PropertyChangeSupport(this)
    private val shoppingListDao = KitchenDatabase.get().shoppingListDao()

    override fun getShoppingList() {
        val propertyName = "shopping_list"
        val shoppingList: MutableList<ShoppingListItem> = mutableListOf()
        runBlocking { launch{
            val retrievedShoppingList = shoppingListDao.getShoppingList()
            for (retrievedShoppingListItem in retrievedShoppingList){
                val shoppingListItem = ShoppingListItem(
                    id = retrievedShoppingListItem.id.toString(),
                    name = retrievedShoppingListItem.name,
                    completed = retrievedShoppingListItem.completed,
                    userId = ""
                )
                shoppingList.add(shoppingListItem)
            }
            propertyChangeSupport.firePropertyChange(propertyName, null, shoppingList)
        } }
    }

    override fun createShoppingListItem(shoppingListItem: ShoppingListItem) {
        runBlocking { launch {
            val shoppingListItemRoom = ShoppingListItemRoom(
                id = 0,
                name = shoppingListItem.name,
                completed = shoppingListItem.completed
            )
            shoppingListDao.createShoppingListItem(shoppingListItemRoom)
            getShoppingList()
        } }
    }

    override fun editShoppingListItem(shoppingListItem: ShoppingListItem) {
        runBlocking { launch{
            val shoppingListItemRoom = ShoppingListItemRoom(
                id = shoppingListItem.id.toLong(),
                name = shoppingListItem.name,
                completed = !shoppingListItem.completed
            )
            shoppingListDao.editShoppingListItem(shoppingListItemRoom)
            getShoppingList()
        } }
    }

    override fun deleteShoppingListItem(shoppingListItem: ShoppingListItem) {
        runBlocking {
            launch {
                val shoppingListItemRoom = ShoppingListItemRoom(
                    id = shoppingListItem.id.toLong(),
                    name = shoppingListItem.name,
                    completed = shoppingListItem.completed
                )
                shoppingListDao.deleteShoppingListItem(shoppingListItemRoom)
                getShoppingList()
            }
        }
    }


}