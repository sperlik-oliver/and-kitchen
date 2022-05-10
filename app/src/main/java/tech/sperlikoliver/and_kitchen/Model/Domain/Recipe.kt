package tech.sperlikoliver.and_kitchen.Model.Domain

import com.google.common.collect.ImmutableList

data class Recipe(
    val id : String = "",
    val name : String = "",
    val directions : String = "",
    val category : String = "",
    val ingredients : ImmutableList<Ingredient> = ImmutableList.of(),
    val image : String = "",
    val userId : String = ""
)