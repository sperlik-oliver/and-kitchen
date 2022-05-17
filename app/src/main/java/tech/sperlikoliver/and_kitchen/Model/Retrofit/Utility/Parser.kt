package tech.sperlikoliver.and_kitchen.Model.Retrofit.Utility

import com.google.firebase.auth.FirebaseAuth
import kotlinx.serialization.decodeFromString
import kotlinx.serialization.json.Json
import tech.sperlikoliver.and_kitchen.Model.App.AnonymousAuth
import tech.sperlikoliver.and_kitchen.Model.Firebase.Entity.Recipe
import tech.sperlikoliver.and_kitchen.Model.Retrofit.Entity.RecipeListRetrofit
import java.util.regex.Pattern

class Parser {
    companion object {
        private val REMOVE_TAGS : Pattern = Pattern.compile("<.+?>")

        private fun removeTags(string: String): String {
            return if (string.isEmpty()) {
                string
            } else {
                val m = REMOVE_TAGS.matcher(string)
                m.replaceAll("")
            }
        }

        fun deserializeRecipe(string : String) : Recipe? {
            return if (string.isEmpty()){
                null
            }else {
                val format =
                    Json { isLenient = true; ignoreUnknownKeys = true; coerceInputValues = true }
                val wrapper = format.decodeFromString<RecipeListRetrofit>(string)
                val data = wrapper.recipes.first()
                var category = ""
                val ingredients = mutableListOf<String>()
                for ((c, s) in data.dishTypes.withIndex()) {
                    category += if (c < data.dishTypes.size - 1) {
                        "$s, "
                    } else {
                        s
                    }
                }
                for (i in data.extendedIngredientRetrofits) {
                    ingredients.add(i.original)
                }
                val userId: String = if (!AnonymousAuth.get()) {
                    FirebaseAuth.getInstance().currentUser?.uid!!
                } else {
                    ""
                }
                return Recipe(
                    name = data.title,
                    directions = removeTags(data.instructions),
                    category = category,
                    description = removeTags(data.summary),
                    ingredients = ingredients,
                    userId = userId
                )
            }
        }
    }
}