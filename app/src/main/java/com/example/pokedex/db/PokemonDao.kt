package com.example.pokedex.db

import androidx.room.*
import com.example.pokedex.model.PokemonEntity
import io.reactivex.Single

@Dao
interface PokemonDao {

  @Query("SELECT * FROM PokemonEntity")
  fun selectAll(): Single<List<PokemonEntity>>

  @Query("SELECT * FROM PokemonEntity WHERE id BETWEEN :from AND :to")
  fun select20(from: Int, to: Int): Single<List<PokemonEntity>>

  @Query("SELECT * FROM PokemonEntity WHERE id LIKE :id LIMIT 1")
  fun findById(id: Int): Single<PokemonEntity>

  @Query("SELECT * FROM PokemonEntity WHERE id LIKE :query OR name LIKE :query OR species LIKE :query")
  fun queryByIdAndName(query: String): Single<List<PokemonEntity>>

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