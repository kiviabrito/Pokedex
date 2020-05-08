package com.example.pokedex.ui.main

import android.graphics.Color
import android.os.Bundle
import android.view.*
import android.widget.EditText
import android.widget.ImageView
import android.widget.Toast
import androidx.appcompat.widget.SearchView
import androidx.core.content.ContextCompat
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
    setHasOptionsMenu(true)
    mainActivity = activity as MainActivity
    mainActivity.showProgressBar(true)
    setupRecyclerView()
    observers()
    viewModel.getPokemonList(0, 20)
  }

  override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
    searchBarFilter(menu, inflater)
    super.onCreateOptionsMenu(menu, inflater)
  }

  private fun searchBarFilter(menu: Menu, inflater: MenuInflater) {
    inflater.inflate(R.menu.search, menu)
    val searchItem = menu.findItem(R.id.action_search)
    val searchView = searchItem.actionView as SearchView
    searchView.setBackgroundColor(Color.WHITE)
    val editText = searchView.findViewById<EditText>(androidx.appcompat.R.id.search_src_text)
    editText.setHintTextColor(Color.LTGRAY)
    editText.setTextColor(ContextCompat.getColor(requireContext(), R.color.colorPrimaryDark))
    val clearIcon = searchView.findViewById<ImageView>(androidx.appcompat.R.id.search_close_btn)
    clearIcon.setColorFilter(ContextCompat.getColor(requireContext(), R.color.colorPrimaryDark))

    searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
      override fun onQueryTextSubmit(query: String?): Boolean {
        return true
      }

      override fun onQueryTextChange(newText: String?): Boolean {
        // filter
        return false
      }
    })
  }

  private fun setupRecyclerView() {
    recyclerView = root.findViewById(R.id.pokemon_list)
    val layoutManager = LinearLayoutManager(context)
    recyclerView.layoutManager = layoutManager
    recyclerView.adapter = adapter
    recyclerView.addOnScrolledToEnd {
      viewModel.getPokemonList(adapter.itemCount + 1, adapter.itemCount + 20)
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
