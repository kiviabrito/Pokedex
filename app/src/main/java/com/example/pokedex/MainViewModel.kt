package com.example.pokedex

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

  // Handle Details Fragment Data
  private val _pokemonDetails: MutableLiveData<PokemonEntity> = MutableLiveData()
  val pokemonDetails: LiveData<PokemonEntity> = _pokemonDetails

  init {
    getPokemonList(0, 20)
  }

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
        this.error.postValue(error.message)
      })
    disposables.add(disposable)
  }

  private fun fetchFirst20PokemonList() {
    val firstList = mutableListOf<PokemonEntity>()
    for (i in 1 until 21) {
      val disposable = service.requestPokemon(i)
        .map { pokemonInfo ->
          updateDataBase(pokemonInfo)
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
        .map { pokemonInfo ->
          updateDataBase(pokemonInfo)
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
        this.error.postValue(error.message)
      })
    disposables.add(disposable)
  }

  private fun setPokemonAbilities(pokemon: PokemonEntity) {
    try {
      val firstPath = pokemon.abilities[0].url.substringAfter("ability/")
      val firstAbility = service.requestAbility(firstPath).blockingGet()
      firstAbility.flavor_text_entries.find { it.language.name == "en" }?.let {
        pokemon.abilities[0].url = it.flavor_text
      }
      if (pokemon.abilities.size > 1) {
        val secondPath = pokemon.abilities[1].url.substringAfter("ability/")
        val secondAbility = service.requestAbility(secondPath).blockingGet()
        secondAbility.flavor_text_entries.find { it.language.name == "en" }?.let {
          pokemon.abilities[1].url = it.flavor_text
        }
      }
    } catch (e: Exception) {
      error.postValue(e.message)
    }
  }

  private fun setPokemonSpecies(id: Int, pokemon: PokemonEntity) {
    try {
      val species = service.requestSpecies(id).blockingGet()
      species.flavor_text_entries.find { it.language.name == "en" }?.let { flavor ->
        pokemon.description = flavor.flavor_text
      }
      species.genera.find { it.language.name == "en" }?.let { gerera ->
        pokemon.category = gerera.genus
      }
    } catch (e: Exception) {
      this.error.postValue(e.message)
    }
  }

  override fun onCleared() {
    super.onCleared()
    disposables.clear()
  }

}
