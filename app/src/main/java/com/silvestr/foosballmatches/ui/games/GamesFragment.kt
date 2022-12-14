package com.silvestr.foosballmatches.ui.games

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.silvestr.foosballmatches.FoosballApplication
import com.silvestr.foosballmatches.data.Game
import com.silvestr.foosballmatches.databinding.FragmentGamesBinding
import com.silvestr.foosballmatches.di.ViewModelFactory
import javax.inject.Inject

class GamesFragment : Fragment() {

    private var _binding: FragmentGamesBinding? = null
    private val binding get() = _binding!!

    @Inject
    lateinit var viewModelFactory: ViewModelFactory

    private val gamesViewModel: GamesViewModel by lazy {
        ViewModelProvider(
            requireActivity(), //using activity context in order to share GamesViewModel between EditGameDialogFragment, AddGameDialogFragment, GamesFragment
            viewModelFactory
        )[GamesViewModel::class.java]
    }


    private val adapter: GamesAdapter by lazy {
        GamesAdapter(
            editClickListener = { _, game, position -> showEditGameDialogFragment(game, position) },
            deleteClickListener = { _, game -> gamesViewModel.deleteGame(game) })
    }

    private fun showEditGameDialogFragment(game: Game, position: Int) {
        EditGameDialogFragment.showDialog(childFragmentManager, game, position)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        (activity?.application as FoosballApplication).appComponent.inject(this)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentGamesBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        gamesViewModel.games.observe(viewLifecycleOwner) {
            adapter.submitList(it)
        }

        val gameRecycler = binding.recyclerGame
        gameRecycler.layoutManager = LinearLayoutManager(context)
        gameRecycler.adapter = adapter
        gameRecycler.addItemDecoration(
            DividerItemDecoration(
                requireActivity().applicationContext,
                RecyclerView.VERTICAL
            )
        )

        binding.buttonAdd.setOnClickListener {
            AddGameDialogFragment.showDialog(childFragmentManager)
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}