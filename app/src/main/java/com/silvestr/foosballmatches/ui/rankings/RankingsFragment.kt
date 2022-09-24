package com.silvestr.foosballmatches.ui.rankings

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.silvestr.foosballmatches.databinding.FragmentRankingsBinding

class RankingsFragment : Fragment() {

    private var _binding: FragmentRankingsBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val homeViewModel =
            ViewModelProvider(this).get(RankingsViewModel::class.java)

        _binding = FragmentRankingsBinding.inflate(inflater, container, false)
        val root: View = binding.root

        val textView: TextView = binding.textRankings
        homeViewModel.text.observe(viewLifecycleOwner) {
            textView.text = it
        }
        return root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}