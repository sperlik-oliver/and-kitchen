package tech.sperlikoliver.and_kitchen.Model.Repository.Implementation

import android.util.Log
import com.google.android.gms.tasks.Task
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import tech.sperlikoliver.and_kitchen.Model.Domain.DAO.ShoppingListItemRoom
import tech.sperlikoliver.and_kitchen.Model.Domain.Repository.ShoppingListItem
import tech.sperlikoliver.and_kitchen.Model.KitchenDatabase
import tech.sperlikoliver.and_kitchen.Model.Repository.Interface.IShoppingListRepository
import tech.sperlikoliver.and_kitchen.Model.Utility.mAuth
import java.beans.PropertyChangeSupport

private const val TAG : String = "ShoppingListRepositoryImpl"

class ShoppingListRepositoryImpl : IShoppingListRepository {


    override val propertyChangeSupport: PropertyChangeSupport = PropertyChangeSupport(this)
    private val database: FirebaseFirestore = Firebase.firestore
    private val shoppingListRef = database.collection("shopping_list")
    private val shoppingListDao = KitchenDatabase.get().shoppingListDao()

    override fun getShoppingList(){
        val propertyName = "shopping_list"
        val shoppingList: MutableList<ShoppingListItem> = mutableListOf()
        if (!mAuth.get()) {
            shoppingListRef.whereEqualTo("userId", FirebaseAuth.getInstance().currentUser?.uid)
                .get().addOnCompleteListener { shoppingListSnapshot ->
                val shoppingListResult = shoppingListSnapshot.result
                if (!shoppingListSnapshot.isSuccessful) {
                    shoppingListSnapshot.exception?.message?.let { Log.e(TAG, it) }
                    return@addOnCompleteListener
                }
                if (shoppingListResult == null) {
                    Log.e(TAG, "Shopping list snapshot is null")
                    return@addOnCompleteListener
                }
                if (shoppingListResult.isEmpty) {
                    propertyChangeSupport.firePropertyChange(propertyName, null, shoppingList)
                    return@addOnCompleteListener
                }
                for (shoppingListItemDocument in shoppingListResult) {
                    val shoppingListItem = ShoppingListItem(
                        id = shoppingListItemDocument.id,
                        name = shoppingListItemDocument.data["name"] as String,
                        completed = shoppingListItemDocument.data["completed"] as Boolean,
                        userId = shoppingListItemDocument.data["userId"] as String
                    )
                    shoppingList.add(shoppingListItem)
                }
                propertyChangeSupport.firePropertyChange(propertyName, null, shoppingList)
            }
        } else {
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
    }

    override fun createShoppingListItem(shoppingListItem : ShoppingListItem){
        if (!mAuth.get()) {
            val shoppingListItemData = hashMapOf(
                "name" to shoppingListItem.name,
                "completed" to shoppingListItem.completed,
                "userId" to FirebaseAuth.getInstance().currentUser?.uid
            )
            shoppingListRef.add(shoppingListItemData)
            getShoppingList()
        } else {
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
    }

    override fun editShoppingListItem(shoppingListItem: ShoppingListItem){
        if (!mAuth.get()) {
            val shoppingListItemData = hashMapOf(
                "name" to shoppingListItem.name,
                "completed" to !shoppingListItem.completed,
                "userId" to FirebaseAuth.getInstance().currentUser?.uid
            )
            shoppingListRef.document(shoppingListItem.id).set(shoppingListItemData)
            getShoppingList()
        } else {
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
    }

    override fun deleteShoppingListItem(shoppingListItem: ShoppingListItem){
        if(!mAuth.get()) {
            shoppingListRef.document(shoppingListItem.id).delete()
            getShoppingList()
        }else {
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






}



