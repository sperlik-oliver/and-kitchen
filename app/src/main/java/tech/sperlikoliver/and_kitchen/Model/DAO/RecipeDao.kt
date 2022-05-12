package tech.sperlikoliver.and_kitchen.Model.DAO

import androidx.room.*
import tech.sperlikoliver.and_kitchen.Model.Domain.DAO.MealPlannerEntryRoom
import tech.sperlikoliver.and_kitchen.Model.Domain.DAO.RecipeRoom

@Dao
interface RecipeDao {
    @Query("SELECT * FROM recipes")
    suspend fun getRecipes() : MutableList<RecipeRoom>

    @Query("SELECT * FROM recipes WHERE id = :id")
    suspend fun getRecipe(id : Long) : RecipeRoom

    @Insert
    suspend fun createRecipe(recipe : RecipeRoom)

    @Update
    suspend fun editRecipe(recipe : RecipeRoom)

    @Delete
    suspend fun deleteRecipe(recipe : RecipeRoom)
}