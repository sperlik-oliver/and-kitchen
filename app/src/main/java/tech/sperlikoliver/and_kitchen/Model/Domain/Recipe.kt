package tech.sperlikoliver.and_kitchen.Model.Domain

import com.google.common.collect.ImmutableList

data class Recipe(
    val id : String = "",
    val name : String = "",
    val directions : String = "",
    val category : String = "",
    val description : String = "",
    val ingredients : MutableList<String> = mutableListOf(),
//    val image : String = "",
    val userId : String = ""
)