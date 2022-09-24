package com.silvestr.foosballmatches.ui.players

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.silvestr.foosballmatches.databinding.FragmentPlayersBinding

class PlayersFragment : Fragment() {

    private var _binding: FragmentPlayersBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val notificationsViewModel =
            ViewModelProvider(this).get(PlayersViewModel::class.java)

        _binding = FragmentPlayersBinding.inflate(inflater, container, false)
        val root: View = binding.root

        val textView: TextView = binding.textPlayers
        notificationsViewModel.text.observe(viewLifecycleOwner) {
            textView.text = it
        }
        return root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}