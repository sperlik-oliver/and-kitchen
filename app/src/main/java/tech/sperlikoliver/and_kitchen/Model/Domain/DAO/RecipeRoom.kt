package tech.sperlikoliver.and_kitchen.Model.Domain.DAO

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.Ignore
import androidx.room.PrimaryKey
import com.google.common.collect.ImmutableList

@Entity(tableName = "recipes")
data class RecipeRoom(
    @PrimaryKey(autoGenerate = true)
    var id : Long,
    @ColumnInfo(name = "name")
    var name : String,
    @ColumnInfo(name = "directions")
    var directions : String,
    @ColumnInfo(name = "category")
    var category : String,
    @ColumnInfo(name = "description")
    var description : String,
    @ColumnInfo(name = "ingredients")
    var ingredients : MutableList<String>
)