package com.example.pokedex

import android.app.Application
import com.example.pokedex.network.ClientAPI
import io.reactivex.disposables.Disposable

class App: Application() {

    companion object {
        lateinit var instance: App

       // lateinit var db : AppDatabase

        val pokemonService by lazy {
            ClientAPI.create()
        }
        var disposable: Disposable? = null
    }

    override fun onCreate() {
        super.onCreate()
        instance = this
        /*db = Room.databaseBuilder(
            applicationContext,
            AppDatabase::class.java, "mobile-challenge-db"
        ).build()*/
    }
}