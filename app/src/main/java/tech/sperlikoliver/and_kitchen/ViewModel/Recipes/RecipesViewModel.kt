package tech.sperlikoliver.and_kitchen.ViewModel.Recipes

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import tech.sperlikoliver.and_kitchen.Model.Firebase.Entity.Recipe
import tech.sperlikoliver.and_kitchen.Model.Proxy.RecipesRepositoryProxy
import java.beans.PropertyChangeListener

class RecipesViewModel : ViewModel() {
    private val repository : RecipesRepositoryProxy = RecipesRepositoryProxy()

    private val _recipesFlow = MutableStateFlow<MutableList<Recipe>>(mutableListOf())
    val recipes : StateFlow<MutableList<Recipe>> get() = _recipesFlow
    private fun setRecipes (recipes : MutableList<Recipe>){ _recipesFlow.value = recipes }

    init {
        addRecipesListener()
        repository.getRecipes()
    }

    private fun addRecipesListener() {
        repository.addPropertyChangeListener(PropertyChangeListener{
                event ->
            if(event.propertyName == "recipes") {
                setRecipes(event.newValue as MutableList<Recipe>)
            }
        })
    }

    fun deleteRecipe(recipe : Recipe){
        repository.deleteRecipe(recipe)
    }
}