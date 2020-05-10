package com.example.pokedex.ui.details

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.pokedex.R

class PhotosAdapter(private val urlList: List<String>) : RecyclerView.Adapter<PhotosAdapter.ViewHolder>() {

  override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
    return ViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.item_photo_list, parent, false))
  }

  override fun onBindViewHolder(holder: ViewHolder, position: Int) {
    holder.bindItems(urlList[position], position)
  }

  override fun getItemCount(): Int {
    return urlList.size
  }

  class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
    fun bindItems(url: String, position: Int) {

      val imageView = itemView.findViewById<ImageView>(R.id.pokemon_photo)
      val textView = itemView.findViewById<TextView>(R.id.image_description)

      Glide.with(itemView.context)
        .load(url)
        .into(imageView)

      when (position) {
        0 -> {
          textView.text = "Front Default"
        }
        1 -> {
          textView.text = "Front Default"
        }
        2 -> {
          textView.text = "Back Default"
        }
        3 -> {
          textView.text = "Front Shiny"
        }
        4 -> {
          textView.text = "Back Shiny"
        }
        5 -> {
          textView.text = "Front Female"
        }
        6 -> {
          textView.text = "Back Female"
        }
        7 -> {
          textView.text = "Front Shiny Female"
        }
        8 -> {
          textView.text = "Back Shiny Female"
        }
      }
    }
  }
}