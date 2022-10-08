package com.silvestr.foosballmatches.ui.players

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.silvestr.foosballmatches.FoosballApplication
import com.silvestr.foosballmatches.databinding.FragmentPlayersBinding
import com.silvestr.foosballmatches.di.ViewModelFactory
import com.silvestr.foosballmatches.ui.games.GamesViewModel
import javax.inject.Inject

class PlayersFragment : Fragment() {

    private var _binding: FragmentPlayersBinding? = null
    private val binding get() = _binding!!

    @Inject
    lateinit var viewModelFactory: ViewModelFactory
    var playersViewModel: PlayersViewModel? = null
    var gamesViewModel: GamesViewModel? = null

    private val adapter: PlayersAdapter by lazy { PlayersAdapter() }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        (activity?.application as FoosballApplication).appComponent.inject(this)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentPlayersBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        playersViewModel =
            ViewModelProvider(requireActivity(), viewModelFactory)[PlayersViewModel::class.java]
        gamesViewModel =
            ViewModelProvider(requireActivity(), viewModelFactory)[GamesViewModel::class.java]

        gamesViewModel?.games?.observe(requireActivity()) {
            playersViewModel?.loadPlayers()
        }

        playersViewModel?.players?.observe(requireActivity()) {
            adapter.submitList(it.toList())
        }

        val playersRecycler = binding.recyclerPlayers
        playersRecycler.layoutManager = LinearLayoutManager(context)
        playersRecycler.adapter = adapter
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}