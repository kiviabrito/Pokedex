package com.example.pokedex.ui.main

import androidx.lifecycle.ViewModel
import com.example.pokedex.App
import com.example.pokedex.network.PokemonService
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers

class MainViewModel(
    val service: PokemonService = App.pokemonService,
    var disposable: Disposable? = App.disposable
) : ViewModel() {

    fun getPokemonList(from: Int) {
        disposable = service.getNext20(from)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({ result ->
                println("Result ${result.results}")
            }, { error ->
                println("Error ${error.message}")
            })
    }

    fun getPokemonById(id: Int) {
        disposable = service.requestPokemon(id)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({ result ->
                println("Result ${result.name}")
            }, { error ->
                println("Error ${error.message}")
            })
    }

    fun getSpeciesById(id: Int) {
        disposable = service.requestSpecies(id)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({ result ->
                println("Result ${result.name}")
            }, { error ->
                println("Error ${error.message}")
            })
    }

    fun getEvolutionById(id: Int) {
        disposable = service.requestEvolution(id)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({ result ->
                println("Result ${result.chain.evolves_to[0].species.name}")
            }, { error ->
                println("Error ${error.message}")
            })
    }
}
