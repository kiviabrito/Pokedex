package com.example.pokedex.ui.main

import android.graphics.Color
import android.os.Bundle
import android.view.*
import android.widget.AutoCompleteTextView
import android.widget.ProgressBar
import android.widget.Toast
import androidx.appcompat.widget.SearchView
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.pokedex.MainActivity
import com.example.pokedex.MainViewModel
import com.example.pokedex.R
import com.example.pokedex.model.PokemonEntity
import com.example.pokedex.ui.details.DetailsFragment
import com.example.pokedex.utility.CustomListAdapter
import com.example.pokedex.utility.addOnScrolledToEnd
import kotlinx.android.synthetic.main.main_fragment.view.*

class MainFragment : Fragment() , PokemonDetailsView  {

  // MARK: Init

  companion object {
    fun newInstance() = MainFragment()
  }

  private lateinit var root: View
  private var searchAutoComplete: AutoCompleteTextView? = null
  private val viewModel: MainViewModel by activityViewModels()
  private val adapter: PokemonAdapter by lazy { PokemonAdapter(ArrayList()) }

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
    (activity as MainActivity).changeTitle( getString(R.string.app_name) )
    setupRecyclerView()
    val progressBar = root.progress_bar
    progressBar.visibility = View.VISIBLE
    observers(progressBar)
  }

  override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
    searchBar(menu, inflater)
    super.onCreateOptionsMenu(menu, inflater)
  }

  private fun setupRecyclerView() {
    val recyclerView = root.pokemon_list
    val layoutManager = LinearLayoutManager(requireContext())
    recyclerView.layoutManager = layoutManager
    adapter.pokemonDetailsView = this
    recyclerView.adapter = adapter
    recyclerView.addOnScrolledToEnd {
      viewModel.getPokemonList(adapter.itemCount + 1, adapter.itemCount + 20)
    }
  }

  // MARK: Observers

  private fun observers(progressBar: ProgressBar) {
    viewModel.pokemonListAutoComplete.observe(viewLifecycleOwner, Observer { list ->
      progressBar.visibility = View.GONE
      val adapter = CustomListAdapter(requireContext(), R.layout.item_autocomplete_text, list)
      searchAutoComplete?.setAdapter(adapter)
    })

    viewModel.pokemonList.observe(viewLifecycleOwner, Observer { list ->
      progressBar.visibility = View.GONE
      adapter.setItemsAdapter(list)
    })

    viewModel.error.observe(viewLifecycleOwner, Observer { error ->
      progressBar.visibility = View.GONE
      Toast.makeText(requireContext(), getString(R.string.error_message,error), Toast.LENGTH_LONG).show()
    })
  }

  // MARK: SearchView

  private fun searchBar(menu: Menu, inflater: MenuInflater) {
    inflater.inflate(R.menu.search, menu)
    val searchItem = menu.findItem(R.id.action_search)
    val searchView = searchItem.actionView as SearchView
    searchAutoComplete = searchView.findViewById(androidx.appcompat.R.id.search_src_text)
    searchAutoComplete?.let { autoComplete ->
      autoComplete.setHintTextColor(Color.LTGRAY)
      autoComplete.setTextColor(ContextCompat.getColor(requireContext(), R.color.colorPrimaryDark))
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
          if (text.count() == 1) {
            viewModel.queryPokemon(newText)
          } else if (text.isEmpty()) {
            val adapter = CustomListAdapter(requireContext(), R.layout.item_autocomplete_text, arrayListOf())
            searchAutoComplete?.setAdapter(adapter)
          }
        }
        return false
      }
    })

    searchAutoComplete?.setOnItemClickListener { parent, _, position, _ ->
      val selectedPokemon = parent.adapter.getItem(position) as PokemonEntity?
      if (selectedPokemon != null) {
        searchAutoComplete?.text?.clear()
        openPokemon(selectedPokemon.id)
      }
    }
  }

  override fun openPokemon(id: Int) {
    if (parentFragmentManager.findFragmentByTag(getString(R.string.details_fragment_tag)) == null) {
      val bundle = Bundle()
      bundle.putInt(getString(R.string.id_bun),id )
      val detailsFragment = DetailsFragment.newInstance()
      detailsFragment.arguments = bundle
      parentFragmentManager.beginTransaction()
        .replace(R.id.container, detailsFragment, getString(R.string.details_fragment_tag))
        .addToBackStack(null)
        .commit()
    }
  }

}
