package tech.sperlikoliver.and_kitchen.ViewModel

import android.util.Log
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import tech.sperlikoliver.and_kitchen.Model.Domain.Recipe
import tech.sperlikoliver.and_kitchen.Model.Repository.Implementation.RecipesRepositoryImpl
import tech.sperlikoliver.and_kitchen.Model.Repository.Interface.IRecipesRepository
import java.beans.PropertyChangeListener

private const val TAG : String = "RecipesViewModel"

class RecipesViewModel(recipeId : String = "") : ViewModel() {

    private val repository : IRecipesRepository = RecipesRepositoryImpl()
    
    private val _recipesFlow = MutableStateFlow<MutableList<Recipe>>(mutableListOf())
    val recipes : StateFlow<MutableList<Recipe>> get() = _recipesFlow
    private fun setRecipes (recipes : MutableList<Recipe>){ _recipesFlow.value = recipes }

    private val _recipeFlow = MutableStateFlow<Recipe>(Recipe())
    val recipe : StateFlow<Recipe> get() = _recipeFlow
    private fun setRecipe(recipe : Recipe){_recipeFlow.value = recipe}

    private val _nameFieldFlow = MutableStateFlow("")
    private val _categoryFieldFlow = MutableStateFlow("")
    private val _descriptionFieldFlow = MutableStateFlow("")
    private val _ingredientsFieldFlow = MutableStateFlow("")
    private val _directionsFieldFlow = MutableStateFlow("")
    private val _addedIngredientsFlow = MutableStateFlow<MutableList<String>>(mutableListOf())

    val nameField : StateFlow<String> get() = _nameFieldFlow
    val categoryField : StateFlow<String> get() = _categoryFieldFlow
    val descriptionField : StateFlow<String> get() = _descriptionFieldFlow
    val ingredientsField : StateFlow<String> get() = _ingredientsFieldFlow
    val directionsField : StateFlow<String> get() = _directionsFieldFlow
    val addedIngredients : StateFlow<MutableList<String>> get() = _addedIngredientsFlow

    fun setNameField(name : String){_nameFieldFlow.value = name}
    fun setCategoryField(category : String){_categoryFieldFlow.value = category}
    fun setDescriptionField(description : String){_descriptionFieldFlow.value = description}
    fun setIngredientsField(ingredients : String){_ingredientsFieldFlow.value = ingredients}
    fun setDirectionsField(directions : String){_directionsFieldFlow.value = directions}
    fun setAddedIngredients(addedIngredients : MutableList<String>){_addedIngredientsFlow.value = addedIngredients}


    init{
        addRecipesListener()
        repository.getRecipes()
        if (recipeId != ""){
            getRecipe(recipeId)
        }
    }

    private fun addRecipesListener() {
        repository.addPropertyChangeListener(PropertyChangeListener{
            event ->
            if(event.propertyName == "recipes") {
                setRecipes(event.newValue as MutableList<Recipe>)
            }
            if (event.propertyName == "recipe"){
                val newRecipe = event.newValue as Recipe
                setRecipe(newRecipe)
                setNameField(newRecipe.name)
                setCategoryField(newRecipe.category)
                setDescriptionField(newRecipe.description)
                setIngredientsField("")
                setDirectionsField(newRecipe.directions)
                setAddedIngredients(newRecipe.ingredients)
            }
        })
    }

    fun getRecipe(recipeId : String){
        repository.getRecipe(recipeId)
    }

    fun createRecipe(recipe : Recipe){
        repository.createRecipe(recipe)
    }
    fun editRecipe(recipe : Recipe){
        repository.editRecipe(recipe)
    }
    fun deleteRecipe(recipe : Recipe){
        repository.deleteRecipe(recipe)
    }


    


}