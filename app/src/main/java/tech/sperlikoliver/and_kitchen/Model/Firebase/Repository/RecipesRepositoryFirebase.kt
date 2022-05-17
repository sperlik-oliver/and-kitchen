package tech.sperlikoliver.and_kitchen.Model.Firebase.Repository

import android.util.Log
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import tech.sperlikoliver.and_kitchen.Model.App.PropertyChangeAware
import tech.sperlikoliver.and_kitchen.Model.Interface.IRecipesRepository
import tech.sperlikoliver.and_kitchen.Model.Firebase.Entity.Recipe
import java.beans.PropertyChangeSupport

private const val TAG : String = "RecipesRepositoryImpl"

class RecipesRepositoryFirebase : IRecipesRepository {

    override val propertyChangeSupport: PropertyChangeSupport = PropertyChangeSupport(this)
    private val database: FirebaseFirestore = Firebase.firestore
    private val recipesRef = database.collection("recipes")

    override fun getRecipes() {
        val propertyName = "recipes"
        val recipes: MutableList<Recipe> = mutableListOf()
            recipesRef.whereEqualTo("userId", FirebaseAuth.getInstance().currentUser?.uid).get()
                .addOnCompleteListener() { recipeSnapshot ->
                    if (!recipeSnapshot.isSuccessful) {
                        recipeSnapshot.exception?.message?.let { Log.e(TAG, it) }
                        return@addOnCompleteListener
                    }
                    val recipeResult = recipeSnapshot.result
                    if (recipeResult == null) {
                        Log.e(TAG, "Recipe result is null")
                        return@addOnCompleteListener
                    }
                    if (recipeResult.isEmpty) {
                        propertyChangeSupport.firePropertyChange(propertyName, null, recipes)
                        return@addOnCompleteListener
                    }
                    for (recipeDocument in recipeResult) {
                        val recipe = Recipe(
                            id = recipeDocument.id,
                            name = recipeDocument.data["name"] as String,
                            directions = recipeDocument.data["directions"] as String,
                            category = recipeDocument.data["category"] as String,
                            description = recipeDocument.data["description"] as String,
                            userId = recipeDocument.data["userId"] as String,
                            ingredients = recipeDocument.data["ingredients"] as MutableList<String>
                        )
                        recipes.add(recipe)
                    }
                    propertyChangeSupport.firePropertyChange(propertyName, null, recipes)
                }
    }

    override fun getRecipe(recipeId : String){
        val propertyName = "recipe"
            recipesRef.document(recipeId).get().addOnCompleteListener() { recipeSnapshot ->

                if (!recipeSnapshot.isSuccessful) {
                    recipeSnapshot.exception?.message?.let { Log.e(TAG, it) }
                    return@addOnCompleteListener
                }
                val recipeResult = recipeSnapshot.result
                if (recipeResult == null) {
                    Log.e(TAG, "Recipe result is null")
                    return@addOnCompleteListener
                }
                if (recipeResult.data.isNullOrEmpty()) {
                    Log.e(TAG, "Recipe result data is null or empty")
                    return@addOnCompleteListener
                }
                val recipe = Recipe(
                    id = recipeResult.id,
                    name = recipeResult.data!!["name"] as String,
                    directions = recipeResult.data!!["directions"] as String,
                    category = recipeResult.data!!["category"] as String,
                    description = recipeResult.data!!["description"] as String,
                    userId = recipeResult.data!!["userId"] as String,
                    ingredients = recipeResult.data!!["ingredients"] as MutableList<String>
                )
                propertyChangeSupport.firePropertyChange(propertyName, null, recipe)
            }
    }


    override fun createRecipe(recipe: Recipe) {
            val recipeData = hashMapOf(
                "name" to recipe.name,
                "directions" to recipe.directions,
                "category" to recipe.category,
                "description" to recipe.description,
                "userId" to FirebaseAuth.getInstance().currentUser?.uid,
                "ingredients" to recipe.ingredients
            )
            recipesRef.add(recipeData)
            getRecipes()
    }

    override fun editRecipe(recipe: Recipe) {
            val recipeData = hashMapOf(
                "name" to recipe.name,
                "directions" to recipe.directions,
                "category" to recipe.category,
                "description" to recipe.description,
                "userId" to FirebaseAuth.getInstance().currentUser?.uid,
                "ingredients" to recipe.ingredients
            )
            recipesRef.document(recipe.id).set(recipeData)
            getRecipes()
    }

    override fun deleteRecipe(recipe: Recipe) {
            recipesRef.document(recipe.id).delete()
            getRecipes()
    }




}