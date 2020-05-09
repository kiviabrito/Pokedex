package com.example.pokedex.ui.main

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.pokedex.App
import com.example.pokedex.db.AppDatabase
import com.example.pokedex.model.PokemonEntity
import com.example.pokedex.model.PokemonInfo
import com.example.pokedex.network.PokemonService
import com.example.pokedex.utility.SingleLiveData
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers

class MainViewModel(
  private val service: PokemonService = App.pokemonService,
  private var disposables: CompositeDisposable = CompositeDisposable(),
  private val database: AppDatabase = App.db
) : ViewModel() {

  // Handle Error
  val error: SingleLiveData<String> = SingleLiveData()

  // Handle RecyclerView Data
  private val _pokemonList: MutableLiveData<ArrayList<PokemonEntity>> = MutableLiveData()
  val pokemonList: LiveData<ArrayList<PokemonEntity>> = _pokemonList

  // Handle AutoComplete Data
  private val _pokemonListAutoComplete: MutableLiveData<ArrayList<PokemonEntity>> = MutableLiveData()
  val pokemonListAutoComplete: LiveData<ArrayList<PokemonEntity>> = _pokemonListAutoComplete

  fun getPokemonList(from: Int, to: Int) {
    val disposable = database.pokemonDao().select20(from = from, to = to)
      .subscribeOn(Schedulers.io())
      .observeOn(Schedulers.single())
      .subscribe({ list ->
        if (list.isNotEmpty()) {
          updateRecyclerView(list)
        } else {
          fetchFirst20PokemonList()
        }
      }, { error ->
        println("Error ${error.message}")
      })
    disposables.add(disposable)
  }

  private fun fetchFirst20PokemonList() {
    val firstList = mutableListOf<PokemonEntity>()
    for (i in 1 until 21) {
      val disposable = service.requestPokemon(i)
        .map { pokemon ->
          updateDataBase(pokemon)
        }
        .subscribeOn(Schedulers.io())
        .observeOn(AndroidSchedulers.mainThread())
        .subscribe({ pokemon ->
          firstList.add(pokemon)
          if (firstList.size == 19) {
            updateRecyclerView(firstList)
          } else if (firstList.size == 20) {
            fetchAllPokemonList()
          }
        }, { error ->
          this.error.postValue(error.message)
        })
      disposables.add(disposable)
    }
  }

  private fun fetchAllPokemonList() {
    for (i in 20 until 808) {
      val disposable = service.requestPokemon(i)
        .map { pokemon ->
          updateDataBase(pokemon)
        }
        .subscribeOn(Schedulers.computation())
        .observeOn(AndroidSchedulers.mainThread())
        .subscribe({}, { error ->
          this.error.postValue(error.message)
        })
      disposables.add(disposable)
    }
  }

  private fun updateRecyclerView(firstList: List<PokemonEntity>) {
    val arrayList = arrayListOf<PokemonEntity>()
    firstList.sortedBy { it.id }.flatMapTo(arrayList) { arrayListOf(it) }
    _pokemonList.postValue(arrayList)
  }

  private fun updateDataBase(pokemon: PokemonInfo): PokemonEntity {
    val entity = pokemon.createPokemonEntity()
    database.pokemonDao().upsert(entity)
    return entity
  }

  fun queryPokemon(query: String) {
    val disposable = database.pokemonDao().queryByIdAndName("%$query%")
      .subscribeOn(Schedulers.computation())
      .observeOn(AndroidSchedulers.mainThread())
      .subscribe({ list ->
        val arrayList = arrayListOf<PokemonEntity>()
        list.sortedBy { it.id }.flatMapTo(arrayList) { arrayListOf(it) }
        _pokemonListAutoComplete.postValue(arrayList)
      }, { error ->
        this.error.postValue(error.message)
      })
    disposables.add(disposable)
  }

  override fun onCleared() {
    super.onCleared()
    disposables.clear()
  }

}

/*

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

*/
