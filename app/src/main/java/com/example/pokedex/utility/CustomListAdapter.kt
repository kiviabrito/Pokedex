package com.example.pokedex.utility

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.annotation.LayoutRes
import com.bumptech.glide.Glide
import com.example.pokedex.R
import com.example.pokedex.model.PokemonEntity
import java.util.*

class CustomListAdapter(context: Context, @LayoutRes private val layoutResource: Int, private val pokemon: List<PokemonEntity>):
  ArrayAdapter<PokemonEntity>(context, layoutResource, pokemon),
  Filterable {
  private var pokemonList = pokemon

  override fun getCount(): Int {
    return pokemonList.size
  }

  override fun getItem(p0: Int): PokemonEntity? {
    return pokemonList[p0]
  }

  override fun getItemId(p0: Int): Long {
    // Or just return p0
    return pokemonList[p0].id.toLong()
  }

  @ExperimentalStdlibApi
  override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
    val view = convertView ?: LayoutInflater.from(context).inflate(layoutResource, parent, false)
    val pokemonPhoto: ImageView = view.findViewById(R.id.pokemon_photo) as ImageView
    val pokemonName: TextView = view.findViewById(R.id.pokemon_name) as TextView

    val pokemon = pokemonList[position]
    pokemonName.text = "#${pokemon.id.toThreeDigString()} - ${pokemon.name.capitalize(Locale.ROOT)}"
    Glide.with(context)
      .load(pokemon.photos[0])
      .into(pokemonPhoto)

    return view
  }

  override fun getFilter(): Filter {
    return object : Filter() {
      override fun publishResults(charSequence: CharSequence?, filterResults: FilterResults) {
        pokemonList = filterResults.values as List<PokemonEntity>
        notifyDataSetChanged()
      }

      override fun performFiltering(charSequence: CharSequence?): FilterResults {
        val queryString = charSequence?.toString()?.toLowerCase(Locale.ROOT)
        val filterResults = FilterResults()
        val suggestions = if (queryString==null || queryString.isEmpty())
         pokemon
        else {
         pokemon.filter {
            it.name.toLowerCase(Locale.ROOT).contains(queryString) ||
                it.id.toString().contains(queryString)
          }
        }
        filterResults.values = suggestions
        filterResults.count = suggestions.count()
        return filterResults
      }

      override fun convertResultToString(resultValue: Any?): CharSequence {
        return (resultValue as PokemonEntity).name
      }
    }
  }

}