package tech.sperlikoliver.and_kitchen.Model

import android.util.Log
import android.util.LogPrinter
import android.util.Property
import androidx.compose.animation.core.updateTransition
import com.google.common.collect.ImmutableList
import com.google.firebase.database.*
import com.google.firebase.database.ktx.database
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.MetadataChanges
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import tech.sperlikoliver.and_kitchen.Model.Domain.ShoppingListItem
import kotlin.properties.Delegates
import kotlin.reflect.KProperty

class Repository : PropertyChangeAware() {

    private val database: FirebaseFirestore = Firebase.firestore
    private val shoppingListRef = database.collection("shopping_list")

    init {
        shoppingListRef.addSnapshotListener (MetadataChanges.EXCLUDE) { snapshot, e ->
            if (snapshot != null && snapshot.documents.isNotEmpty()) {
                var shoppingList: MutableList<ShoppingListItem> = mutableListOf()

                for (document in snapshot) {
                    var shoppingListItem = ShoppingListItem(id = document.id, document.data["name"] as String, completed = document.data["completed"] as Boolean)
                    shoppingList.add(shoppingListItem)
                }
                updateShoppingList(ImmutableList.copyOf(shoppingList))
            } else if (snapshot != null && snapshot.documents.isEmpty()){
                var shoppingList: MutableList<ShoppingListItem> = mutableListOf()
                updateShoppingList(ImmutableList.copyOf(shoppingList))
            } else if (e != null) {
                Log.d("Firebase error: ", "Cannot retrieve data")
            } else {
                Log.d("Snapshot: ", "Current snapshot is null")
            }
        }
    }

    private fun updateShoppingList (shoppingList : ImmutableList<ShoppingListItem>) {

        propertyChangeSupport.firePropertyChange("shopping_list", null, shoppingList)
    }

    fun createShoppingListItem(shoppingListItem : ShoppingListItem){
        val data = hashMapOf(
            "name" to shoppingListItem.name,
            "completed" to shoppingListItem.completed
        )
        shoppingListRef.add(data)
    }

    fun deleteShoppingListItem(shoppingListItem: ShoppingListItem){
        shoppingListRef.document(shoppingListItem.id).delete()
    }

    fun editShoppingListItem(shoppingListItem: ShoppingListItem){

        val data = hashMapOf(
            "name" to shoppingListItem.name,
            "completed" to !shoppingListItem.completed
        )
        shoppingListRef.document(shoppingListItem.id).set(data)
    }
}



