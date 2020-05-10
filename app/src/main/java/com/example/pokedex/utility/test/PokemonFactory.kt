package com.example.pokedex.utility.test

import com.example.pokedex.model.Detail
import com.example.pokedex.model.PokemonEntity
import com.example.pokedex.model.PokemonInfo
import com.example.pokedex.model.Sprites
import kotlin.random.Random

class PokemonFactory {

  companion object {

    fun createPokemonEntity(id: Int): PokemonEntity {
      return PokemonEntity(
        id = id,
        name = Random.toString(),
        photos = listOf(
          "https://assets.pokemon.com/assets/cms2/img/pokedex/full/$id.png",
          "pokemon.sprites.front_default",
          "pokemon.sprites.back_default",
          "pokemon.sprites.front_shiny",
          "pokemon.sprites.back_shiny",
          "pokemon.sprites.front_female",
          "pokemon.sprites.back_female",
          "pokemon.sprites.front_shiny_female",
          "pokemon.sprites.back_shiny_female"
        ),
        abilities = listOf(),
        forms = listOf(),
        height = 2,
        held_items = listOf(),
        is_default = true,
        moves = listOf(),
        order = id,
        species = Random.toString(),
        stats = listOf(),
        types = listOf(),
        weight = Random.nextInt(),
        category = Random.toString(),
        description = Random.toString()
      )
    }

    fun createPokemonEntityList(size: Int): List<PokemonEntity> {
      val finalList = mutableListOf<PokemonEntity>()
      for (i in 0 until size + 1) {
        val pokemon = PokemonEntity(
          id = i,
          name = Random.toString(),
          photos = listOf(
            "https://assets.pokemon.com/assets/cms2/img/pokedex/full/$i.png",
            "pokemon.sprites.front_default",
            "pokemon.sprites.back_default",
            "pokemon.sprites.front_shiny",
            "pokemon.sprites.back_shiny",
            "pokemon.sprites.front_female",
            "pokemon.sprites.back_female",
            "pokemon.sprites.front_shiny_female",
            "pokemon.sprites.back_shiny_female"
          ),
          abilities = listOf(),
          forms = listOf(),
          height = 2,
          held_items = listOf(),
          is_default = true,
          moves = listOf(),
          order = i,
          species = Random.toString(),
          stats = listOf(),
          types = listOf(),
          weight = Random.nextInt(),
          category = Random.toString(),
          description = Random.toString()
        )
        finalList.add(pokemon)
      }
      return finalList
    }

    fun createPokemonInfo(id: Int): PokemonInfo {
      return PokemonInfo(
        abilities = listOf(),
        base_experience = 1,
        forms = listOf(),
        game_indices = listOf(),
        height = 1,
        held_items = listOf(),
        id = id,
        is_default = true,
        location_area_encounters = Random.toString(),
        moves = listOf(),
        name = Random.toString(),
        order = id,
        species = Detail(Random.toString(), Random.toString()),
        sprites = Sprites(
          "pokemon.sprites.front_default",
          "pokemon.sprites.back_default",
          "pokemon.sprites.front_shiny",
          "pokemon.sprites.back_shiny",
          "pokemon.sprites.front_female",
          "pokemon.sprites.back_female",
          "pokemon.sprites.front_shiny_female",
          "pokemon.sprites.back_shiny_female"
        ),
        stats = listOf(),
        types = listOf(),
        weight = 1
      )
    }

  }

}

