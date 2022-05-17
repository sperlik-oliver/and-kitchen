package tech.sperlikoliver.and_kitchen.View.Main.MealPlanner.Utility

import java.text.SimpleDateFormat
import java.util.*

class DateTimeFormat {
    companion object{

        fun parseStringToEpoch (mDate : String, mTime : String) : Long {
            return SimpleDateFormat("dd/MM/yyyy HH:mm").parse("$mDate $mTime").time/1000
        }

        fun parseEpochToStringSeparate(date : Long) : HashMap<String, String> {
           return hashMapOf(
               "date" to SimpleDateFormat("dd/MM/yyyy").format(Date(date*1000)),
               "time" to SimpleDateFormat("HH:mm").format(Date(date*1000))
           )
        }

        fun parseEpochToString(date : Long) : String {
            return SimpleDateFormat("dd/MM/yyyy HH:mm").format(date*1000)
        }

        fun parseHourAndMinToTime(mHour : Int, mMinute : Int) : String{
            return if(mMinute < 10 && mHour < 10){
                "0$mHour:0$mMinute"
            } else if (mMinute < 10 && mHour >= 10){
                "$mHour:0$mMinute"
            } else if (mMinute >= 10 && mHour < 10){
                "0$mHour:$mMinute"
            } else {
                "$mHour:$mMinute"
            }
        }

        fun parseDayMonthYearToDate(mDayOfMonth : Int, mMonth : Int, mYear : Int) : String{
            return if(mDayOfMonth < 10 && mMonth < 10){
                "0$mDayOfMonth/0${mMonth+1}/$mYear"
            } else if (mDayOfMonth < 10 && mMonth >= 10){
                "0$mDayOfMonth/${mMonth+1}/$mYear"
            } else if (mDayOfMonth >= 10 && mMonth < 10){
                "$mDayOfMonth/0${mMonth+1}/$mYear"
            } else {
                "$mDayOfMonth/${mMonth+1}/$mYear"
            }
        }
    }
}