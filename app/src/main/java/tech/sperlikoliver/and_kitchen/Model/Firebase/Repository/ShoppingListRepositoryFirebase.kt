package tech.sperlikoliver.and_kitchen.Model.Firebase.Repository

import android.util.Log
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import tech.sperlikoliver.and_kitchen.Model.App.PropertyChangeAware
import tech.sperlikoliver.and_kitchen.Model.Interface.IShoppingListRepository
import tech.sperlikoliver.and_kitchen.Model.Firebase.Entity.ShoppingListItem
import java.beans.PropertyChangeSupport

private const val TAG : String = "ShoppingListRepositoryImpl"

class ShoppingListRepositoryFirebase : IShoppingListRepository {


    override val propertyChangeSupport: PropertyChangeSupport = PropertyChangeSupport(this)
    private val database: FirebaseFirestore = Firebase.firestore
    private val shoppingListRef = database.collection("shopping_list")


    override fun getShoppingList(){
        val propertyName = "shopping_list"
        val shoppingList: MutableList<ShoppingListItem> = mutableListOf()
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
    }

    override fun createShoppingListItem(shoppingListItem : ShoppingListItem){
            val shoppingListItemData = hashMapOf(
                "name" to shoppingListItem.name,
                "completed" to shoppingListItem.completed,
                "userId" to FirebaseAuth.getInstance().currentUser?.uid
            )
            shoppingListRef.add(shoppingListItemData)
            getShoppingList()
    }

    override fun editShoppingListItem(shoppingListItem: ShoppingListItem){
            val shoppingListItemData = hashMapOf(
                "name" to shoppingListItem.name,
                "completed" to !shoppingListItem.completed,
                "userId" to FirebaseAuth.getInstance().currentUser?.uid
            )
            shoppingListRef.document(shoppingListItem.id).set(shoppingListItemData)
            getShoppingList()
    }

    override fun deleteShoppingListItem(shoppingListItem: ShoppingListItem){

            shoppingListRef.document(shoppingListItem.id).delete()
            getShoppingList()

    }
}



