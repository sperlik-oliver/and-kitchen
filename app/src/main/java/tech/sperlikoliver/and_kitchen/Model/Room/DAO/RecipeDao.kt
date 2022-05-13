package tech.sperlikoliver.and_kitchen.Model.Room.DAO

import androidx.room.*
import tech.sperlikoliver.and_kitchen.Model.Room.Entity.RecipeRoom

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