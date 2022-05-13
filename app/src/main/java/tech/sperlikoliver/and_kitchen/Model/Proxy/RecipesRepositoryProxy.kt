package tech.sperlikoliver.and_kitchen.Model.Proxy

import tech.sperlikoliver.and_kitchen.Model.App.AnonymousAuth
import tech.sperlikoliver.and_kitchen.Model.Firebase.Entity.Recipe
import tech.sperlikoliver.and_kitchen.Model.Firebase.Repository.RecipesRepositoryFirebase
import tech.sperlikoliver.and_kitchen.Model.Interface.IRecipesRepository
import tech.sperlikoliver.and_kitchen.Model.Retrofit.Repository.RecipesRepositoryRetrofit
import tech.sperlikoliver.and_kitchen.Model.Room.Repository.RecipesRepositoryRoom
import java.beans.PropertyChangeListener
import java.beans.PropertyChangeSupport

class RecipesRepositoryProxy : IRecipesRepository {
    private val recipesRepositoryFirebase : IRecipesRepository = RecipesRepositoryFirebase()
    private val recipesRepositoryRoom : IRecipesRepository = RecipesRepositoryRoom()
    private val recipesRepositoryRetrofit : RecipesRepositoryRetrofit = RecipesRepositoryRetrofit()
    override val propertyChangeSupport : PropertyChangeSupport = PropertyChangeSupport(this)

    init {
        addRecipesListener()
    }

    private fun addRecipesListener(){
        recipesRepositoryFirebase.addPropertyChangeListener(PropertyChangeListener {
            event ->
            if (event.propertyName == "recipes"){
                propertyChangeSupport.firePropertyChange("recipes", null, event.newValue)
            }
            if (event.propertyName == "recipe"){
                propertyChangeSupport.firePropertyChange("recipe", null, event.newValue)
            }
        })
        recipesRepositoryRoom.addPropertyChangeListener(PropertyChangeListener {
                event ->
            if (event.propertyName == "recipes"){
                propertyChangeSupport.firePropertyChange("recipes", null, event.newValue)
            }
            if (event.propertyName == "recipe"){
                propertyChangeSupport.firePropertyChange("recipe", null, event.newValue)
            }
        })
        recipesRepositoryRetrofit.addPropertyChangeListener(PropertyChangeListener {
                event ->
            if (event.propertyName == "generated_recipe"){
                propertyChangeSupport.firePropertyChange("generated_recipe", null, event.newValue)
            }
        })
    }
    override fun getRecipes() {
        if (!AnonymousAuth.get()){
            recipesRepositoryFirebase.getRecipes()
        } else {
            recipesRepositoryRoom.getRecipes()
        }
    }

    override fun getRecipe(recipeId: String) {
        if (!AnonymousAuth.get()){
            recipesRepositoryFirebase.getRecipe(recipeId)
        } else {
            recipesRepositoryRoom.getRecipe(recipeId)
        }
    }

    override fun createRecipe(recipe: Recipe) {
        if (!AnonymousAuth.get()){
            recipesRepositoryFirebase.createRecipe(recipe)
        }else {
            recipesRepositoryRoom.createRecipe(recipe)
        }
    }

    override fun editRecipe(recipe: Recipe) {
        if (!AnonymousAuth.get()){
            recipesRepositoryFirebase.editRecipe(recipe)
        } else {
            recipesRepositoryRoom.editRecipe(recipe)
        }
    }

    override fun deleteRecipe(recipe: Recipe) {
        if(!AnonymousAuth.get()){
            recipesRepositoryFirebase.deleteRecipe(recipe)
        } else {
            recipesRepositoryRoom.deleteRecipe(recipe)
        }
    }

    fun getRandomRecipe(){
        recipesRepositoryRetrofit.getRandomRecipe()
    }
}