package com.example.pokedex.network

import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory

class ClientAPI {

    companion object {
        fun create(): PokemonService {
            val retrofit = Retrofit.Builder()
                .addCallAdapterFactory(
                    RxJava2CallAdapterFactory.create())
                .addConverterFactory(
                    GsonConverterFactory.create())
                .baseUrl("https://pokeapi.co/api/v2/")
                .build()
            return retrofit.create(PokemonService::class.java)
        }
    }

}