package com.example.pokedex.db

import androidx.room.TypeConverter
import com.example.pokedex.model.*
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken

class Converters {

    @TypeConverter
    fun fromStatList(value: List<Stat>): String {
        val type = object : TypeToken<List<Stat>>() {}.type
        return Gson().toJson(value, type)
    }

    @TypeConverter
    fun toStatList(value: String): List<Stat> {
        val type = object : TypeToken<List<Stat>>() {}.type
        return Gson().fromJson(value, type)
    }

    @TypeConverter
    fun fromStringList(value: List<String>): String {
        val type = object : TypeToken<List<String>>() {}.type
        return Gson().toJson(value, type)
    }

    @TypeConverter
    fun toStringList(value: String): List<String> {
        val type = object : TypeToken<List<String>>() {}.type
        return Gson().fromJson(value, type)
    }

    @TypeConverter
    fun fromDetailList(value: List<Detail>): String {
        val type = object : TypeToken<List<Detail>>() {}.type
        return Gson().toJson(value, type)
    }

    @TypeConverter
    fun toDetailList(value: String): List<Detail> {
        val type = object : TypeToken<List<Detail>>() {}.type
        return Gson().fromJson(value, type)
    }

}