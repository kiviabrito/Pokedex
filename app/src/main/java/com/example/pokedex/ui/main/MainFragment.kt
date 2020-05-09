package com.example.pokedex.ui.main

import android.graphics.Color
import android.os.Bundle
import android.view.*
import android.widget.AutoCompleteTextView
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
import com.example.pokedex.model.PokemonEntity
import com.example.pokedex.utility.CustomListAdapter

class MainFragment : Fragment() {

  // MARK: Init

  companion object {
    fun newInstance() = MainFragment()
  }

  private lateinit var root: View
  private lateinit var recyclerView: RecyclerView
  private lateinit var mainActivity: MainActivity
  private var searchAutoComplete: AutoCompleteTextView? = null
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
    searchAutoComplete = searchView.findViewById(androidx.appcompat.R.id.search_src_text)
    searchAutoComplete?.let { autoComplete ->
      autoComplete.setHintTextColor(Color.LTGRAY)
      autoComplete.setTextColor(ContextCompat.getColor(context!!, R.color.colorPrimaryDark))
      autoComplete.setDropDownBackgroundResource(R.drawable.rounded_corners)
      autoComplete.setBackgroundColor(Color.WHITE)
    }
    setAutocompleteListeners(searchView)
    super.onCreateOptionsMenu(menu, inflater)
  }

  private fun setAutocompleteListeners(searchView: SearchView) {
    searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
      override fun onQueryTextSubmit(query: String?): Boolean {
        searchView.clearFocus()
        return true
      }

      override fun onQueryTextChange(newText: String?): Boolean {
        newText?.let { text ->
          searchAutoComplete?.let { autoComplete ->
            if (text.length > 1 && (autoComplete.adapter == null || autoComplete.adapter.isEmpty)) {
              viewModel.queryPokemon(newText)
            }
          }
        }
        return false
      }
    })

    searchAutoComplete?.setOnItemClickListener { parent, _, position, _ ->
      val selectedPokemon = parent.adapter.getItem(position) as PokemonEntity?
      if (selectedPokemon != null) {
        println("open pokemon ${selectedPokemon.name}")
        searchAutoComplete?.text?.clear()
      }
    }
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
    viewModel.pokemonListAutoComplete.observe(viewLifecycleOwner, Observer { list ->
      mainActivity.showProgressBar(false)
      val adapter = CustomListAdapter(context!!, R.layout.item_autocomplete_text, list)
      searchAutoComplete?.setAdapter(adapter)
    })

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
