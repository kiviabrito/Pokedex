package com.example.pokedex.ui.details

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.pokedex.MainActivity
import com.example.pokedex.MainViewModel
import com.example.pokedex.R
import com.example.pokedex.ui.main.PokemonAdapter

class DetailsFragment : Fragment() {

  // MARK: Init

  companion object {
    fun newInstance() = DetailsFragment()
  }

  private lateinit var root: View
  private lateinit var recyclerView: RecyclerView
  private lateinit var mainActivity: MainActivity
  private val viewModel: MainViewModel by activityViewModels()
  private val adapter: PhotosAdapter by lazy { PhotosAdapter(ArrayList()) }

  override fun onCreateView(
    inflater: LayoutInflater, container: ViewGroup?,
    savedInstanceState: Bundle?
  ): View {
    root = inflater.inflate(R.layout.details_fragment, container, false)
    setupView()
    return root
  }

  private fun setupView() {
    mainActivity = activity as MainActivity
    mainActivity.showProgressBar(true)
    setupRecyclerView()
    observers()
    viewModel.getPokemonList(0, 20)
  }

  private fun setupRecyclerView() {
    recyclerView = root.findViewById(R.id.pokemon_list)
    val layoutManager = LinearLayoutManager(requireContext())
    recyclerView.layoutManager = layoutManager
    recyclerView.adapter = adapter
  }

  // MARK: Observers

  private fun observers() {

  }

  // MARK: SearchView


  // MARK: Helper Functions


}
