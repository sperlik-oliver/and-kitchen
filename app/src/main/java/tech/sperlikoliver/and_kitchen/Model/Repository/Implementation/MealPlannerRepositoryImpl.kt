package tech.sperlikoliver.and_kitchen.Model.Repository.Implementation

import android.util.Log
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import tech.sperlikoliver.and_kitchen.Model.Domain.DAO.MealPlannerEntryRoom
import tech.sperlikoliver.and_kitchen.Model.Domain.Repository.MealPlannerEntry
import tech.sperlikoliver.and_kitchen.Model.KitchenDatabase
import tech.sperlikoliver.and_kitchen.Model.Repository.Interface.IMealPlannerRepository
import tech.sperlikoliver.and_kitchen.Model.Utility.mAuth
import java.beans.PropertyChangeSupport

private const val TAG : String = "MealPlannerRepositoryImpl"

class MealPlannerRepositoryImpl : IMealPlannerRepository {

    override val propertyChangeSupport: PropertyChangeSupport = PropertyChangeSupport(this)
    private val database: FirebaseFirestore = Firebase.firestore
    private val mealPlannerRef = database.collection("meal_planner")
    private val mealPlannerDao = KitchenDatabase.get().mealPlannerDao()

    override fun getMealPlanner() {
        val propertyName = "meal_planner"
        val mealPlanner: MutableList<MealPlannerEntry> = mutableListOf()
        if(!mAuth.get()) {
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
        }else {
            runBlocking { launch{
                val retrievedMealPlanner = mealPlannerDao.getMealPlanner()
                for (retrievedMealPlannerEntry in retrievedMealPlanner){
                    val mealPlannerEntry = MealPlannerEntry(
                        id = retrievedMealPlannerEntry.id.toString(),
                        dateTime = retrievedMealPlannerEntry.dateTime,
                        recipeId = retrievedMealPlannerEntry.recipeId.toString(),
                        userId = ""
                    )
                    mealPlanner.add(mealPlannerEntry)
                }
                propertyChangeSupport.firePropertyChange(propertyName, null, mealPlanner)
            } }
        }
    }

    override fun getMealPlannerEntry(mealPlannerEntryId: String) {
        val propertyName = "meal_planner_entry"
        if (!mAuth.get()) {
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
        } else {
            runBlocking { launch{
                val retrievedMealPlannerEntry = mealPlannerDao.getMealPlannerEntry(mealPlannerEntryId.toLong())
                val mealPlannerEntry = MealPlannerEntry(
                    id = retrievedMealPlannerEntry.id.toString(),
                    dateTime = retrievedMealPlannerEntry.dateTime,
                    recipeId = retrievedMealPlannerEntry.recipeId.toString(),
                    userId = ""
                )
                propertyChangeSupport.firePropertyChange(propertyName, null, mealPlannerEntry)
            } }
        }
    }

    override fun createMealPlannerEntry(mealPlannerEntry: MealPlannerEntry) {
        if(!mAuth.get()) {
            val mealPlannerEntryData = hashMapOf(
                "dateTime" to mealPlannerEntry.dateTime,
                "recipeId" to mealPlannerEntry.recipeId,
                "userId" to FirebaseAuth.getInstance().currentUser?.uid
            )
            mealPlannerRef.add(mealPlannerEntryData)
            getMealPlanner()
        } else {
            runBlocking { launch {
                val mealPlannerEntryRoom = MealPlannerEntryRoom(
                    id = 0,
                    dateTime = mealPlannerEntry.dateTime,
                    recipeId = mealPlannerEntry.recipeId.toLong()
                )
                mealPlannerDao.createMealPlannerEntry(mealPlannerEntryRoom)
                getMealPlanner()
            } }
        }
    }

    override fun editMealPlannerEntry(mealPlannerEntry: MealPlannerEntry) {
        if (!mAuth.get()) {
            val mealPlannerEntryData = hashMapOf(
                "dateTime" to mealPlannerEntry.dateTime,
                "recipeId" to mealPlannerEntry.recipeId,
                "userId" to FirebaseAuth.getInstance().currentUser?.uid
            )
            mealPlannerRef.document(mealPlannerEntry.id).set(mealPlannerEntryData)
            getMealPlanner()
        } else{
            runBlocking { launch {
                val mealPlannerEntryRoom = MealPlannerEntryRoom(
                    id = mealPlannerEntry.id.toLong(),
                    dateTime = mealPlannerEntry.dateTime,
                    recipeId = mealPlannerEntry.recipeId.toLong()
                )
                mealPlannerDao.editMealPlannerEntry(mealPlannerEntryRoom)
                getMealPlanner()
            } }
        }
    }

    override fun deleteMealPlannerEntry(mealPlannerEntry: MealPlannerEntry) {
        if(!mAuth.get()){
            mealPlannerRef.document(mealPlannerEntry.id).delete()
            getMealPlanner()
        } else {
            runBlocking { launch{
                val mealPlannerEntryRoom = MealPlannerEntryRoom(
                    id = mealPlannerEntry.id.toLong(),
                    dateTime = mealPlannerEntry.dateTime,
                    recipeId = mealPlannerEntry.recipeId.toLong()
                )
                mealPlannerDao.deleteMealPlannerEntry(mealPlannerEntryRoom)
                getMealPlanner()
            } }
        }


    }





}