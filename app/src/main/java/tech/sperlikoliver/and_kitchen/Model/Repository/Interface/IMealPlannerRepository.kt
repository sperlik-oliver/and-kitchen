package tech.sperlikoliver.and_kitchen.Model.Repository.Interface

import tech.sperlikoliver.and_kitchen.Model.Domain.MealPlannerEntry
import tech.sperlikoliver.and_kitchen.Model.Utility.PropertyChangeAware

interface IMealPlannerRepository : PropertyChangeAware{

    fun getMealPlanner()
    fun getMealPlannerEntry(mealPlannerEntryId : String)
    fun createMealPlannerEntry(mealPlannerEntry : MealPlannerEntry)
    fun editMealPlannerEntry(mealPlannerEntry : MealPlannerEntry)
    fun deleteMealPlannerEntry(mealPlannerEntry : MealPlannerEntry)

}