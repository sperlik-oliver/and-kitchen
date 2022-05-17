package tech.sperlikoliver.and_kitchen.Model.Room.Repository

import android.util.Log
import kotlinx.coroutines.Delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import tech.sperlikoliver.and_kitchen.Model.Firebase.Entity.Recipe
import tech.sperlikoliver.and_kitchen.Model.Interface.IRecipesRepository
import tech.sperlikoliver.and_kitchen.Model.Room.Entity.RecipeRoom
import tech.sperlikoliver.and_kitchen.Model.Room.Database.KitchenDatabase
import java.beans.PropertyChangeSupport

class RecipesRepositoryRoom : IRecipesRepository{
    override val propertyChangeSupport: PropertyChangeSupport = PropertyChangeSupport(this)
    private val recipeDao = KitchenDatabase.get().recipeDao()

    override fun getRecipes() {
        val propertyName = "recipes"
        val recipes: MutableList<Recipe> = mutableListOf()
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

    override fun getRecipe(recipeId: String) {
        val propertyName = "recipe"
        runBlocking { launch {
            val retrievedRecipe : RecipeRoom = recipeDao.getRecipe(recipeId.toLong())
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

    override fun createRecipe(recipe: Recipe) {
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

    override fun editRecipe(recipe: Recipe) {
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

    override fun deleteRecipe(recipe: Recipe) {
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