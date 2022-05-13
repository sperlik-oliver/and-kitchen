package tech.sperlikoliver.and_kitchen.ViewModel.MealPlanner

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import tech.sperlikoliver.and_kitchen.Model.Firebase.Entity.MealPlannerEntry
import tech.sperlikoliver.and_kitchen.Model.Firebase.Entity.Recipe
import tech.sperlikoliver.and_kitchen.Model.Proxy.MealPlannerRepositoryProxy
import tech.sperlikoliver.and_kitchen.Model.Proxy.RecipesRepositoryProxy
import java.beans.PropertyChangeListener
import java.text.DateFormat
import java.text.SimpleDateFormat
import java.util.*

class AddMealPlannerViewModel : ViewModel(){
    private val mealPlannerRepository : MealPlannerRepositoryProxy = MealPlannerRepositoryProxy()
    private val recipesRepository : RecipesRepositoryProxy = RecipesRepositoryProxy()

    private val _recipesFlow = MutableStateFlow<MutableList<Recipe>>(mutableListOf())
    val recipes : StateFlow<MutableList<Recipe>> get() = _recipesFlow
    private fun setRecipes (recipes : MutableList<Recipe>){ _recipesFlow.value = recipes }

    private val _mDateFlow = MutableStateFlow<String>("")
    private val _mTimeFlow = MutableStateFlow<String>("")
    private val _mSelectedTextFlow = MutableStateFlow<String>("")
    private val _recipeIdFlow = MutableStateFlow<String>("")
    val mDate : StateFlow<String> get() = _mDateFlow
    val mTime : StateFlow<String> get() = _mTimeFlow
    val selectedText : StateFlow<String> get() = _mSelectedTextFlow
    val recipeId : StateFlow<String> get() = _recipeIdFlow
    fun setRecipeId(recipeId : String){_recipeIdFlow.value=recipeId}
    fun setMDate(mDate : String){_mDateFlow.value = mDate}
    fun setMTime(mTime : String){_mTimeFlow.value = mTime}
    fun setSelectedText(selectedText : String){_mSelectedTextFlow.value = selectedText}

    init {
        addRecipesListener()
        recipesRepository.getRecipes()
    }

    private fun addRecipesListener(){
        recipesRepository.addPropertyChangeListener(PropertyChangeListener {
                event ->
            if(event.propertyName == "recipes") {
                setRecipes(event.newValue as MutableList<Recipe>)
            }
        })
    }

    fun createMealPlannerEntry(mealPlannerEntry : MealPlannerEntry){
        mealPlannerRepository.createMealPlannerEntry(mealPlannerEntry)
    }
}