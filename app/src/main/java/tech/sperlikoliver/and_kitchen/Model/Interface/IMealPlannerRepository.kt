package tech.sperlikoliver.and_kitchen.Model.Interface

import tech.sperlikoliver.and_kitchen.Model.Firebase.Entity.MealPlannerEntry
import tech.sperlikoliver.and_kitchen.Model.App.PropertyChangeAware

interface IMealPlannerRepository : PropertyChangeAware{

    fun getMealPlanner()
    fun getMealPlannerEntry(mealPlannerEntryId : String)
    fun createMealPlannerEntry(mealPlannerEntry : MealPlannerEntry)
    fun editMealPlannerEntry(mealPlannerEntry : MealPlannerEntry)
    fun deleteMealPlannerEntry(mealPlannerEntry : MealPlannerEntry)

}