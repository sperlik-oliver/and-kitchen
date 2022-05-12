package tech.sperlikoliver.and_kitchen.Model.Domain.Repository

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.Ignore
import androidx.room.PrimaryKey


data class ShoppingListItem (
    val id : String = "",
    val name : String,
    val completed : Boolean = false,
    val userId : String = ""
    ) {
}