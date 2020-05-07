package com.example.pokedex.ui.main

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import com.example.pokedex.R

class MainFragment : Fragment() {

    companion object {
        fun newInstance() = MainFragment()
    }

    private val viewModel: MainViewModel by activityViewModels()
    private lateinit var root: View

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View {
        root = inflater.inflate(R.layout.main_fragment, container, false)
        viewModel.getPokemonList(0)
        viewModel.getPokemonById(2)
        viewModel.getSpeciesById(62)
        viewModel.getEvolutionById(2)

        return root
    }

}
