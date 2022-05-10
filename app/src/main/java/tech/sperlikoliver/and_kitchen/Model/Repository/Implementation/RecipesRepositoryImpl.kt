package tech.sperlikoliver.and_kitchen.Model.Repository.Implementation

import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import tech.sperlikoliver.and_kitchen.Model.Domain.Recipe
import tech.sperlikoliver.and_kitchen.Model.Repository.Interface.IRecipesRepository
import tech.sperlikoliver.and_kitchen.Model.Utility.PropertyChangeAware
import java.beans.PropertyChangeSupport

class RecipesRepositoryImpl : IRecipesRepository {

    override val propertyChangeSupport: PropertyChangeSupport = PropertyChangeSupport(this)
    private val database: FirebaseFirestore = Firebase.firestore
    private val recipesRef = database.collection("recipes")

    init{
        addRecipesListener()
    }

    private fun addRecipesListener(){
        TODO("Not yet implemented")
    }

    override fun createRecipe(recipe: Recipe) {
        TODO("Not yet implemented")
    }

    override fun editRecipe(recipe: Recipe) {
        TODO("Not yet implemented")
    }

    override fun deleteRecipe(recipe: Recipe) {
        TODO("Not yet implemented")
    }




}