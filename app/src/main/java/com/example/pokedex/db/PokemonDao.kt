package com.example.pokedex.db

import androidx.room.*
import com.example.pokedex.model.PokemonEntity

@Dao
interface PokemonDao {

  @Query("SELECT * FROM PokemonEntity")
  fun getAll(): List<PokemonEntity>

  @Query("SELECT * FROM PokemonEntity WHERE id LIKE :id LIMIT 1")
  fun findById(id: String): PokemonEntity

  @Query("SELECT * FROM PokemonEntity WHERE id LIKE :query OR id LIKE :query")
  fun queryByIdAndName(query: String): List<PokemonEntity>

  @Insert
  fun insertAll(pokemon: List<PokemonEntity>)

  @Delete
  fun delete(pokemon: PokemonEntity)

  @Insert(onConflict = OnConflictStrategy.IGNORE)
  fun insert(entity: PokemonEntity): Long

  @Update(onConflict = OnConflictStrategy.REPLACE)
  fun update(entity: PokemonEntity)

  @Transaction
  fun upsert(entity: PokemonEntity) {
    val id = insert(entity)
    if (id == -1L) {
      update(entity)
    }
  }
}