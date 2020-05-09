package com.example.pokedex.ui.main

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.core.graphics.drawable.DrawableCompat
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.pokedex.R
import com.example.pokedex.model.PokemonEntity
import java.util.*

class PokemonAdapter(private var items: ArrayList<PokemonEntity>, private val fragment: Fragment) :
  RecyclerView.Adapter<PokemonAdapter.UserViewHolder>() {

  override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): UserViewHolder {
    val view = LayoutInflater.from(parent.context).inflate(R.layout.item_pokemon_list, parent, false)
    return UserViewHolder(view)
  }

  override fun getItemCount(): Int {
    return items.size
  }

  fun getItem(position: Int): PokemonEntity {
    return items[position]
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
  override fun onBindViewHolder(holder: UserViewHolder, position: Int) {
    val item = items[position]
    holder.nameTextView.text = item.name.toUpperCase(Locale.ROOT)
    holder.idTextView.text = "#${item.id}"
    Glide.with(fragment)
      .load(item.photos[0])
      .into(holder.imageView)

    when (item.types.count()) {
      0 -> {
        holder.type1TextView.visibility = View.GONE
        holder.type2TextView.visibility = View.GONE
      }
      1 -> {
        holder.type1TextView.visibility = View.VISIBLE
        holder.type2TextView.visibility = View.GONE
        holder.type1TextView.text = item.types[0].capitalize(Locale.ROOT)
        setBackGroundColor(holder.type1TextView, item.types[0])
      }
      2 -> {
        holder.type1TextView.visibility = View.VISIBLE
        holder.type2TextView.visibility = View.VISIBLE
        holder.type1TextView.text = item.types[0].capitalize(Locale.ROOT)
        holder.type2TextView.text = item.types[1].capitalize(Locale.ROOT)
        setBackGroundColor(holder.type1TextView, item.types[0])
        setBackGroundColor(holder.type2TextView, item.types[1])
      }
    }
  }

  private fun setBackGroundColor(view: TextView, type: String) {
    val background = view.background
    when (type) {
      "bug" -> {
        DrawableCompat.setTint(background, ContextCompat.getColor(fragment.requireContext(), R.color.colorBug))
        view.background = background
      }
      "dark" -> {
        DrawableCompat.setTint(background, ContextCompat.getColor(fragment.requireContext(), R.color.colorDark))
        view.background = background
      }
      "dragon" -> {
        DrawableCompat.setTint(background, ContextCompat.getColor(fragment.requireContext(), R.color.colorDragon))
        view.background = background
      }
      "electric" -> {
        DrawableCompat.setTint(background, ContextCompat.getColor(fragment.requireContext(), R.color.colorElectric))
        view.background = background
      }
      "fairy" -> {
        DrawableCompat.setTint(background, ContextCompat.getColor(fragment.requireContext(), R.color.colorFairy))
        view.background = background
      }
      "fighting" -> {
        DrawableCompat.setTint(background, ContextCompat.getColor(fragment.requireContext(), R.color.colorFighting))
        view.background = background
      }
      "fire" -> {
        DrawableCompat.setTint(background, ContextCompat.getColor(fragment.requireContext(), R.color.colorFire))
        view.background = background
      }
      "flying" -> {
        DrawableCompat.setTint(background, ContextCompat.getColor(fragment.requireContext(), R.color.colorFlying))
        view.background = background
      }
      "ghost" -> {
        DrawableCompat.setTint(background, ContextCompat.getColor(fragment.requireContext(), R.color.colorGhost))
        view.background = background
      }
      "grass" -> {
        DrawableCompat.setTint(background, ContextCompat.getColor(fragment.requireContext(), R.color.colorGrass))
        view.background = background
      }
      "ground" -> {
        DrawableCompat.setTint(background, ContextCompat.getColor(fragment.requireContext(), R.color.colorGround))
        view.background = background
      }
      "ice" -> {
        DrawableCompat.setTint(background, ContextCompat.getColor(fragment.requireContext(), R.color.colorIce))
        view.background = background
      }
      "normal" -> {
        DrawableCompat.setTint(background, ContextCompat.getColor(fragment.requireContext(), R.color.colorNormal))
        view.background = background
      }
      "poison" -> {
        DrawableCompat.setTint(background, ContextCompat.getColor(fragment.requireContext(), R.color.colorPoison))
        view.background = background
      }
      "psychic" -> {
        DrawableCompat.setTint(background, ContextCompat.getColor(fragment.requireContext(), R.color.colorPsychic))
        view.background = background
      }
      "rock" -> {
        DrawableCompat.setTint(background, ContextCompat.getColor(fragment.requireContext(), R.color.colorRock))
        view.background = background
      }
      "steel" -> {
        DrawableCompat.setTint(background, ContextCompat.getColor(fragment.requireContext(), R.color.colorSteel))
        view.background = background
      }
      "water" -> {
        DrawableCompat.setTint(background, ContextCompat.getColor(fragment.requireContext(), R.color.colorWater))
        view.background = background
      }
      else -> {
        DrawableCompat.setTint(background, ContextCompat.getColor(fragment.requireContext(), R.color.colorUnknown))
        view.background = background
      }
    }
  }

  class UserViewHolder(view: View) : RecyclerView.ViewHolder(view) {
    var imageView: ImageView = view.findViewById(R.id.pokemon_photo) as ImageView
    var nameTextView: TextView = view.findViewById(R.id.pokemon_name) as TextView
    var idTextView: TextView = view.findViewById(R.id.pokemon_id) as TextView
    var type1TextView: TextView = view.findViewById(R.id.type_1) as TextView
    var type2TextView: TextView = view.findViewById(R.id.type_2) as TextView
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