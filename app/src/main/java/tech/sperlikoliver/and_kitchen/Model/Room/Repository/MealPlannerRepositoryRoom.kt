package tech.sperlikoliver.and_kitchen.Model.Room.Repository

import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import tech.sperlikoliver.and_kitchen.Model.Firebase.Entity.MealPlannerEntry
import tech.sperlikoliver.and_kitchen.Model.Interface.IMealPlannerRepository
import tech.sperlikoliver.and_kitchen.Model.Room.Entity.MealPlannerEntryRoom
import tech.sperlikoliver.and_kitchen.Model.Room.Database.KitchenDatabase
import java.beans.PropertyChangeSupport

class MealPlannerRepositoryRoom : IMealPlannerRepository {

    override val propertyChangeSupport: PropertyChangeSupport = PropertyChangeSupport(this)
    private val mealPlannerDao = KitchenDatabase.get().mealPlannerDao()

    override fun getMealPlanner() {
        val propertyName = "meal_planner"
        val mealPlanner: MutableList<MealPlannerEntry> = mutableListOf()
        runBlocking { launch{
            val retrievedMealPlanner = mealPlannerDao.getMealPlanner()
            for (retrievedMealPlannerEntry in retrievedMealPlanner){
                val mealPlannerEntry = MealPlannerEntry(
                    id = retrievedMealPlannerEntry.id.toString(),
                    dateTime = retrievedMealPlannerEntry.dateTime,
                    recipeId = retrievedMealPlannerEntry.recipeId.toString(),
                    userId = ""
                )
                mealPlanner.add(mealPlannerEntry)
            }
            propertyChangeSupport.firePropertyChange(propertyName, null, mealPlanner)
        } }
    }

    override fun getMealPlannerEntry(mealPlannerEntryId: String) {
        val propertyName = "meal_planner_entry"
        runBlocking { launch{
            val retrievedMealPlannerEntry = mealPlannerDao.getMealPlannerEntry(mealPlannerEntryId.toLong())
            val mealPlannerEntry = MealPlannerEntry(
                id = retrievedMealPlannerEntry.id.toString(),
                dateTime = retrievedMealPlannerEntry.dateTime,
                recipeId = retrievedMealPlannerEntry.recipeId.toString(),
                userId = ""
            )
            propertyChangeSupport.firePropertyChange(propertyName, null, mealPlannerEntry)
        } }
    }

    override fun createMealPlannerEntry(mealPlannerEntry: MealPlannerEntry) {
        runBlocking { launch {
            val mealPlannerEntryRoom = MealPlannerEntryRoom(
                id = 0,
                dateTime = mealPlannerEntry.dateTime,
                recipeId = mealPlannerEntry.recipeId.toLong()
            )
            mealPlannerDao.createMealPlannerEntry(mealPlannerEntryRoom)
            getMealPlanner()
        } }
    }

    override fun editMealPlannerEntry(mealPlannerEntry: MealPlannerEntry) {
        runBlocking { launch {
            val mealPlannerEntryRoom = MealPlannerEntryRoom(
                id = mealPlannerEntry.id.toLong(),
                dateTime = mealPlannerEntry.dateTime,
                recipeId = mealPlannerEntry.recipeId.toLong()
            )
            mealPlannerDao.editMealPlannerEntry(mealPlannerEntryRoom)
            getMealPlanner()
        } }
    }

    override fun deleteMealPlannerEntry(mealPlannerEntry: MealPlannerEntry) {
        runBlocking { launch{
            val mealPlannerEntryRoom = MealPlannerEntryRoom(
                id = mealPlannerEntry.id.toLong(),
                dateTime = mealPlannerEntry.dateTime,
                recipeId = mealPlannerEntry.recipeId.toLong()
            )
            mealPlannerDao.deleteMealPlannerEntry(mealPlannerEntryRoom)
            getMealPlanner()
        } }
    }

}