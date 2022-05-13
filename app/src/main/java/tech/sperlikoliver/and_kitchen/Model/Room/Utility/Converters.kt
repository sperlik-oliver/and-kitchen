package tech.sperlikoliver.and_kitchen.Model.Room.Utility

import androidx.room.TypeConverter

import kotlinx.serialization.encodeToString
import kotlinx.serialization.*
import kotlinx.serialization.json.Json
import kotlinx.serialization.decodeFromString


class Converters {
    @TypeConverter
    fun fromMutableList(value : MutableList<String>) = Json.encodeToString(value)

    @TypeConverter
    fun toMutableList(value : String) = Json.decodeFromString<MutableList<String>>(value)
}