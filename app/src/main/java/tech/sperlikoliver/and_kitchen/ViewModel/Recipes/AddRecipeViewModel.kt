package tech.sperlikoliver.and_kitchen.ViewModel.Recipes

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import tech.sperlikoliver.and_kitchen.Model.Firebase.Entity.Recipe
import tech.sperlikoliver.and_kitchen.Model.Proxy.RecipesRepositoryProxy
import java.beans.PropertyChangeListener

class AddRecipeViewModel : ViewModel() {

    private val repository : RecipesRepositoryProxy = RecipesRepositoryProxy()

    private val _generatedRecipeFlow = MutableStateFlow<Recipe>(Recipe())
    val generatedRecipe : StateFlow<Recipe> get() = _generatedRecipeFlow
    fun setGeneratedRecipe(recipe : Recipe){_generatedRecipeFlow.value = recipe}

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
    }

    private fun addRecipesListener() {
        repository.addPropertyChangeListener(PropertyChangeListener{
                event ->
            if(event.propertyName == "generated_recipe"){
                val newRecipe = event.newValue as Recipe
                setGeneratedRecipe(newRecipe)
            }
        })
    }

    fun createRecipe(recipe : Recipe){
        repository.createRecipe(recipe)
    }

    fun getRandomRecipe(){
        repository.getRandomRecipe()
    }

}