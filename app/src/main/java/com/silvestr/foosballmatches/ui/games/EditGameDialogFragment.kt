package com.silvestr.foosballmatches.ui.games

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.FragmentManager
import androidx.lifecycle.ViewModelProvider
import com.silvestr.foosballmatches.FoosballApplication
import com.silvestr.foosballmatches.data.Game
import com.silvestr.foosballmatches.data.Player
import com.silvestr.foosballmatches.databinding.FragmentEditGameBinding
import com.silvestr.foosballmatches.di.ViewModelFactory
import com.silvestr.foosballmatches.utils.FragmentArg
import javax.inject.Inject


class EditGameDialogFragment : DialogFragment() {

    companion object {
        private val TAG = EditGameDialogFragment::class.java.simpleName

        fun showDialog(fragmentManager: FragmentManager, game: Game, index: Int) {
            val fragment = EditGameDialogFragment()
            fragment.game = game
            fragment.index = index
            fragment.show(fragmentManager, TAG)
        }
    }

    @Inject
    lateinit var viewModelFactory: ViewModelFactory
    var gamesViewModel: GamesViewModel? = null

    private var _binding: FragmentEditGameBinding? = null
    private val binding get() = _binding!!

    private var game by FragmentArg<Game>()
    private var index by FragmentArg<Int>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        (activity?.application as FoosballApplication).appComponent.inject(this)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentEditGameBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        gamesViewModel =
            ViewModelProvider(requireActivity(), viewModelFactory)[GamesViewModel::class.java]

        binding.game = game

        binding.buttonUpdate.setOnClickListener {
            val updatedPlayer1 = game?.player1?.copy(
                firstName = binding.editTextPlayer1FirstName.text.toString(),
                lastName = binding.editTextPlayer1LastName.text.toString()
            )
            val updatedPlayer2 = game?.player2?.copy(
                firstName = binding.editTextPlayer2FirstName.text.toString(),
                lastName = binding.editTextPlayer2LastName.text.toString()
            )
            val updatedGame = game?.copy(
                player1 = updatedPlayer1,
                player2 = updatedPlayer2,
                score1 = binding.editTextScore1.text.toString().toInt(),
                score2 = binding.editTextScore2.text.toString().toInt()
            )

            if (updatedGame != null)
                gamesViewModel?.editGame(updatedGame, index!!)

            dismiss()
        }
        binding.buttonCancel.setOnClickListener {
            dismiss()
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}