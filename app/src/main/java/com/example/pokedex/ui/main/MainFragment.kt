package com.example.pokedex.ui.main

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.pokedex.MainActivity
import com.example.pokedex.R


class MainFragment : Fragment() {

  // MARK: Init

  companion object {
    fun newInstance() = MainFragment()
  }

  private lateinit var root: View
  private lateinit var recyclerView: RecyclerView
  private lateinit var mainActivity: MainActivity
  private val viewModel: MainViewModel by activityViewModels()
  private val adapter: PokemonAdapter by lazy {
    PokemonAdapter(ArrayList(), this)
  }

  override fun onCreateView(
    inflater: LayoutInflater, container: ViewGroup?,
    savedInstanceState: Bundle?
  ): View {
    root = inflater.inflate(R.layout.main_fragment, container, false)
    setupView()
    return root
  }

  private fun setupView() {
    mainActivity = activity as MainActivity
    mainActivity.showProgressBar(true)
    setupRecyclerView()
    observers()
    viewModel.getPokemonList(0,20)
  }

  private fun setupRecyclerView() {
    recyclerView = root.findViewById(R.id.pokemon_list)
    val layoutManager = LinearLayoutManager(context)
    recyclerView.layoutManager = layoutManager
    recyclerView.adapter = adapter
    recyclerView.addOnScrolledToEnd {
      viewModel.getPokemonList(adapter.itemCount +1 , adapter.itemCount + 20)
    }
  }

  // MARK: Observers

  private fun observers() {
    viewModel.pokemonList.observe(viewLifecycleOwner, Observer { list ->
      mainActivity.showProgressBar(false)
      adapter.setItemsAdapter(list)
    })

    viewModel.error.observe(viewLifecycleOwner, Observer { error ->
      mainActivity.showProgressBar(false)
      Toast.makeText(context, error, Toast.LENGTH_LONG).show()
    })
  }

  // MARK: Helper Functions

  private fun RecyclerView.addOnScrolledToEnd(onScrolledToEnd: () -> Unit) {

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

}
