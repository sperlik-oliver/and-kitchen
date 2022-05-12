package tech.sperlikoliver.and_kitchen.Model.Repository.Implementation

import android.util.Log
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import tech.sperlikoliver.and_kitchen.Model.Domain.DAO.RecipeRoom
import tech.sperlikoliver.and_kitchen.Model.Domain.Repository.Recipe
import tech.sperlikoliver.and_kitchen.Model.KitchenDatabase
import tech.sperlikoliver.and_kitchen.Model.Repository.Interface.IRecipesRepository
import tech.sperlikoliver.and_kitchen.Model.Utility.mAuth
import java.beans.PropertyChangeSupport

private const val TAG : String = "RecipesRepositoryImpl"

class RecipesRepositoryImpl : IRecipesRepository {

    override val propertyChangeSupport: PropertyChangeSupport = PropertyChangeSupport(this)
    private val database: FirebaseFirestore = Firebase.firestore
    private val recipesRef = database.collection("recipes")
    private val recipeDao = KitchenDatabase.get().recipeDao()

    override fun getRecipes() {
        val propertyName = "recipes"
        val recipes: MutableList<Recipe> = mutableListOf()
        if(!mAuth.get()) {
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
        } else {
            runBlocking { launch{
                val retrievedRecipes = recipeDao.getRecipes()
                for (retrievedRecipe in retrievedRecipes){
                    val recipe = Recipe(
                        id = retrievedRecipe.id.toString(),
                        name = retrievedRecipe.name,
                        directions = retrievedRecipe.directions,
                        category = retrievedRecipe.category,
                        description = retrievedRecipe.description,
                        userId = "",
                        ingredients = retrievedRecipe.ingredients
                    )
                    recipes.add(recipe)
                }
                propertyChangeSupport.firePropertyChange(propertyName, null, recipes)
            } }
        }
    }

    override fun getRecipe(recipeId : String){
        val propertyName = "recipe"
        if (!mAuth.get()) {
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
        } else{
            runBlocking { launch {
                val retrievedRecipe = recipeDao.getRecipe(recipeId.toLong())
                val recipe = Recipe(
                    id = retrievedRecipe.id.toString(),
                    name = retrievedRecipe.name,
                    directions = retrievedRecipe.directions,
                    category = retrievedRecipe.category,
                    description = retrievedRecipe.description,
                    userId = "",
                    ingredients = retrievedRecipe.ingredients
                )
                propertyChangeSupport.firePropertyChange(propertyName, null, recipe)
            } }
        }
    }


    override fun createRecipe(recipe: Recipe) {
        if(!mAuth.get()) {
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
        } else {
            runBlocking { launch{
                val recipeRoom = RecipeRoom(
                    id = 0,
                    name = recipe.name,
                    directions = recipe.directions,
                    category = recipe.category,
                    description = recipe.description,
                    ingredients = recipe.ingredients
                )
                recipeDao.createRecipe(recipeRoom)
                getRecipes()
            } }
        }
    }

    override fun editRecipe(recipe: Recipe) {
        if(!mAuth.get()) {
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
        } else{
            runBlocking { launch{
                val recipeRoom = RecipeRoom(
                    id = recipe.id.toLong(),
                    name = recipe.name,
                    directions = recipe.directions,
                    category = recipe.category,
                    description = recipe.description,
                    ingredients = recipe.ingredients
                )
                recipeDao.editRecipe(recipeRoom)
                getRecipes()
            } }
        }
    }

    override fun deleteRecipe(recipe: Recipe) {
        if(!mAuth.get()) {
            recipesRef.document(recipe.id).delete()
            getRecipes()
        } else {
            runBlocking { launch{
                val recipeRoom = RecipeRoom(
                    id = recipe.id.toLong(),
                    name = recipe.name,
                    directions = recipe.directions,
                    category = recipe.category,
                    description = recipe.description,
                    ingredients = recipe.ingredients
                )
                recipeDao.deleteRecipe(recipeRoom)
                getRecipes()
            } }
        }
    }




}