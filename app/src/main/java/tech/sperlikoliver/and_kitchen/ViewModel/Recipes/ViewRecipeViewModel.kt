package tech.sperlikoliver.and_kitchen.ViewModel.Recipes

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import tech.sperlikoliver.and_kitchen.Model.Firebase.Entity.Recipe
import tech.sperlikoliver.and_kitchen.Model.Proxy.RecipesRepositoryProxy
import java.beans.PropertyChangeListener

class ViewRecipeViewModel(recipeId : String) : ViewModel(){
    private val repository : RecipesRepositoryProxy = RecipesRepositoryProxy()

    private val _recipeFlow = MutableStateFlow<Recipe>(Recipe())
    val recipe : StateFlow<Recipe> get() = _recipeFlow
    private fun setRecipe(recipe : Recipe){_recipeFlow.value = recipe}

    init{
        addRecipesListener()
        getRecipe(recipeId)
    }

    private fun addRecipesListener() {
        repository.addPropertyChangeListener(PropertyChangeListener{
                event ->
            if (event.propertyName == "recipe"){
                val newRecipe = event.newValue as Recipe
                setRecipe(newRecipe)
            }
        })
    }

    fun getRecipe(recipeId : String){
        repository.getRecipe(recipeId)
    }
}