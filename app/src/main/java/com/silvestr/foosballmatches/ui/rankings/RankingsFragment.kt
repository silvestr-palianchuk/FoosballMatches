package com.silvestr.foosballmatches.ui.rankings

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.silvestr.foosballmatches.FoosballApplication
import com.silvestr.foosballmatches.databinding.FragmentRankingsBinding
import com.silvestr.foosballmatches.di.ViewModelFactory
import com.silvestr.foosballmatches.ui.games.GamesViewModel
import javax.inject.Inject

class RankingsFragment : Fragment() {

    companion object {
        val DEFAULT_SORT_TYPE = SortType.WON
    }

    private var _binding: FragmentRankingsBinding? = null
    private val binding get() = _binding!!

    @Inject
    lateinit var viewModelFactory: ViewModelFactory
    var rankingsViewModel: RankingsViewModel? = null
    var gamesViewModel: GamesViewModel? = null

    private val adapter: RankingsAdapter by lazy { RankingsAdapter() }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        (activity?.application as FoosballApplication).appComponent.inject(this)
    }

    override fun onCreateView(
            inflater: LayoutInflater,
            container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View {
        _binding = FragmentRankingsBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        rankingsViewModel = ViewModelProvider(requireActivity(), viewModelFactory)[RankingsViewModel::class.java]
        gamesViewModel = ViewModelProvider(requireActivity(), viewModelFactory)[GamesViewModel::class.java]

        rankingsViewModel?.rankings?.observe(requireActivity()) {
            adapter.updateRankings(it, rankingsViewModel?.sortType ?: DEFAULT_SORT_TYPE)
        }

        gamesViewModel?.games?.observe(requireActivity()) {
           rankingsViewModel?.loadRankings()
        }

        val rankingsRecycler = binding.recyclerRankings
        rankingsRecycler.layoutManager = LinearLayoutManager(context)
        rankingsRecycler.adapter = adapter
        rankingsRecycler.addItemDecoration(DividerItemDecoration(requireActivity().applicationContext, RecyclerView.VERTICAL))

        binding.played.apply {
            isActivated = rankingsViewModel?.sortType == SortType.PLAYED

            setOnClickListener {
                if (it.isActivated.not()) {
                    rankingsViewModel?.sortType = SortType.PLAYED
                    it.isActivated = true
                    binding.won.isActivated = false
                    rankingsViewModel?.sortRankings(SortType.PLAYED)
                }
            }
        }

        binding.won.apply {
            isActivated = rankingsViewModel?.sortType == SortType.WON

            setOnClickListener {
                if (it.isActivated.not()) {
                    rankingsViewModel?.sortType = SortType.WON
                    it.isActivated = true
                    binding.played.isActivated = false
                    rankingsViewModel?.sortRankings(SortType.WON)
                }
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}