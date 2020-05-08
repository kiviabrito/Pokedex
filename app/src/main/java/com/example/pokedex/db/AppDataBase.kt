package com.example.pokedex.db

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.example.pokedex.model.PokemonEntity

@TypeConverters(Converters::class)
@Database(
    entities = [
        PokemonEntity::class
    ], version = 1
)
abstract class AppDatabase : RoomDatabase() {

    abstract fun pokemonDao(): PokemonDao

    companion object {

        @Volatile private var INSTANCE: AppDatabase? = null

        fun getInstance(context: Context): AppDatabase =
            INSTANCE ?: synchronized(this) {
                INSTANCE ?: buildDatabase(context).also { INSTANCE = it }
            }

        private fun buildDatabase(context: Context) =
            Room.databaseBuilder(context.applicationContext,
                AppDatabase::class.java, "pokedex.db")
                .build()
    }

}