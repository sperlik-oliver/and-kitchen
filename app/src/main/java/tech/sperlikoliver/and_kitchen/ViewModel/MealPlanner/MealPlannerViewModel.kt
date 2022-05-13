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

class MealPlannerViewModel : ViewModel(){
    private val mealPlannerRepository : MealPlannerRepositoryProxy = MealPlannerRepositoryProxy()

    private val _mealPlannerFlow = MutableStateFlow<MutableList<MealPlannerEntry>>(mutableListOf())
    val mealPlanner : StateFlow<MutableList<MealPlannerEntry>> get() = _mealPlannerFlow
    private fun setMealPlanner (mealPlanner : MutableList<MealPlannerEntry>){_mealPlannerFlow.value = mealPlanner}

    init {
        addMealPlannerListener()
        mealPlannerRepository.getMealPlanner()
    }

    private fun addMealPlannerListener(){
        mealPlannerRepository.addPropertyChangeListener(PropertyChangeListener{
                event ->
            if (event.propertyName == "meal_planner"){
                val newMealPlanner = event.newValue as MutableList<MealPlannerEntry>
                newMealPlanner.sortWith(compareByDescending{it.dateTime})
                setMealPlanner(newMealPlanner)
            }
        })
    }

    fun deleteMealPlannerEntry(mealPlannerEntry: MealPlannerEntry){
        mealPlannerRepository.deleteMealPlannerEntry(mealPlannerEntry)
    }
}