package tech.sperlikoliver.and_kitchen.Model.Domain

import androidx.room.ColumnInfo
import androidx.room.Ignore
import androidx.room.PrimaryKey

data class MealPlannerEntry (
    @PrimaryKey
    val id : String = "",
    @ColumnInfo(name = "date_time")
    val dateTime : Long = 0,
    @ColumnInfo(name = "recipe_id")
    val recipeId : String = "",
    @Ignore
    val userId : String = ""
        )