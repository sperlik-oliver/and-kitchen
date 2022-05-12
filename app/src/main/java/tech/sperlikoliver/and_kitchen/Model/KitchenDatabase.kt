package tech.sperlikoliver.and_kitchen.Model

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import tech.sperlikoliver.and_kitchen.Model.DAO.MealPlannerDao
import tech.sperlikoliver.and_kitchen.Model.DAO.RecipeDao
import tech.sperlikoliver.and_kitchen.Model.DAO.ShoppingListDao
import tech.sperlikoliver.and_kitchen.Model.Domain.DAO.MealPlannerEntryRoom
import tech.sperlikoliver.and_kitchen.Model.Domain.DAO.RecipeRoom
import tech.sperlikoliver.and_kitchen.Model.Domain.DAO.ShoppingListItemRoom
import tech.sperlikoliver.and_kitchen.Model.Utility.Converters


@Database(entities = [MealPlannerEntryRoom::class, RecipeRoom::class, ShoppingListItemRoom::class], version = 1)
@TypeConverters(Converters::class)
abstract class KitchenDatabase : RoomDatabase() {
    abstract fun mealPlannerDao() : MealPlannerDao
    abstract fun recipeDao() : RecipeDao
    abstract fun shoppingListDao() : ShoppingListDao

    companion object {
        private var INSTANCE: KitchenDatabase? = null

        fun initialize(context: Context){
            if (INSTANCE == null){
                synchronized(this){
                    INSTANCE = Room.databaseBuilder(context, KitchenDatabase::class.java, "kitchen_database").build()
                }
            }
        }

        fun get() : KitchenDatabase {
                return INSTANCE!!
        }
    }
}