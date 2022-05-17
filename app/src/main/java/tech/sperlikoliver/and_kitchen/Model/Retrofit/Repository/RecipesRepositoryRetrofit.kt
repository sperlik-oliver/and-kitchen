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
import tech.sperlikoliver.and_kitchen.Model.Retrofit.Utility.Parser
import java.beans.PropertyChangeSupport

class RecipesRepositoryRetrofit : PropertyChangeAware{

    override val propertyChangeSupport: PropertyChangeSupport = PropertyChangeSupport(this)
    private val recipeDao = RecipeDao.initialize()

    fun getRandomRecipe(){
        val propertyName = "generated_recipe"
        val getRandomRecipe = recipeDao.getRandomRecipe()
        getRandomRecipe.enqueue(object : retrofit2.Callback<String>{
            override fun onResponse(call: Call<String>, response: Response<String>) {
                if (response.body() != null){
                    propertyChangeSupport.firePropertyChange(propertyName, null, Parser.deserializeRecipe(response.body()!!))
                }
            }
            override fun onFailure(call: Call<String>, t: Throwable) {
                Log.e("RecipeRepositoryRetrofit", t.message!!)
            }
        })
    }
}