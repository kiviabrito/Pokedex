package com.example.pokedex

import android.app.Application
import com.example.pokedex.db.AppDatabase
import com.example.pokedex.network.PokemonService
import io.reactivex.disposables.Disposable

class App: Application() {

    companion object {
        lateinit var instance: App

        lateinit var db : AppDatabase

        val pokemonService by lazy {
            PokemonService.create()
        }
    }

    override fun onCreate() {
        super.onCreate()
        instance = this
        db = AppDatabase.getInstance(this)
    }
}