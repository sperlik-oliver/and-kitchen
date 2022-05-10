package tech.sperlikoliver.and_kitchen.Model.Repository

import android.util.Log
import com.google.common.collect.ImmutableList
import com.google.firebase.auth.EmailAuthProvider
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.MetadataChanges
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import tech.sperlikoliver.and_kitchen.Model.Domain.ShoppingListItem
import tech.sperlikoliver.and_kitchen.Model.Utility.PropertyChangeAware

class ShoppingListRepositoryImpl : PropertyChangeAware() {

    private val database: FirebaseFirestore = Firebase.firestore
    //refs
    private val shoppingListRef = database.collection("shopping_list")
    private val recipesRef = database.collection("recipes")


    init {
        //shopping list

        shoppingListRef.whereEqualTo("userId", FirebaseAuth.getInstance().currentUser?.uid).addSnapshotListener (MetadataChanges.EXCLUDE) { snapshot, e ->
            if (snapshot != null && snapshot.documents.isNotEmpty()) {
                var shoppingList: MutableList<ShoppingListItem> = mutableListOf()

                for (document in snapshot) {
                    var shoppingListItem = ShoppingListItem(id = document.id, document.data["name"] as String, completed = document.data["completed"] as Boolean, userId = document.data["userId"] as String)
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
        //recipes
//        recipesRef.addSnapshotListener(MetadataChanges.EXCLUDE) {snapshot, e ->
//            if (snapshot != null && snapshot.documents.isNotEmpty()) {
//                var recipes : MutableList<Recipe> = mutableListOf()
//
//                for (document in snapshot){
//                    var recipe = Recipe(id = document.id, name = document.data["name"] as String, directions = document.data["directions"] as String, category = document.data["category"] as String, ingredients = )
//                }
//            }
//        }
    }
    //shopping list methods
    fun createShoppingListItem(shoppingListItem : ShoppingListItem){
        val data = hashMapOf(
            "name" to shoppingListItem.name,
            "completed" to shoppingListItem.completed,
            "userId" to FirebaseAuth.getInstance().currentUser?.uid
        )
        shoppingListRef.add(data)
    }
    fun deleteShoppingListItem(shoppingListItem: ShoppingListItem){
        shoppingListRef.document(shoppingListItem.id).delete()
    }
    fun editShoppingListItem(shoppingListItem: ShoppingListItem){
        val data = hashMapOf(
            "name" to shoppingListItem.name,
            "completed" to !shoppingListItem.completed,
            "userId" to FirebaseAuth.getInstance().currentUser?.uid
        )
        shoppingListRef.document(shoppingListItem.id).set(data)
    }
    private fun updateShoppingList (shoppingList : ImmutableList<ShoppingListItem>) {

        propertyChangeSupport.firePropertyChange("shopping_list", null, shoppingList)
    }

    //recipe methods



    //user methods
    fun signOut (){
        FirebaseAuth.getInstance().signOut()
    }

    fun deleteAccount(){
        FirebaseAuth.getInstance().currentUser?.delete()
        FirebaseAuth.getInstance().signOut()
    }

    fun changePassword(password: String, oldPassword : String, email : String){
        val credential = EmailAuthProvider.getCredential(email, oldPassword)
        FirebaseAuth.getInstance().currentUser!!.reauthenticate(credential).addOnSuccessListener { FirebaseAuth.getInstance().currentUser?.updatePassword(password) }
    }

    fun changeEmail(email : String, oldEmail : String, password : String){
        val credential = EmailAuthProvider.getCredential(oldEmail, password)
        FirebaseAuth.getInstance().currentUser!!.reauthenticate(credential).addOnSuccessListener { FirebaseAuth.getInstance().currentUser?.updateEmail(email)}
    }


}



