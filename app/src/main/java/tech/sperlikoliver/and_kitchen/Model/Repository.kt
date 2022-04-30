package tech.sperlikoliver.and_kitchen.Model

import android.util.Log
import android.util.LogPrinter
import android.util.Property
import androidx.compose.animation.core.updateTransition
import com.google.firebase.database.*
import com.google.firebase.database.ktx.database
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import tech.sperlikoliver.and_kitchen.Model.Domain.ShoppingListItem
import kotlin.properties.Delegates
import kotlin.reflect.KProperty

class Repository : PropertyChangeAware() {

    private val database: FirebaseFirestore = Firebase.firestore
    private val shoppingListRef = database.collection("shopping_list")

    init {
        shoppingListRef.addSnapshotListener { snapshot, e ->
            if (snapshot != null && snapshot.documents.isNotEmpty()) {
                var shoppingList: MutableList<ShoppingListItem> = mutableListOf()
                for (document in snapshot) {
                    var shoppingListItem = ShoppingListItem(document.data["name"] as String)
                    Log.e("Model", shoppingListItem.name)
                    shoppingList.add(shoppingListItem)
                }
                updateShoppingList(shoppingList)
            } else if (snapshot != null && snapshot.documents.isEmpty()){
                var shoppingList: MutableList<ShoppingListItem> = mutableListOf()
                updateShoppingList(shoppingList)
            } else if (e != null) {
                Log.d("Firebase error: ", "Cannot retrieve data")
            } else {
                Log.d("Snapshot: ", "Current snapshot is null")
            }
        }
    }

    private fun updateShoppingList (shoppingList : List<ShoppingListItem>) {
        Log.i("Repository", shoppingList.toString())
        propertyChangeSupport.firePropertyChange("shopping_list", null, shoppingList)
    }
}



