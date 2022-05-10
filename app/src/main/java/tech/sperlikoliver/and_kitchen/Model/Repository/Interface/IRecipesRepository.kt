package tech.sperlikoliver.and_kitchen.Model.Repository.Interface

import tech.sperlikoliver.and_kitchen.Model.Domain.Recipe
import tech.sperlikoliver.and_kitchen.Model.Utility.PropertyChangeAware

interface IRecipesRepository : PropertyChangeAware {

    fun createRecipe(recipe : Recipe)
    fun editRecipe(recipe : Recipe)
    fun deleteRecipe(recipe : Recipe)

}