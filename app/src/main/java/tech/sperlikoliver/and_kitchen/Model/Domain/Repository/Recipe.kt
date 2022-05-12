package tech.sperlikoliver.and_kitchen.Model.Domain.Repository

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.Ignore
import androidx.room.PrimaryKey
import com.google.common.collect.ImmutableList


data class Recipe(
    val id : String = "",
    val name : String = "",
    val directions : String = "",
    val category : String = "",
    val description : String = "",
    val ingredients : MutableList<String> = mutableListOf(),
    val userId : String = ""
)