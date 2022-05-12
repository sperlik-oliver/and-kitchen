package tech.sperlikoliver.and_kitchen.Model.Domain

import androidx.room.ColumnInfo
import androidx.room.Ignore
import androidx.room.PrimaryKey
import com.google.common.collect.ImmutableList

data class Recipe(
    @PrimaryKey
    val id : String = "",
    @ColumnInfo(name = "name")
    val name : String = "",
    @ColumnInfo(name = "directions")
    val directions : String = "",
    @ColumnInfo(name = "category")
    val category : String = "",
    @ColumnInfo(name = "description")
    val description : String = "",
    @Ignore
    val ingredients : MutableList<String> = mutableListOf(),
//    val image : String = "",
    @Ignore
    val userId : String = ""
)