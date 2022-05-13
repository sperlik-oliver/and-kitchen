package tech.sperlikoliver.and_kitchen.Model.Proxy

import com.google.android.gms.auth.api.Auth
import tech.sperlikoliver.and_kitchen.Model.App.AnonymousAuth
import tech.sperlikoliver.and_kitchen.Model.Firebase.Entity.MealPlannerEntry
import tech.sperlikoliver.and_kitchen.Model.Firebase.Repository.MealPlannerRepositoryFirebase
import tech.sperlikoliver.and_kitchen.Model.Interface.IMealPlannerRepository
import tech.sperlikoliver.and_kitchen.Model.Room.Repository.MealPlannerRepositoryRoom
import java.beans.PropertyChangeListener
import java.beans.PropertyChangeSupport

class MealPlannerRepositoryProxy : IMealPlannerRepository{
    private val mealPlannerRepositoryFirebase : IMealPlannerRepository = MealPlannerRepositoryFirebase()
    private val mealPlannerRepositoryRoom : IMealPlannerRepository = MealPlannerRepositoryRoom()
    override val propertyChangeSupport: PropertyChangeSupport = PropertyChangeSupport(this)


    init {
        addMealPlannerListener()
    }

    private fun addMealPlannerListener(){
        mealPlannerRepositoryFirebase.addPropertyChangeListener(PropertyChangeListener {
            event ->
            if (event.propertyName == "meal_planner"){
                propertyChangeSupport.firePropertyChange("meal_planner", null, event.newValue)
            }
            if (event.propertyName == "meal_planner_entry"){
                propertyChangeSupport.firePropertyChange("meal_planner_entry", null, event.newValue)
            }

        })

        mealPlannerRepositoryRoom.addPropertyChangeListener(PropertyChangeListener {
            event ->
            if (event.propertyName == "meal_planner"){
                propertyChangeSupport.firePropertyChange("meal_planner", null, event.newValue)
            }
            if (event.propertyName == "meal_planner_entry"){
                propertyChangeSupport.firePropertyChange("meal_planner_entry", null, event.newValue)
            }
        })
    }

    override fun getMealPlanner(){
        if (!AnonymousAuth.get()){
            mealPlannerRepositoryFirebase.getMealPlanner()
        } else {
            mealPlannerRepositoryRoom.getMealPlanner()
        }
    }

    override fun getMealPlannerEntry(mealPlannerEntryId: String) {
        if (!AnonymousAuth.get()){
            mealPlannerRepositoryFirebase.getMealPlannerEntry(mealPlannerEntryId)
        } else {
            mealPlannerRepositoryRoom.getMealPlannerEntry(mealPlannerEntryId)
        }
    }

    override fun createMealPlannerEntry(mealPlannerEntry: MealPlannerEntry) {
        if (!AnonymousAuth.get()){
            mealPlannerRepositoryFirebase.createMealPlannerEntry(mealPlannerEntry)
        } else {
            mealPlannerRepositoryRoom.createMealPlannerEntry(mealPlannerEntry)
        }
    }

    override fun editMealPlannerEntry(mealPlannerEntry: MealPlannerEntry) {
        if (!AnonymousAuth.get()){
            mealPlannerRepositoryFirebase.editMealPlannerEntry(mealPlannerEntry)
        } else {
            mealPlannerRepositoryRoom.editMealPlannerEntry(mealPlannerEntry)
        }
    }

    override fun deleteMealPlannerEntry(mealPlannerEntry: MealPlannerEntry) {
        if (!AnonymousAuth.get()){
            mealPlannerRepositoryFirebase.deleteMealPlannerEntry(mealPlannerEntry)
        } else {
            mealPlannerRepositoryRoom.deleteMealPlannerEntry(mealPlannerEntry)
        }
    }

}