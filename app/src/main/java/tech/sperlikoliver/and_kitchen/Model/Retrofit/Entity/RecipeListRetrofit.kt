package tech.sperlikoliver.and_kitchen.Model.Retrofit.Entity

import kotlinx.serialization.Serializable

@Serializable
data class RecipeListRetrofit(
    val recipes : List<RecipeRetrofit> = listOf()
)
