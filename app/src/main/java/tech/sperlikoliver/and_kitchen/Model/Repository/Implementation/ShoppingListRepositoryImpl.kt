package tech.sperlikoliver.and_kitchen.Model.Repository.Implementation

import android.util.Log
import com.google.common.collect.ImmutableList
import com.google.firebase.auth.EmailAuthProvider
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.MetadataChanges
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import tech.sperlikoliver.and_kitchen.Model.Domain.ShoppingListItem
import tech.sperlikoliver.and_kitchen.Model.Repository.Interface.IShoppingListRepository
import tech.sperlikoliver.and_kitchen.Model.Utility.PropertyChangeAware
import java.beans.PropertyChangeSupport

class ShoppingListRepositoryImpl : IShoppingListRepository {


    override val propertyChangeSupport: PropertyChangeSupport = PropertyChangeSupport(this)
    private val database: FirebaseFirestore = Firebase.firestore
    private val shoppingListRef = database.collection("shopping_list")


    init {
        addShoppingListListener()
    }

    private fun addShoppingListListener(){
        shoppingListRef.whereEqualTo("userId", FirebaseAuth.getInstance().currentUser?.uid).addSnapshotListener (MetadataChanges.EXCLUDE) { snapshot, e ->
            if (snapshot != null && snapshot.documents.isNotEmpty()) {
                var shoppingList: MutableList<ShoppingListItem> = mutableListOf()

                for (document in snapshot) {
                    var shoppingListItem = ShoppingListItem(id = document.id, document.data["name"] as String, completed = document.data["completed"] as Boolean, userId = document.data["userId"] as String)
                    shoppingList.add(shoppingListItem)
                }
                propertyChangeSupport.firePropertyChange("shopping_list", null, shoppingList)
            } else if (snapshot != null && snapshot.documents.isEmpty()){
                var shoppingList: MutableList<ShoppingListItem> = mutableListOf()
                propertyChangeSupport.firePropertyChange("shopping_list", null, shoppingList)
            } else if (e != null) {
                Log.d("Firebase error: ", "Cannot retrieve data")
            } else {
                Log.d("Snapshot: ", "Current snapshot is null")
            }
        }
    }

    override fun createShoppingListItem(shoppingListItem : ShoppingListItem){
        val data = hashMapOf(
            "name" to shoppingListItem.name,
            "completed" to shoppingListItem.completed,
            "userId" to FirebaseAuth.getInstance().currentUser?.uid
        )
        shoppingListRef.add(data)
    }

    override fun editShoppingListItem(shoppingListItem: ShoppingListItem){
        val data = hashMapOf(
            "name" to shoppingListItem.name,
            "completed" to !shoppingListItem.completed,
            "userId" to FirebaseAuth.getInstance().currentUser?.uid
        )
        shoppingListRef.document(shoppingListItem.id).set(data)
    }

    override fun deleteShoppingListItem(shoppingListItem: ShoppingListItem){
        shoppingListRef.document(shoppingListItem.id).delete()
    }






}



