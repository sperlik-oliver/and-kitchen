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

class EditMealPlannerViewModel(mealPlannerEntryId: String) : ViewModel() {
    private val mealPlannerRepository : MealPlannerRepositoryProxy = MealPlannerRepositoryProxy()
    private val recipesRepository : RecipesRepositoryProxy = RecipesRepositoryProxy()



    private val _recipesFlow = MutableStateFlow<MutableList<Recipe>>(mutableListOf())
    val recipes : StateFlow<MutableList<Recipe>> get() = _recipesFlow
    private fun setRecipes (recipes : MutableList<Recipe>){ _recipesFlow.value = recipes }

    private val _mDateFlow = MutableStateFlow<String>("")
    private val _mTimeFlow = MutableStateFlow<String>("")
    private val _mSelectedTextFlow = MutableStateFlow<String>("")
    val mDate : StateFlow<String> get() = _mDateFlow
    val mTime : StateFlow<String> get() = _mTimeFlow
    val selectedText : StateFlow<String> get() = _mSelectedTextFlow
    fun setMDate(mDate : String){_mDateFlow.value = mDate}
    fun setMTime(mTime : String){_mTimeFlow.value = mTime}
    fun setSelectedText(selectedText : String){_mSelectedTextFlow.value = selectedText}

    init {
        addMealPlannerListener()
        addRecipesListener()
        recipesRepository.getRecipes()
        getMealPlannerEntry(mealPlannerEntryId)
    }

    private fun addMealPlannerListener(){
        mealPlannerRepository.addPropertyChangeListener(PropertyChangeListener{
                event ->
            if (event.propertyName == "meal_planner_entry"){
                val newMealPlannerEntry = event.newValue as MealPlannerEntry
                for (recipe in recipes.value){
                    if(recipe.id == newMealPlannerEntry.recipeId){
                        setSelectedText(recipe.name)
                    }
                }

                val date : Date = Date(newMealPlannerEntry.dateTime * 1000)
                val dateFormat : DateFormat = SimpleDateFormat("dd/MM/yyyy")
                val timeFormat : DateFormat = SimpleDateFormat("HH:mm")
                val formattedDate : String = dateFormat.format(date)
                val formattedTime : String = timeFormat.format(date)

                setMDate(formattedDate)
                setMTime(formattedTime)
            }
        })
    }

    private fun addRecipesListener(){
        recipesRepository.addPropertyChangeListener(PropertyChangeListener {
                event ->
            if(event.propertyName == "recipes") {
                setRecipes(event.newValue as MutableList<Recipe>)
            }
        })
    }

    fun getMealPlannerEntry(mealPlannerEntryId : String){
        mealPlannerRepository.getMealPlannerEntry(mealPlannerEntryId)
    }

    fun editMealPlannerEntry(mealPlannerEntry: MealPlannerEntry){
        mealPlannerRepository.editMealPlannerEntry(mealPlannerEntry)
    }
}