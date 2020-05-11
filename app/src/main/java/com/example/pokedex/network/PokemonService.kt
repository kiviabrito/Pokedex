package com.example.pokedex.network

import com.example.pokedex.model.AbilityInfo
import com.example.pokedex.model.PokemonInfo
import com.example.pokedex.model.SpeciesInfo
import io.reactivex.Single
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Path

interface PokemonService {

    @GET("pokemon/{id}/")
    fun requestPokemon(@Path("id") id: Int): Single<PokemonInfo>

    @GET("pokemon-species/{id}/")
    fun requestSpecies(@Path("id") id: Int): Single<SpeciesInfo>

    @GET("ability/{path}")
    fun requestAbility(@Path("path") path: String): Single<AbilityInfo>

    companion object {
        fun create(): PokemonService {
            val retrofit = Retrofit.Builder()
                .addCallAdapterFactory(
                    RxJava2CallAdapterFactory.create()
                )
                .addConverterFactory(
                    GsonConverterFactory.create()
                )
                .baseUrl("https://pokeapi.co/api/v2/")
                .build()
            return retrofit.create(PokemonService::class.java)
        }
    }
}