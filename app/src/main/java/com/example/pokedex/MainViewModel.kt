package com.example.pokedex

import android.annotation.SuppressLint
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.pokedex.db.AppDatabase
import com.example.pokedex.model.PokemonEntity
import com.example.pokedex.model.PokemonInfo
import com.example.pokedex.network.PokemonService
import com.example.pokedex.utility.SingleLiveData
import io.reactivex.android.schedulers.AndroidSchedulers.mainThread
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

  // Handle RecyclerView Data
  private val _pokemonDetails: MutableLiveData<PokemonEntity> = MutableLiveData()
  val pokemonDetails: LiveData<PokemonEntity> = _pokemonDetails

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
        .observeOn(mainThread())
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
        .observeOn(mainThread())
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
      .observeOn(mainThread())
      .subscribe({ list ->
        val arrayList = arrayListOf<PokemonEntity>()
        list.sortedBy { it.id }.flatMapTo(arrayList) { arrayListOf(it) }
        _pokemonListAutoComplete.postValue(arrayList)
      }, { error ->
        this.error.postValue(error.message)
      })
    disposables.add(disposable)
  }

  fun getPokemonById(id: Int) {
    val disposable = database.pokemonDao().findById(id)
      .map { pokemon ->
        if (pokemon.description == "") {
          setPokemonSpecies(id, pokemon)
          setPokemonAbilities(pokemon)
          database.pokemonDao().upsert(pokemon)
        }
        pokemon
      }
      .onErrorReturn { database.pokemonDao().findById(id).blockingGet() }
      .subscribeOn(Schedulers.io())
      .observeOn(mainThread())
      .subscribe({ pokemon ->
        _pokemonDetails.postValue(pokemon)
      }, { error ->
        println("Error ${error.message}")
      })
    disposables.add(disposable)
  }

  @SuppressLint("CheckResult")
  private fun setPokemonAbilities(pokemon: PokemonEntity) {
    try {
      // Pokemon Ability 1
      val path1 = pokemon.abilities[0].url.substringAfter("ability/")
      val ability1 = service.requestAbility(path1).blockingGet()
      ability1.flavor_text_entries.find { it.language.name == "en" }?.let {
        pokemon.abilities[0].url = it.flavor_text
      }
      // Pokemon Ability 2 in case it exists
      if (pokemon.abilities.size > 1) {
        val path2 = pokemon.abilities[1].url.substringAfter("ability/")
        val ability2 = service.requestAbility(path2).blockingGet()
        ability2.flavor_text_entries.find { it.language.name == "en" }?.let {
          pokemon.abilities[1].url = it.flavor_text
        }
      }
    } catch (e: Exception) {
      error.postValue(e.message)
    }
  }

  @SuppressLint("CheckResult")
  private fun setPokemonSpecies(id: Int, pokemon: PokemonEntity) {
    try {
      val species = service.requestSpecies(id).blockingGet()
      // Pokemon Description
      species.flavor_text_entries.find { it.language.name == "en" }?.let { flavor ->
        pokemon.description = flavor.flavor_text
      }
      // Pokemon Category
      species.genera.find { it.language.name == "en" }?.let { gerera ->
        pokemon.category = gerera.genus
      }
    } catch (e: Exception) {
      error.postValue(e.message)
    }
  }

  override fun onCleared() {
    super.onCleared()
    disposables.clear()
  }

}
