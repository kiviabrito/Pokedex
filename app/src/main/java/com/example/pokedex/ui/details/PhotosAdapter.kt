package com.example.pokedex.ui.details

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.pokedex.R

class PhotosAdapter(private val urlList: ArrayList<String>) : RecyclerView.Adapter<PhotosAdapter.ViewHolder>() {

  override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
    return ViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.item_photo_list, parent, false))
  }

  override fun onBindViewHolder(holder: ViewHolder, position: Int) {
    holder.bindItems(urlList[position])
  }

  override fun getItemCount(): Int {
    return urlList.size
  }

  class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
    fun bindItems(url: String) {
      val imageView = itemView.findViewById<ImageView>(R.id.pokemon_photo)
      Glide.with(itemView.context)
        .load(url)
        .into(imageView)
    }
  }
}