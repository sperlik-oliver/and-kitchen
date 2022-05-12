package tech.sperlikoliver.and_kitchen.Model.Domain

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.Ignore
import androidx.room.PrimaryKey

@Entity
data class ShoppingListItem (
    @PrimaryKey val id : String = "",
    @ColumnInfo(name = "name") val name : String,
    @ColumnInfo(name = "completed") val completed : Boolean = false,
    @Ignore val userId : String = ""
    ) {
}