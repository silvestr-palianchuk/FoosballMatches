package com.silvestr.foosballmatches.ui.games

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
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
    var gamesViewModel: GamesViewModel? = null

    private val adapter: GamesAdapter by lazy {
        GamesAdapter(
            editClickListener = { _, game -> showEditGameDialogFragment(game) },
            deleteClickListener = { _, game -> gamesViewModel?.deleteGame(game) })
    }

    private fun showEditGameDialogFragment(game: Game) {
        val index = gamesViewModel?.getGameIndex(game)

        if (index != null)
            EditGameDialogFragment.showDialog(childFragmentManager, game, index)
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

        gamesViewModel =
            ViewModelProvider(requireActivity(), viewModelFactory)[GamesViewModel::class.java]
        gamesViewModel?.games?.observe(requireActivity()) {
            adapter.updateGames(it)
        }

        val gameRecycler = binding.recyclerGame
        gameRecycler.layoutManager = LinearLayoutManager(context)
        gameRecycler.adapter = adapter
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}