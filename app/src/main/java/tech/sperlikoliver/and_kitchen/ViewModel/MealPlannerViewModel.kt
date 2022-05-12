package tech.sperlikoliver.and_kitchen.ViewModel

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import tech.sperlikoliver.and_kitchen.Model.Domain.Repository.MealPlannerEntry
import tech.sperlikoliver.and_kitchen.Model.Domain.Repository.Recipe
import tech.sperlikoliver.and_kitchen.Model.Repository.Implementation.MealPlannerRepositoryImpl
import tech.sperlikoliver.and_kitchen.Model.Repository.Implementation.RecipesRepositoryImpl
import tech.sperlikoliver.and_kitchen.Model.Repository.Interface.IMealPlannerRepository
import tech.sperlikoliver.and_kitchen.Model.Repository.Interface.IRecipesRepository
import java.beans.PropertyChangeListener
import java.text.DateFormat
import java.text.SimpleDateFormat
import java.util.*

class MealPlannerViewModel(mealPlannerEntryId : String = "") : ViewModel() {
    private val mealPlannerRepositoryImpl : IMealPlannerRepository = MealPlannerRepositoryImpl()
    private val recipesRepositoryImpl : IRecipesRepository = RecipesRepositoryImpl()

    private val _mealPlannerFlow = MutableStateFlow<MutableList<MealPlannerEntry>>(mutableListOf())
    val mealPlanner : StateFlow<MutableList<MealPlannerEntry>> get() = _mealPlannerFlow
    private fun setMealPlanner (mealPlanner : MutableList<MealPlannerEntry>){_mealPlannerFlow.value = mealPlanner}

    private val _mealPlannerEntryFlow = MutableStateFlow<MealPlannerEntry>(MealPlannerEntry())
    val mealPlannerEntry : StateFlow<MealPlannerEntry> get() = _mealPlannerEntryFlow
    private fun setMealPlannerEntry(mealPlannerEntry : MealPlannerEntry){_mealPlannerEntryFlow.value = mealPlannerEntry}


    private val _recipesFlow = MutableStateFlow<MutableList<Recipe>>(mutableListOf())
    val recipes : StateFlow<MutableList<Recipe>> get() = _recipesFlow
    private fun setRecipes (recipes : MutableList<Recipe>){ _recipesFlow.value = recipes }


    private val _mDateFlow = MutableStateFlow<String>("")
    private val _mTimeFlow = MutableStateFlow<String>("")
    private val _mSelectedTextFlow = MutableStateFlow<String>("")
    private val _recipeIdFlow = MutableStateFlow<String>("")

    val recipeId : StateFlow<String> get() = _recipeIdFlow
    val mDate : StateFlow<String> get() = _mDateFlow
    val mTime : StateFlow<String> get() = _mTimeFlow
    val selectedText : StateFlow<String> get() = _mSelectedTextFlow

    fun setRecipeId(recipeId : String){_recipeIdFlow.value=recipeId}
    fun setMDate(mDate : String){_mDateFlow.value = mDate}
    fun setMTime(mTime : String){_mTimeFlow.value = mTime}
    fun setSelectedText(selectedText : String){_mSelectedTextFlow.value = selectedText}


    init {
        addMealPlannerListener()
        addRecipesListener()
        recipesRepositoryImpl.getRecipes()
        mealPlannerRepositoryImpl.getMealPlanner()

        if (mealPlannerEntryId != ""){
            getMealPlannerEntry(mealPlannerEntryId)
        }
    }

    private fun addMealPlannerListener(){
        mealPlannerRepositoryImpl.addPropertyChangeListener(PropertyChangeListener{
            event ->
            if (event.propertyName == "meal_planner"){
                val newMealPlanner = event.newValue as MutableList<MealPlannerEntry>
                newMealPlanner.sortWith(compareByDescending{it.dateTime})
                setMealPlanner(newMealPlanner)
            }
            if (event.propertyName == "meal_planner_entry"){

                val newMealPlannerEntry = event.newValue as MealPlannerEntry
                for (recipe in recipes.value){
                    if(recipe.id == newMealPlannerEntry.recipeId){
                        setSelectedText(recipe.name)
                    }
                }
                setMealPlannerEntry(newMealPlannerEntry)
                val date : Date = Date(newMealPlannerEntry.dateTime * 1000)
                val dateFormat : DateFormat = SimpleDateFormat("dd/MM/yyyy")
                val timeFormat : DateFormat = SimpleDateFormat("HH:mm")
                val formattedDate : String = dateFormat.format(date)
                val formattedTime : String = timeFormat.format(date)

                setMDate(formattedDate)
                setMTime(formattedTime)
                setRecipeId(newMealPlannerEntry.recipeId)
            }
        })
    }

    private fun addRecipesListener(){
        recipesRepositoryImpl.addPropertyChangeListener(PropertyChangeListener {
            event ->
            if(event.propertyName == "recipes") {
                setRecipes(event.newValue as MutableList<Recipe>)
            }
        })
    }

    fun getMealPlannerEntry(mealPlannerEntryId : String){
        mealPlannerRepositoryImpl.getMealPlannerEntry(mealPlannerEntryId)
    }

    fun createMealPlannerEntry(mealPlannerEntry : MealPlannerEntry){
        mealPlannerRepositoryImpl.createMealPlannerEntry(mealPlannerEntry)
    }

    fun editMealPlannerEntry(mealPlannerEntry: MealPlannerEntry){
        mealPlannerRepositoryImpl.editMealPlannerEntry(mealPlannerEntry)
    }

    fun deleteMealPlannerEntry(mealPlannerEntry: MealPlannerEntry){
        mealPlannerRepositoryImpl.deleteMealPlannerEntry(mealPlannerEntry)
    }


}