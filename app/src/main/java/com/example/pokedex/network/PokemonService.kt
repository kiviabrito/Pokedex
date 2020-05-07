package com.example.pokedex.network

import android.telecom.Call
import com.example.pokedex.model.EvolutionChainEntity
import com.example.pokedex.model.Pokemon20
import com.example.pokedex.model.PokemonEntity
import com.example.pokedex.model.SpeciesEntity
import io.reactivex.Observable
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface PokemonService {
    @GET("pokemon/{id}/")
    fun requestPokemon(@Path("id") id: Int): Observable<PokemonEntity>

    @GET("pokemon-species/{id}/")
    fun requestSpecies(@Path("id") id: Int): Observable<SpeciesEntity>

    @GET("evolution-chain/{id}/")
    fun requestEvolution(@Path("id") id: Int): Observable<EvolutionChainEntity>

    @GET("pokemon?limit=20")
    fun getNext20(@Query("offset") from: Int) : Observable<Pokemon20>
}