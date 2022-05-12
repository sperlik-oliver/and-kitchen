package tech.sperlikoliver.and_kitchen.Model.Domain.DAO

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.Ignore
import androidx.room.PrimaryKey

@Entity(tableName = "meal_planner")
data class MealPlannerEntryRoom (
    @PrimaryKey(autoGenerate = true)
    var id : Long,
    @ColumnInfo(name = "date_time")
    var dateTime : Long,
    @ColumnInfo(name = "recipe_id")
    var recipeId : Long,
)