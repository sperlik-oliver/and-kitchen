package tech.sperlikoliver.and_kitchen.Model.Repository.Interface

import tech.sperlikoliver.and_kitchen.Model.Domain.Repository.Recipe
import tech.sperlikoliver.and_kitchen.Model.Utility.PropertyChangeAware

interface IRecipesRepository : PropertyChangeAware {

    fun getRecipes()
    fun getRecipe(recipeId : String)
    fun createRecipe(recipe : Recipe)
    fun editRecipe(recipe : Recipe)
    fun deleteRecipe(recipe : Recipe)

}