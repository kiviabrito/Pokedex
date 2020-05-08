package com.example.pokedex.ui.main

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.pokedex.App
import com.example.pokedex.db.AppDatabase
import com.example.pokedex.model.PokemonEntity
import com.example.pokedex.model.PokemonInfo
import com.example.pokedex.network.PokemonService
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers

class MainViewModel(
  val service: PokemonService = App.pokemonService,
  var disposable: Disposable? = App.disposable,
  val database: AppDatabase = App.db
) : ViewModel() {

  private val _pokemonList: MutableLiveData<List<PokemonEntity>> = MutableLiveData()
  val pokemonList: LiveData<List<PokemonEntity>> = _pokemonList

  init {
    for (i in 1 until 20) {
      getPokemonById(i)
    }
  }

  fun getPokemonById(id: Int) {
    disposable = service.requestPokemon(id)
      .map { pokemon ->
        // Create PokemonEntity
        val entity = createPokemonEntity(pokemon)
        // Save to DB
        database.pokemonDao().upsert(entity)
        entity
      }
      .subscribeOn(Schedulers.io())
      .observeOn(AndroidSchedulers.mainThread())
      .subscribe({ pokemon ->
        println("Result name ${pokemon.name} id  ${pokemon.id}")
      }, { error ->
        println("Error ${error.message}")
      })
  }

  private fun createPokemonEntity(pokemon: PokemonInfo): PokemonEntity {
    val abilities = mutableListOf<String>()
    pokemon.abilities.forEach { ability ->
      if (ability.is_hidden.not()) {
        abilities.add(ability.ability.name)
      }
    }
    val forms = mutableListOf<String>()
    pokemon.forms.forEach { form ->
      forms.add(form.name)
    }
    val heldItems = mutableListOf<String>()
    pokemon.held_items.forEach { item ->
      heldItems.add(item.item.name)
    }
    val moves = mutableListOf<String>()
    pokemon.moves.forEach { move ->
      moves.add(move.move.name)
    }
    val types = mutableListOf<String>()
    pokemon.types.forEach { type ->
      types.add(type.type.name)
    }
    val entity = PokemonEntity(
      id = pokemon.id,
      name = pokemon.name,
      photos = listOf(
        pokemon.sprites.front_default,
        pokemon.sprites.front_shiny,
        pokemon.sprites.back_shiny,
        pokemon.sprites.front_shiny
      ),
      abilities = abilities,
      forms = forms,
      height = pokemon.height,
      held_items = heldItems,
      is_default = pokemon.is_default,
      moves = moves,
      order = pokemon.order,
      species = pokemon.species.name,
      stats = pokemon.stats,
      types = types,
      weight = pokemon.height
    )
    return entity
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
