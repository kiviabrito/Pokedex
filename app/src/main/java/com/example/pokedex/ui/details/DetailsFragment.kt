package com.example.pokedex.ui.details

import android.app.AlertDialog
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.PagerSnapHelper
import androidx.recyclerview.widget.RecyclerView
import com.example.pokedex.MainActivity
import com.example.pokedex.MainViewModel
import com.example.pokedex.R
import com.example.pokedex.model.PokemonEntity
import com.example.pokedex.utility.LinePagerIndicatorDecoration
import com.example.pokedex.utility.setBackgroundColor
import kotlinx.android.synthetic.main.details_fragment.view.*


class DetailsFragment : Fragment(){

  // MARK: Init

  companion object {
    fun newInstance() = DetailsFragment()
  }

  private lateinit var root: View
  private lateinit var recyclerView: RecyclerView
  private lateinit var mainActivity: MainActivity
  private val viewModel: MainViewModel by activityViewModels()

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
    val id = arguments?.getInt("ID")
    setupRecyclerView()
    id?.let {
      root.progress_bar.visibility = View.VISIBLE
      observers(it)
      viewModel.getPokemonById(it)
    }
  }

  private fun setupRecyclerView() {
    recyclerView = root.findViewById(R.id.pokemon_photo_list)
    val layoutManager = LinearLayoutManager(requireContext(), RecyclerView.HORIZONTAL, false)
    recyclerView.layoutManager = layoutManager
    recyclerView.adapter = PhotosAdapter(arrayListOf())
    // add pager behavior
    val snapHelper = PagerSnapHelper()
    snapHelper.attachToRecyclerView(recyclerView)
  }

  // MARK: Observers

  private fun observers(id: Int) {
    viewModel.pokemonDetails.observe(viewLifecycleOwner, Observer { pokemon ->
      if (pokemon.id == id) {
        populateView(pokemon)
        root.progress_bar.visibility = View.GONE
      }
    })

    viewModel.error.observe(viewLifecycleOwner, Observer { error ->
      root.progress_bar.visibility = View.GONE
      Toast.makeText(requireContext(), error, Toast.LENGTH_LONG).show()
    })
  }

  // MARK: Helper Functions

  private fun populateView(pokemon: PokemonEntity) {

    root.pokemon_description_input.text = pokemon.description.replace("\n", " ")

    // Height and Weight

    val height = (pokemon.height.toDouble()/10)
    val weight = (pokemon.weight.toDouble()/10)
    root.height_input.text = "$height m"
    root.weight_input.text = "$weight kg"

    // Photos

    recyclerView.adapter = PhotosAdapter(pokemon.photos.filterNotNull())
    mainActivity.supportActionBar?.title = "#${String.format("%03d", pokemon.id)} - ${pokemon.name.capitalize()}"
    // pager indicator
    recyclerView.addItemDecoration(LinePagerIndicatorDecoration())

    // Category

    root.category1.text = pokemon.category.capitalize()

    // Abilities

    when (pokemon.abilities.count()) {
      0 -> {
        root.ability_1.visibility = View.GONE
        root.ability_2.visibility = View.GONE
      }
      1 -> {
        root.ability_1.visibility = View.VISIBLE
        root.ability_2.visibility = View.GONE
        root.ability_1.text = pokemon.abilities[0].name.capitalize() + "  "
      }
      2 -> {
        root.ability_1.visibility = View.VISIBLE
        root.ability_2.visibility = View.VISIBLE
        root.ability_1.text = pokemon.abilities[1].name.capitalize() + "  "
        root.ability_2.text = pokemon.abilities[0].name.capitalize() + "  "
      }
    }

    root.ability_1.setOnClickListener {
      showAbilityDescriptionDialog(pokemon, 0)
    }
    root.ability_2.setOnClickListener {
      showAbilityDescriptionDialog(pokemon, 1)
    }


    // Types

    when (pokemon.types.count()) {
      0 -> {
        root.type_1.visibility = View.GONE
        root.type_2.visibility = View.GONE
      }
      1 -> {
        root.type_1.visibility = View.VISIBLE
        root.type_2.visibility = View.GONE
        root.type_1.text = pokemon.types[0].capitalize()
        requireContext().setBackgroundColor(root.type_1, pokemon.types[0])
      }
      2 -> {
        root.type_1.visibility = View.VISIBLE
        root.type_2.visibility = View.VISIBLE
        root.type_1.text = pokemon.types[1].capitalize()
        root.type_2.text = pokemon.types[0].capitalize()
        requireContext().setBackgroundColor(root.type_1, pokemon.types[1])
        requireContext().setBackgroundColor(root.type_2, pokemon.types[0])
      }
    }

    // Stats

    root.speed_input.text = pokemon.stats[0].base_stat.toString()
    root.special_defense_input.text = pokemon.stats[1].base_stat.toString()
    root.special_attach_input.text = pokemon.stats[2].base_stat.toString()
    root.defense_input.text = pokemon.stats[3].base_stat.toString()
    root.attach_input.text = pokemon.stats[4].base_stat.toString()
    root.hp_input.text = pokemon.stats[5].base_stat.toString()

  }

  private fun showAbilityDescriptionDialog(pokemon: PokemonEntity, index: Int) {
    AlertDialog.Builder(requireContext())
      .setMessage(pokemon.abilities[index].url.replace("\n", " "))
      .setTitle(pokemon.abilities[index].name.capitalize())
      .setPositiveButton("OK") { dialog, _ ->
        dialog.cancel()
      }
      .show()
  }

}
