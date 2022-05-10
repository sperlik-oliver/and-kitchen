package tech.sperlikoliver.and_kitchen.ViewModel

import androidx.lifecycle.ViewModel
import tech.sperlikoliver.and_kitchen.Model.Repository.Implementation.MealPlannerRepositoryImpl
import tech.sperlikoliver.and_kitchen.Model.Repository.Interface.IMealPlannerRepository

class MealPlannerViewModel() : ViewModel() {
    private val mealPlannerRepositoryImpl : IMealPlannerRepository = MealPlannerRepositoryImpl()
}