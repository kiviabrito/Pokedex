package com.example.pokedex

import androidx.room.Room
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.platform.app.InstrumentationRegistry
import com.example.pokedex.db.AppDatabase
import com.example.pokedex.utility.testUtil.PokemonFactory
import org.junit.After
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith

/**
 * Instrumented test, which will execute on an Android device.
 *
 * See [testing documentation](http://d.android.com/tools/testing).
 */
@RunWith(AndroidJUnit4::class)
class AppDataBaseInstrumentedTest {

  private lateinit var database: AppDatabase

  @Test
  fun useAppContext() {
    // Context of the app under test.
    val appContext = InstrumentationRegistry.getInstrumentation().targetContext
    assertEquals("com.example.pokedex", appContext.packageName)
  }

  @Before
  fun initDb() {
    database = Room.inMemoryDatabaseBuilder(
      InstrumentationRegistry.getInstrumentation().targetContext,
      AppDatabase::class.java
    ).build()
  }

  @After
  fun closeDb() {
    database.close()
  }

  @Test
  fun insertPokemon() {
    val pokemon = PokemonFactory.createPokemonEntity(1)
    database.pokemonDao().upsert(pokemon)
    val pokemonList = database.pokemonDao().selectAll()
    assert(pokemonList.blockingGet().isNotEmpty())
  }

  @Test
  fun getPokemonData() {
    val cachedPokemon = PokemonFactory.createPokemonEntityList(3)
    cachedPokemon.forEach {
      database.pokemonDao().upsert(it)
    }
    val retrievedPokemon = database.pokemonDao().selectAll().blockingGet()
    assert(retrievedPokemon == cachedPokemon.sortedWith(compareBy({ it.id }, { it.id })))
  }

  @Test
  fun clearPokemonData() {
    val pokemon = PokemonFactory.createPokemonEntity(1)
    database.pokemonDao().upsert(pokemon)
    database.pokemonDao().delete(pokemon)
    assert(database.pokemonDao().selectAll().blockingGet().isEmpty())
  }
}
