package tech.sperlikoliver.and_kitchen.Model.Repository.Implementation

import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import tech.sperlikoliver.and_kitchen.Model.Repository.Interface.IMealPlannerRepository
import java.beans.PropertyChangeSupport


class MealPlannerRepositoryImpl : IMealPlannerRepository {

    override val propertyChangeSupport: PropertyChangeSupport = PropertyChangeSupport(this)
    private val database: FirebaseFirestore = Firebase.firestore
    private val mealPlannerRef = database.collection("meal_planner")

    init {
        addMealPlannerListener()
    }

    private fun addMealPlannerListener(){
        TODO("Not yet implemented")
    }

    override fun createMealPlannerEntry() {
        TODO("Not yet implemented")
    }

    override fun editMealPlannerEntry() {
        TODO("Not yet implemented")
    }

    override fun deleteMealPlannerEntry() {
        TODO("Not yet implemented")
    }





}