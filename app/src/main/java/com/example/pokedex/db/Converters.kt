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

}


/* // Unit Conversion

    @TypeConverter
    fun fromDetail(value: Detail): String {
        return Gson().toJson(value, Detail::class.java)
    }

    @TypeConverter
    fun toDetail(value: String): Detail {
        return Gson().fromJson(value, Detail::class.java)
    }

    @TypeConverter
    fun fromURLString(value: URLString): String {
        return Gson().toJson(value, URLString::class.java)
    }

    @TypeConverter
    fun toURLString(value: String): URLString {
        return Gson().fromJson(value, URLString::class.java)
    }

    @TypeConverter
    fun fromSprites(value: Sprites): String {
        return Gson().toJson(value, Sprites::class.java)
    }

    @TypeConverter
    fun toSprites(value: String): Sprites {
        return Gson().fromJson(value, Sprites::class.java)
    }

    @TypeConverter
    fun fromChain(value: Chain): String {
        return Gson().toJson(value, Chain::class.java)
    }

    @TypeConverter
    fun toChain(value: String): Chain {
        return Gson().fromJson(value, Chain::class.java)
    }

    // List Conversion

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

    @TypeConverter
    fun fromAbilityList(value: List<Ability>): String {
        val type = object : TypeToken<List<Ability>>() {}.type
        return Gson().toJson(value, type)
    }

    @TypeConverter
    fun toAbilityList(value: String): List<Ability> {
        val type = object : TypeToken<List<Ability>>() {}.type
        return Gson().fromJson(value, type)
    }

    @TypeConverter
    fun fromGameIndexList(value: List<GameIndex>): String {
        val type = object : TypeToken<List<GameIndex>>() {}.type
        return Gson().toJson(value, type)
    }

    @TypeConverter
    fun toGameIndexList(value: String): List<GameIndex> {
        val type = object : TypeToken<List<GameIndex>>() {}.type
        return Gson().fromJson(value, type)
    }

    @TypeConverter
    fun fromHeldItemList(value: List<HeldItem>): String {
        val type = object : TypeToken<List<HeldItem>>() {}.type
        return Gson().toJson(value, type)
    }

    @TypeConverter
    fun toHeldItemList(value: String): List<HeldItem> {
        val type = object : TypeToken<List<HeldItem>>() {}.type
        return Gson().fromJson(value, type)
    }

    @TypeConverter
    fun fromMoveList(value: List<Move>): String {
        val type = object : TypeToken<List<Move>>() {}.type
        return Gson().toJson(value, type)
    }

    @TypeConverter
    fun toMoveList(value: String): List<Move> {
        val type = object : TypeToken<List<Move>>() {}.type
        return Gson().fromJson(value, type)
    }

    @TypeConverter
    fun fromTypeList(value: List<Type>): String {
        val type = object : TypeToken<List<Type>>() {}.type
        return Gson().toJson(value, type)
    }

    @TypeConverter
    fun toTypeList(value: String): List<Type> {
        val type = object : TypeToken<List<Type>>() {}.type
        return Gson().fromJson(value, type)
    }

    @TypeConverter
    fun fromFlavorList(value: List<Flavor>): String {
        val type = object : TypeToken<List<Flavor>>() {}.type
        return Gson().toJson(value, type)
    }

    @TypeConverter
    fun toFlavorList(value: String): List<Flavor> {
        val type = object : TypeToken<List<Flavor>>() {}.type
        return Gson().fromJson(value, type)
    }

    @TypeConverter
    fun fromGeneraList(value: List<Genera>): String {
        val type = object : TypeToken<List<Genera>>() {}.type
        return Gson().toJson(value, type)
    }

    @TypeConverter
    fun toGeneraList(value: String): List<Genera> {
        val type = object : TypeToken<List<Genera>>() {}.type
        return Gson().fromJson(value, type)
    }

    @TypeConverter
    fun fromNameList(value: List<Name>): String {
        val type = object : TypeToken<List<Name>>() {}.type
        return Gson().toJson(value, type)
    }

    @TypeConverter
    fun toNameList(value: String): List<Name> {
        val type = object : TypeToken<List<Name>>() {}.type
        return Gson().fromJson(value, type)
    }

    @TypeConverter
    fun fromPalParkEncounterList(value: List<PalParkEncounter>): String {
        val type = object : TypeToken<List<PalParkEncounter>>() {}.type
        return Gson().toJson(value, type)
    }

    @TypeConverter
    fun toPalParkEncounterList(value: String): List<PalParkEncounter> {
        val type = object : TypeToken<List<PalParkEncounter>>() {}.type
        return Gson().fromJson(value, type)
    }

    @TypeConverter
    fun fromPokedexNumList(value: List<PokedexNum>): String {
        val type = object : TypeToken<List<PokedexNum>>() {}.type
        return Gson().toJson(value, type)
    }

    @TypeConverter
    fun toPokedexNumList(value: String): List<PokedexNum> {
        val type = object : TypeToken<List<PokedexNum>>() {}.type
        return Gson().fromJson(value, type)
    }

    @TypeConverter
    fun fromVarietyList(value: List<Variety>): String {
        val type = object : TypeToken<List<PokedexNum>>() {}.type
        return Gson().toJson(value, type)
    }

    @TypeConverter
    fun toVarietyList(value: String): List<Variety> {
        val type = object : TypeToken<List<PokedexNum>>() {}.type
        return Gson().fromJson(value, type)
    }
*/