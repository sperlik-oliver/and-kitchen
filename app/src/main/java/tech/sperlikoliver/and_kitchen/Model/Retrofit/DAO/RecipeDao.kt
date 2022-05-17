package tech.sperlikoliver.and_kitchen.Model.Retrofit.DAO

import retrofit2.Call
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.converter.scalars.ScalarsConverterFactory
import retrofit2.http.GET

interface RecipeDao {

    @GET("recipes/random?number=1&apiKey=f6027bb4ede247fea685dbb1079c759b")
    fun getRandomRecipe() : Call<String>

    companion object{
        private const val BASE_URL = "https://api.spoonacular.com/"



        fun initialize() : RecipeDao {
            val retrofit = Retrofit.Builder()
                .addConverterFactory(ScalarsConverterFactory.create())
                .addConverterFactory(GsonConverterFactory.create())
                .baseUrl(BASE_URL)
                .build()

            return retrofit.create(RecipeDao::class.java)
        }
    }
}