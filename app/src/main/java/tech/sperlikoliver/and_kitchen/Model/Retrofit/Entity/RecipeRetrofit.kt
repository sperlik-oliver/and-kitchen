package tech.sperlikoliver.and_kitchen.Model.Retrofit.Entity

import kotlinx.serialization.Serializable

@Serializable
data class RecipeRetrofit(
    val title : String = "",
    val instructions : String = "",
    val summary : String = "",
    val dishTypes : List<String> = listOf(),
    val extendedIngredientRetrofits : List<ExtendedIngredientRetrofit> = listOf()
)