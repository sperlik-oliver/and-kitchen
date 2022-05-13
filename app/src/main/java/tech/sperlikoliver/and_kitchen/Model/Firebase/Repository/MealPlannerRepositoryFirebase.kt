package tech.sperlikoliver.and_kitchen.Model.Firebase.Repository

import android.util.Log
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import tech.sperlikoliver.and_kitchen.Model.App.PropertyChangeAware
import tech.sperlikoliver.and_kitchen.Model.Interface.IMealPlannerRepository
import tech.sperlikoliver.and_kitchen.Model.Firebase.Entity.MealPlannerEntry
import java.beans.PropertyChangeSupport

private const val TAG : String = "MealPlannerRepositoryImpl"

class MealPlannerRepositoryFirebase : IMealPlannerRepository {

    override val propertyChangeSupport: PropertyChangeSupport = PropertyChangeSupport(this)
    private val database: FirebaseFirestore = Firebase.firestore
    private val mealPlannerRef = database.collection("meal_planner")


    override fun getMealPlanner() {
        val propertyName = "meal_planner"
        val mealPlanner: MutableList<MealPlannerEntry> = mutableListOf()
            mealPlannerRef.whereEqualTo("userId", FirebaseAuth.getInstance().currentUser?.uid).get()
                .addOnCompleteListener() { mealPlannerSnapshot ->
                    if (!mealPlannerSnapshot.isSuccessful) {
                        mealPlannerSnapshot.exception?.message?.let { Log.e(TAG, it) }
                        return@addOnCompleteListener
                    }
                    val mealPlannerResult = mealPlannerSnapshot.result

                    if (mealPlannerResult == null) {
                        Log.e(TAG, "Meal planner result is null")
                        return@addOnCompleteListener
                    }
                    if (mealPlannerResult.isEmpty) {
                        propertyChangeSupport.firePropertyChange(propertyName, null, mealPlanner)
                        return@addOnCompleteListener
                    }
                    for (mealPlannerEntryDocument in mealPlannerResult) {
                        val mealPlannerEntry = MealPlannerEntry(
                            id = mealPlannerEntryDocument.id,
                            dateTime = mealPlannerEntryDocument.data["dateTime"] as Long,
                            recipeId = mealPlannerEntryDocument.data["recipeId"] as String,
                            userId = mealPlannerEntryDocument.data["userId"] as String
                        )
                        mealPlanner.add(mealPlannerEntry)
                    }
                    propertyChangeSupport.firePropertyChange(propertyName, null, mealPlanner)
                }
        }


    override fun getMealPlannerEntry(mealPlannerEntryId: String) {
        val propertyName = "meal_planner_entry"
            mealPlannerRef.document(mealPlannerEntryId).get()
                .addOnCompleteListener() { mealPlannerEntrySnapshot ->
                    if (!mealPlannerEntrySnapshot.isSuccessful) {
                        mealPlannerEntrySnapshot.exception?.message?.let { Log.e(TAG, it) }
                        return@addOnCompleteListener
                    }
                    val mealPlannerEntryResult = mealPlannerEntrySnapshot.result
                    if (mealPlannerEntryResult == null) {
                        Log.e(TAG, "Meal planner entry result is null")
                        return@addOnCompleteListener
                    }
                    if (mealPlannerEntryResult.data.isNullOrEmpty()) {
                        Log.e(TAG, "Meal planner entry result data is null or empty")
                        return@addOnCompleteListener
                    }
                    val mealPlannerEntry = MealPlannerEntry(
                        id = mealPlannerEntryResult.id,
                        dateTime = mealPlannerEntryResult.data!!["dateTime"] as Long,
                        recipeId = mealPlannerEntryResult.data!!["recipeId"] as String,
                        userId = mealPlannerEntryResult.data!!["userId"] as String
                    )
                    propertyChangeSupport.firePropertyChange(propertyName, null, mealPlannerEntry)
                }
        }


    override fun createMealPlannerEntry(mealPlannerEntry: MealPlannerEntry) {
            val mealPlannerEntryData = hashMapOf(
                "dateTime" to mealPlannerEntry.dateTime,
                "recipeId" to mealPlannerEntry.recipeId,
                "userId" to FirebaseAuth.getInstance().currentUser?.uid
            )
            mealPlannerRef.add(mealPlannerEntryData)
            getMealPlanner()
    }

    override fun editMealPlannerEntry(mealPlannerEntry: MealPlannerEntry) {

            val mealPlannerEntryData = hashMapOf(
                "dateTime" to mealPlannerEntry.dateTime,
                "recipeId" to mealPlannerEntry.recipeId,
                "userId" to FirebaseAuth.getInstance().currentUser?.uid
            )
            mealPlannerRef.document(mealPlannerEntry.id).set(mealPlannerEntryData)
            getMealPlanner()

    }

    override fun deleteMealPlannerEntry(mealPlannerEntry: MealPlannerEntry) {

            mealPlannerRef.document(mealPlannerEntry.id).delete()
            getMealPlanner()


    }
}