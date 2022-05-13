package tech.sperlikoliver.and_kitchen.Model.Firebase.Entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.Ignore
import androidx.room.PrimaryKey


data class MealPlannerEntry (
    val id : String = "",
    val dateTime : Long = 0,
    val recipeId : String = "",
    val userId : String = ""
        )