package com.example.pokedex.utility

import android.content.Context
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.core.graphics.drawable.DrawableCompat
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.pokedex.R


fun RecyclerView.addOnScrolledToEnd(onScrolledToEnd: () -> Unit) {

  this.addOnScrollListener(object : RecyclerView.OnScrollListener() {
    private val visibleThreshold = 5
    private var loading = true
    private var previousTotal = 0

    override fun onScrollStateChanged(
      recyclerView: RecyclerView,
      newState: Int
    ) {
      with(layoutManager as LinearLayoutManager) {
        val visibleItemCount = childCount
        val totalItemCount = itemCount
        val firstVisibleItem = findFirstVisibleItemPosition()
        if (loading && totalItemCount > previousTotal) {
          loading = false
          previousTotal = totalItemCount
        }
        if (!loading && (totalItemCount - visibleItemCount) <= (firstVisibleItem + visibleThreshold)) {
          onScrolledToEnd()
          loading = true
        }
      }
    }
  })

}

fun Context.setBackgroundColor(view: TextView, type: String) {
  val background = view.background
  when (type) {
    "bug" -> {
      DrawableCompat.setTint(background, ContextCompat.getColor(this, R.color.colorBug))
      view.background = background
    }
    "dark" -> {
      DrawableCompat.setTint(background, ContextCompat.getColor(this, R.color.colorDark))
      view.background = background
    }
    "dragon" -> {
      DrawableCompat.setTint(background, ContextCompat.getColor(this, R.color.colorDragon))
      view.background = background
    }
    "electric" -> {
      DrawableCompat.setTint(background, ContextCompat.getColor(this, R.color.colorElectric))
      view.background = background
    }
    "fairy" -> {
      DrawableCompat.setTint(background, ContextCompat.getColor(this, R.color.colorFairy))
      view.background = background
    }
    "fighting" -> {
      DrawableCompat.setTint(background, ContextCompat.getColor(this, R.color.colorFighting))
      view.background = background
    }
    "fire" -> {
      DrawableCompat.setTint(background, ContextCompat.getColor(this, R.color.colorFire))
      view.background = background
    }
    "flying" -> {
      DrawableCompat.setTint(background, ContextCompat.getColor(this, R.color.colorFlying))
      view.background = background
    }
    "ghost" -> {
      DrawableCompat.setTint(background, ContextCompat.getColor(this, R.color.colorGhost))
      view.background = background
    }
    "grass" -> {
      DrawableCompat.setTint(background, ContextCompat.getColor(this, R.color.colorGrass))
      view.background = background
    }
    "ground" -> {
      DrawableCompat.setTint(background, ContextCompat.getColor(this, R.color.colorGround))
      view.background = background
    }
    "ice" -> {
      DrawableCompat.setTint(background, ContextCompat.getColor(this, R.color.colorIce))
      view.background = background
    }
    "normal" -> {
      DrawableCompat.setTint(background, ContextCompat.getColor(this, R.color.colorNormal))
      view.background = background
    }
    "poison" -> {
      DrawableCompat.setTint(background, ContextCompat.getColor(this, R.color.colorPoison))
      view.background = background
    }
    "psychic" -> {
      DrawableCompat.setTint(background, ContextCompat.getColor(this, R.color.colorPsychic))
      view.background = background
    }
    "rock" -> {
      DrawableCompat.setTint(background, ContextCompat.getColor(this, R.color.colorRock))
      view.background = background
    }
    "steel" -> {
      DrawableCompat.setTint(background, ContextCompat.getColor(this, R.color.colorSteel))
      view.background = background
    }
    "water" -> {
      DrawableCompat.setTint(background, ContextCompat.getColor(this, R.color.colorWater))
      view.background = background
    }
    else -> {
      DrawableCompat.setTint(background, ContextCompat.getColor(this, R.color.colorUnknown))
      view.background = background
    }
  }
}