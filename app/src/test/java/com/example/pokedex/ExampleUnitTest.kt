package com.example.pokedex

import androidx.room.Room
import com.example.pokedex.db.AppDatabase
import com.example.pokedex.model.Detail
import com.example.pokedex.model.PokemonEntity
import com.example.pokedex.model.PokemonInfo
import com.example.pokedex.model.Sprites
import com.example.pokedex.network.PokemonService
import io.reactivex.Single
import io.reactivex.disposables.CompositeDisposable
import org.junit.Assert.assertEquals
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mockito
import org.mockito.Mockito.`when`
import org.mockito.MockitoAnnotations
import org.mockito.junit.MockitoJUnitRunner


/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * See [testing documentation](http://d.android.com/tools/testing).
 */
@RunWith(MockitoJUnitRunner::class)
class ExampleUnitTest {
  @Test
  fun addition_isCorrect() {
    assertEquals(4, 2 + 2)
  }

  @Test
  fun testsWork() {
    assertTrue(true)
  }

}
