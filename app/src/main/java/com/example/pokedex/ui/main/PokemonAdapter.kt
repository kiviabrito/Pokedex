package com.example.pokedex.ui.main

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.pokedex.R
import com.example.pokedex.model.PokemonEntity
import com.example.pokedex.utility.setBackgroundColor
import com.example.pokedex.utility.toThreeDigString
import java.util.*

interface PokemonDetailsView {
  fun openPokemon(id: Int)
}

class PokemonAdapter(private var items: ArrayList<PokemonEntity>) :
  RecyclerView.Adapter<PokemonAdapter.PokemonViewHolder>() {

  var pokemonDetailsView : PokemonDetailsView? = null

  override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PokemonViewHolder {
    val view = LayoutInflater.from(parent.context).inflate(R.layout.item_pokemon_list, parent, false)
    return PokemonViewHolder(view)
  }

  override fun getItemCount(): Int {
    return items.size
  }

  fun setItemsAdapter(newList: ArrayList<PokemonEntity>) {
    val oldList = items
    val futureList = arrayListOf<PokemonEntity>()
    (oldList + newList).distinct().flatMapTo(futureList) { arrayListOf(it) }
    val diffCallback = PokemonDiffCallback(oldList, futureList)
    val diffResult = DiffUtil.calculateDiff(diffCallback)
    items = futureList
    diffResult.dispatchUpdatesTo(this)
  }

  @ExperimentalStdlibApi
  override fun onBindViewHolder(holder: PokemonViewHolder, position: Int) {
    val item  = items[position]
    holder.bindItems(item)

    holder.itemView.setOnClickListener {
      pokemonDetailsView?.openPokemon(item.id)
    }
  }

  class PokemonViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

    @ExperimentalStdlibApi
    fun bindItems(item : PokemonEntity) {

      val imageView: ImageView = itemView.findViewById(R.id.pokemon_photo) as ImageView
      val nameTextView: TextView = itemView.findViewById(R.id.pokemon_name) as TextView
      val idTextView: TextView = itemView.findViewById(R.id.pokemon_id) as TextView
      val type1TextView = itemView.findViewById(R.id.type_1) as TextView
      val type2TextView: TextView = itemView.findViewById(R.id.type_2) as TextView

      nameTextView.text = item.name.toUpperCase(Locale.ROOT)
      idTextView.text = "#${item.id.toThreeDigString()}"

      Glide.with(itemView.context)
        .load(item.photos[0])
        .into(imageView)

      when (item.types.count()) {
        0 -> {
          type1TextView.visibility = View.GONE
          type2TextView.visibility = View.GONE
        }
        1 -> {
          type1TextView.visibility = View.VISIBLE
          type2TextView.visibility = View.GONE
          type1TextView.text = item.types[0].capitalize(Locale.ROOT)
          itemView.context.setBackgroundColor(type1TextView, item.types[0])
        }
        2 -> {
          type1TextView.visibility = View.VISIBLE
          type2TextView.visibility = View.VISIBLE
          type1TextView.text = item.types[1].capitalize(Locale.ROOT)
          type2TextView.text = item.types[0].capitalize(Locale.ROOT)
          itemView.context.setBackgroundColor(type1TextView, item.types[1])
          itemView.context.setBackgroundColor(type2TextView, item.types[0])
        }
      }
    }

  }

  class PokemonDiffCallback(
    private val oldList: List<PokemonEntity>,
    private val newList: List<PokemonEntity>
  ) : DiffUtil.Callback() {

    override fun getOldListSize(): Int {
      return oldList.size
    }

    override fun getNewListSize(): Int {
      return newList.size
    }

    override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
      return oldList[oldItemPosition].id == newList[newItemPosition].id
    }

    override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
      return oldList[oldItemPosition].equals(newList[newItemPosition])
    }
  }

}