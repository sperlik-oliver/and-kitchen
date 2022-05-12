package tech.sperlikoliver.and_kitchen.Model.Utility

import android.content.Context
import android.content.SharedPreferences
import android.util.Log
import androidx.room.Room
import tech.sperlikoliver.and_kitchen.Model.KitchenDatabase

class mAuth {
    companion object{
        private var isAnonymous : Boolean = false
        private var preferences : SharedPreferences? = null
        fun initialize(context: Context){
            if (preferences == null){
                synchronized(this){
                    preferences = context.getSharedPreferences("mAuth", Context.MODE_PRIVATE)
                    Log.i("mAuth", "We are in initialize")
                    Log.i("mAuth", preferences.toString())
                    isAnonymous = preferences!!.getBoolean("mAuth", false)
                }
            }
        }

        fun get() : Boolean{
            return isAnonymous
        }

        fun set(_isAnonymous : Boolean){
            isAnonymous = _isAnonymous
            Log.i("mAuth", preferences.toString())
            val editor = preferences!!.edit()
            editor.putBoolean("mAuth", isAnonymous)
            editor.commit()
        }
    }
}