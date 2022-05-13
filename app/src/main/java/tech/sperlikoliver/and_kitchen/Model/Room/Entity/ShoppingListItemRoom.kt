package tech.sperlikoliver.and_kitchen.Model.Room.Entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.Ignore
import androidx.room.PrimaryKey

@Entity(tableName = "shopping_list")
data class ShoppingListItemRoom (
    @PrimaryKey(autoGenerate = true)
    var id : Long,
    @ColumnInfo(name = "name")
    var name : String,
    @ColumnInfo(name = "completed")
    var completed : Boolean
)