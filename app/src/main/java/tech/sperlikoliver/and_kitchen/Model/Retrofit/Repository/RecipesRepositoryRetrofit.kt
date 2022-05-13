package tech.sperlikoliver.and_kitchen.Model.Retrofit.Repository

import android.util.Log
import com.google.firebase.auth.FirebaseAuth
import kotlinx.serialization.decodeFromString
import kotlinx.serialization.json.Json
import retrofit2.Call
import retrofit2.Response
import tech.sperlikoliver.and_kitchen.Model.App.AnonymousAuth
import tech.sperlikoliver.and_kitchen.Model.App.PropertyChangeAware
import tech.sperlikoliver.and_kitchen.Model.Firebase.Entity.Recipe
import tech.sperlikoliver.and_kitchen.Model.Retrofit.DAO.RecipeDao
import tech.sperlikoliver.and_kitchen.Model.Retrofit.Entity.RecipeListRetrofit
import tech.sperlikoliver.and_kitchen.Model.Retrofit.Utility.HtmlParser
import java.beans.PropertyChangeSupport

class RecipesRepositoryRetrofit : PropertyChangeAware{
    override val propertyChangeSupport: PropertyChangeSupport = PropertyChangeSupport(this)
    private val recipeDao = RecipeDao.initialize()

    fun getRandomRecipe(){
        Log.i("Retrofit", "get Random Recipe")
        val propertyName = "generated_recipe"
        val getRandomRecipe = recipeDao.getRandomRecipe()
        getRandomRecipe.enqueue(object : retrofit2.Callback<String>{
            override fun onResponse(call: Call<String>, response: Response<String>) {
                if (response.body() != null){
                    val format = Json { isLenient = true; ignoreUnknownKeys = true; coerceInputValues = true }
                    val wrapper = format.decodeFromString<RecipeListRetrofit>(response.body()!!)
                    val data = wrapper.recipes.first()
                    var category = ""
                    val ingredients = mutableListOf<String>()
                    for ((c, s) in data.dishTypes.withIndex()){
                        category += if (c < data.dishTypes.size-1) {
                            "$s, "
                        }else {
                            "$s"
                        }
                    }
                    for (i in data.extendedIngredientRetrofits){
                        ingredients.add(i.original)
                    }
                    var userId : String
                    if(!AnonymousAuth.get()) {
                        userId = FirebaseAuth.getInstance().currentUser?.uid!!
                    } else{
                        userId = ""
                    }
                    val recipe = Recipe(
                        name = data.title,
                        directions = HtmlParser.removeTags(data.instructions),
                        category = category,
                        description = HtmlParser.removeTags(data.summary),
                        ingredients = ingredients,
                        userId = userId
                    )
                    propertyChangeSupport.firePropertyChange(propertyName, null, recipe)
                }
            }

            override fun onFailure(call: Call<String>, t: Throwable) {
                Log.e("RecipeRepositoryRetrofit", t.message!!)
            }
        })
    }
}