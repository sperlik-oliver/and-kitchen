package tech.sperlikoliver.and_kitchen.Model.Proxy

import tech.sperlikoliver.and_kitchen.Model.App.AnonymousAuth
import tech.sperlikoliver.and_kitchen.Model.App.PropertyChangeAware
import tech.sperlikoliver.and_kitchen.Model.Firebase.Entity.ShoppingListItem
import tech.sperlikoliver.and_kitchen.Model.Firebase.Repository.ShoppingListRepositoryFirebase
import tech.sperlikoliver.and_kitchen.Model.Interface.IShoppingListRepository
import tech.sperlikoliver.and_kitchen.Model.Room.Repository.ShoppingListRepositoryRoom
import java.beans.PropertyChangeListener
import java.beans.PropertyChangeSupport

class ShoppingListRepositoryProxy : IShoppingListRepository{

    private val shoppingListRepositoryFirebase : IShoppingListRepository = ShoppingListRepositoryFirebase()
    private val shoppingListRepositoryRoom : IShoppingListRepository = ShoppingListRepositoryRoom()
    override val propertyChangeSupport : PropertyChangeSupport = PropertyChangeSupport(this)

    init {
        addShoppingListListener()
    }

    private fun addShoppingListListener(){
        shoppingListRepositoryFirebase.addPropertyChangeListener(PropertyChangeListener {
            event ->
            if (event.propertyName == "shopping_list") {
                propertyChangeSupport.firePropertyChange("shopping_list", null, event.newValue)
            }
        })
        shoppingListRepositoryRoom.addPropertyChangeListener(PropertyChangeListener {
            event ->
            if (event.propertyName == "shopping_list") {
                propertyChangeSupport.firePropertyChange("shopping_list", null, event.newValue)
            }
        })
    }


    override fun getShoppingList() {
        if (!AnonymousAuth.get()){
            shoppingListRepositoryFirebase.getShoppingList()
        } else {
            shoppingListRepositoryRoom.getShoppingList()
        }
    }

    override fun createShoppingListItem(shoppingListItem: ShoppingListItem) {
        if (!AnonymousAuth.get()){
            shoppingListRepositoryFirebase.createShoppingListItem(shoppingListItem)
        }else{
            shoppingListRepositoryRoom.createShoppingListItem(shoppingListItem)
        }
    }

    override fun editShoppingListItem(shoppingListItem: ShoppingListItem) {
        if (!AnonymousAuth.get()){
            shoppingListRepositoryFirebase.editShoppingListItem(shoppingListItem)
        }else{
            shoppingListRepositoryRoom.editShoppingListItem(shoppingListItem)
        }
    }

    override fun deleteShoppingListItem(shoppingListItem: ShoppingListItem) {
        if (!AnonymousAuth.get()){
            shoppingListRepositoryFirebase.deleteShoppingListItem(shoppingListItem)
        } else {
            shoppingListRepositoryRoom.deleteShoppingListItem(shoppingListItem)
        }
    }

}