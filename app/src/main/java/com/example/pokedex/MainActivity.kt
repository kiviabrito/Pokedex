package com.example.pokedex

import android.graphics.Color
import android.os.Bundle
import android.view.View
import android.widget.RelativeLayout
import android.widget.TextView
import androidx.appcompat.app.ActionBar
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.res.ResourcesCompat
import com.example.pokedex.ui.main.MainFragment
import kotlinx.android.synthetic.main.main_activity.*

class MainActivity : AppCompatActivity() {

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    setContentView(R.layout.main_activity)
    if (savedInstanceState == null) {
      supportFragmentManager.beginTransaction()
        .replace(R.id.container, MainFragment.newInstance())
        .commitNow()
    }
    changeTitle(getString(R.string.app_name))
  }

  fun changeTitle(title: String) {
    val headerText = TextView(applicationContext)
    val lp = RelativeLayout.LayoutParams(ActionBar.LayoutParams.WRAP_CONTENT, ActionBar.LayoutParams.WRAP_CONTENT)
    headerText.layoutParams = lp
    headerText.text = title
    headerText.textSize = 20f
    headerText.setTextColor(Color.parseColor("#FFFFFF"))
    val tf = ResourcesCompat.getFont(applicationContext, R.font.aclonica)
    headerText.typeface = tf
    supportActionBar?.displayOptions = ActionBar.DISPLAY_SHOW_CUSTOM
    supportActionBar?.customView = headerText
  }

  fun showProgressBar(show: Boolean) {
    val progressBar = progress_bar
    if (show) {
      progressBar.visibility = View.VISIBLE
    } else {
      progressBar.visibility = View.GONE
    }
  }
}
