package com.example.pokedex.ui.details

import android.app.AlertDialog
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ProgressBar
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
import com.example.pokedex.utility.toThreeDigString
import kotlinx.android.synthetic.main.details_fragment.view.*


class DetailsFragment : Fragment(){

  // MARK: Init

  companion object {
    fun newInstance() = DetailsFragment()
  }

  private lateinit var root: View
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
    val id = arguments?.getInt(getString(R.string.id_bun))
    setupRecyclerView()
    id?.let {
      val progressBar = root.progress_bar
      progressBar.visibility = View.VISIBLE
      observers(progressBar, it)
      viewModel.getPokemonById(it)
    }
  }

  private fun setupRecyclerView() {
    val recyclerView = root.pokemon_photo_list
    val layoutManager = LinearLayoutManager(requireContext(), RecyclerView.HORIZONTAL, false)
    recyclerView.layoutManager = layoutManager
    // add pager behavior
    val snapHelper = PagerSnapHelper()
    snapHelper.attachToRecyclerView(recyclerView)
  }

  // MARK: Observers

  private fun observers(progressBar: ProgressBar, id: Int) {
    viewModel.pokemonDetails.observe(viewLifecycleOwner, Observer { pokemon ->
      if (pokemon.id == id) {
        populateView(pokemon)
        progressBar.visibility = View.GONE
      }
    })

    viewModel.error.observe(viewLifecycleOwner, Observer { error ->
      Toast.makeText(requireContext(), getString(R.string.error_message,error), Toast.LENGTH_LONG).show()
      progressBar.visibility = View.GONE
    })
  }

  // MARK: Populate View

  private fun populateView(pokemon: PokemonEntity) {
    (activity as MainActivity).changeTitle( "#${pokemon.id.toThreeDigString()} - ${pokemon.name.capitalize()}" )
    root.pokemon_description_input.text = pokemon.description.replace("\n", " ")
    // Height and Weight
    val height = (pokemon.height.toDouble()/10)
    val weight = (pokemon.weight.toDouble()/10)
    root.height_input.text = getString(R.string.unit_m,height)
    root.weight_input.text = getString(R.string.unit_kg,weight)
    // Photos
    root.pokemon_photo_list.adapter = PhotosAdapter(pokemon.photos.filterNotNull())
    root.pokemon_photo_list.addItemDecoration(LinePagerIndicatorDecoration())
    // Category
    root.category1.text = pokemon.category.capitalize()
    // Abilities
    populateAbilities(pokemon)
    // Types
    populateType(pokemon)
    // Stats
    populateStats(pokemon)
  }

  private fun populateAbilities(pokemon: PokemonEntity) {
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
      showAbilityDescriptionDialog(pokemon, 1)
    }
    root.ability_2.setOnClickListener {
      showAbilityDescriptionDialog(pokemon, 0)
    }
  }

  private fun showAbilityDescriptionDialog(pokemon: PokemonEntity, index: Int) {
    AlertDialog.Builder(requireContext())
      .setMessage(pokemon.abilities[index].url.replace("\n", " "))
      .setTitle(pokemon.abilities[index].name.capitalize())
      .setPositiveButton(getString(R.string.ok_btn)) { dialog, _ ->
        dialog.cancel()
      }
      .show()
  }

  private fun populateType(pokemon: PokemonEntity) {
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
  }

  private fun populateStats(pokemon: PokemonEntity) {
    root.speed_input.text = pokemon.stats[0].base_stat.toString()
    root.special_defense_input.text = pokemon.stats[1].base_stat.toString()
    root.special_attach_input.text = pokemon.stats[2].base_stat.toString()
    root.defense_input.text = pokemon.stats[3].base_stat.toString()
    root.attach_input.text = pokemon.stats[4].base_stat.toString()
    root.hp_input.text = pokemon.stats[5].base_stat.toString()
  }

}
