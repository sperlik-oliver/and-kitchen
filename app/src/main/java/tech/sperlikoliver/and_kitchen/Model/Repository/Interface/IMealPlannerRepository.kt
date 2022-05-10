package tech.sperlikoliver.and_kitchen.Model.Repository.Interface

import tech.sperlikoliver.and_kitchen.Model.Utility.PropertyChangeAware

interface IMealPlannerRepository : PropertyChangeAware{

    fun createMealPlannerEntry()
    fun editMealPlannerEntry()
    fun deleteMealPlannerEntry()

}