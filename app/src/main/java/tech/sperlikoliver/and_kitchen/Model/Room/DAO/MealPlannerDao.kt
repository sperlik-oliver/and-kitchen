package tech.sperlikoliver.and_kitchen.Model.Room.DAO

import androidx.room.*
import tech.sperlikoliver.and_kitchen.Model.Room.Entity.MealPlannerEntryRoom

@Dao
interface MealPlannerDao {
    @Query("SELECT * FROM meal_planner")
    suspend fun getMealPlanner() : MutableList<MealPlannerEntryRoom>

    @Query("SELECT * FROM meal_planner WHERE id = :id")
    suspend fun getMealPlannerEntry(id : Long) : MealPlannerEntryRoom

    @Insert
    suspend fun createMealPlannerEntry(mealPlannerEntryRoom: MealPlannerEntryRoom)

    @Update
    suspend fun editMealPlannerEntry(mealPlannerEntryRoom: MealPlannerEntryRoom)

    @Delete
    suspend fun deleteMealPlannerEntry(mealPlannerEntryRoom: MealPlannerEntryRoom)

}