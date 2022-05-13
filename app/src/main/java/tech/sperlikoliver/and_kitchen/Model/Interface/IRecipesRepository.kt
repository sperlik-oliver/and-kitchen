package tech.sperlikoliver.and_kitchen.Model.Interface

import tech.sperlikoliver.and_kitchen.Model.Firebase.Entity.Recipe
import tech.sperlikoliver.and_kitchen.Model.App.PropertyChangeAware

interface IRecipesRepository : PropertyChangeAware{
    fun getRecipes()
    fun getRecipe(recipeId : String)
    fun createRecipe(recipe : Recipe)
    fun editRecipe(recipe : Recipe)
    fun deleteRecipe(recipe : Recipe)
}