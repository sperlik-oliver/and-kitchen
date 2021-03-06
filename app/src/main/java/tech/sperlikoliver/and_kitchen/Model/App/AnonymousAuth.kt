package tech.sperlikoliver.and_kitchen.Model.App

import android.content.Context
import android.content.SharedPreferences
import android.util.Log

class AnonymousAuth {
    companion object{
        private var isAnonymous : Boolean = false
        private var preferences : SharedPreferences? = null

        fun initialize(context: Context){
            if (preferences == null){
                synchronized(this){
                    preferences = context.getSharedPreferences("mAuth", Context.MODE_PRIVATE)
                    isAnonymous = preferences!!.getBoolean("mAuth", false)
                }
            }
        }

        fun get() : Boolean{
            return isAnonymous
        }

        fun set(_isAnonymous : Boolean){
            isAnonymous = _isAnonymous
            val editor = preferences!!.edit()
            editor.putBoolean("mAuth", isAnonymous)
            editor.commit()
        }
    }
}


