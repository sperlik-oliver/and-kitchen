package tech.sperlikoliver.and_kitchen.ViewModel.MealPlanner

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import tech.sperlikoliver.and_kitchen.Model.Firebase.Entity.MealPlannerEntry
import tech.sperlikoliver.and_kitchen.Model.Proxy.MealPlannerRepositoryProxy
import tech.sperlikoliver.and_kitchen.Model.Proxy.RecipesRepositoryProxy
import java.beans.PropertyChangeListener
import java.text.DateFormat
import java.text.SimpleDateFormat
import java.util.*

class ViewMealPlannerViewModel(mealPlannerEntryId : String) : ViewModel() {
    private val mealPlannerRepository : MealPlannerRepositoryProxy = MealPlannerRepositoryProxy()

    private val _mealPlannerEntryFlow = MutableStateFlow<MealPlannerEntry>(MealPlannerEntry())
    val mealPlannerEntry : StateFlow<MealPlannerEntry> get() = _mealPlannerEntryFlow
    private fun setMealPlannerEntry(mealPlannerEntry : MealPlannerEntry){_mealPlannerEntryFlow.value = mealPlannerEntry}

    init {
        addMealPlannerListener()
        mealPlannerRepository.getMealPlannerEntry(mealPlannerEntryId)
    }

    private fun addMealPlannerListener(){
        mealPlannerRepository.addPropertyChangeListener(PropertyChangeListener{
                event ->
            if (event.propertyName == "meal_planner_entry"){
                val newMealPlannerEntry = event.newValue as MealPlannerEntry
                setMealPlannerEntry(newMealPlannerEntry)
            }
        })
    }
}