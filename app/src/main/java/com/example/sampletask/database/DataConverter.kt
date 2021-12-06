package com.example.sampletask.database

import androidx.room.TypeConverter
import com.example.sampletask.model.Option
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken

class DataConverter {

    var gson = Gson()

    @TypeConverter
    fun fromOptionList(list: List<Option>): String {
        val type = object : TypeToken<List<Option>>() {}.type
        return gson.toJson(list, type)
    }

    @TypeConverter
    fun toOptionList(list: String): List<Option> {
        val type = object : TypeToken<List<Option>>() {}.type
        return gson.fromJson(list, type)
    }

    @TypeConverter
    fun listToJson(value: List<String>?) = Gson().toJson(value)

    @TypeConverter
    fun jsonToList(value: String) = Gson().fromJson(value, Array<String>::class.java).toList()

}