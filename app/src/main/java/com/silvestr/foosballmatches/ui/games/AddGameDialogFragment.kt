package com.silvestr.foosballmatches.ui.games

import android.app.DatePickerDialog
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.FragmentManager
import androidx.lifecycle.ViewModelProvider
import com.silvestr.foosballmatches.FoosballApplication
import com.silvestr.foosballmatches.R
import com.silvestr.foosballmatches.data.DataProvider.gameIdsSet
import com.silvestr.foosballmatches.data.DataProvider.playerIdsSet
import com.silvestr.foosballmatches.data.Game
import com.silvestr.foosballmatches.data.Player
import com.silvestr.foosballmatches.databinding.FragmentAddGameBinding
import com.silvestr.foosballmatches.di.ViewModelFactory
import com.silvestr.foosballmatches.utils.DateHelper
import java.util.Calendar
import javax.inject.Inject


class AddGameDialogFragment : DialogFragment() {

    companion object {
        private val TAG = AddGameDialogFragment::class.java.simpleName

        fun showDialog(fragmentManager: FragmentManager) {
            val fragment = AddGameDialogFragment()
            fragment.show(fragmentManager, TAG)
        }
    }

    @Inject
    lateinit var viewModelFactory: ViewModelFactory
    private var gamesViewModel: GamesViewModel? = null

    private var _binding: FragmentAddGameBinding? = null
    private val binding get() = _binding!!

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        (activity?.application as FoosballApplication).appComponent.inject(this)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentAddGameBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        gamesViewModel =
            ViewModelProvider(requireActivity(), viewModelFactory)[GamesViewModel::class.java]

        binding.date.text = DateHelper.getFormattedDate(Calendar.getInstance().timeInMillis)

        val datePickerDialogListener =
            DatePickerDialog.OnDateSetListener { _, year, monthOfYear, dayOfMonth ->
                val calendar = Calendar.getInstance()
                calendar.set(Calendar.YEAR, year)
                calendar.set(Calendar.MONTH, monthOfYear)
                calendar.set(Calendar.DAY_OF_MONTH, dayOfMonth)
                calendar.set(Calendar.MILLISECOND, 0)
                calendar.set(Calendar.SECOND, 0)
                calendar.set(Calendar.MINUTE, 0)
                calendar.set(Calendar.HOUR, 0)

                binding.date.text = DateHelper.getFormattedDate(calendar.timeInMillis)
            }

        binding.date.setOnClickListener {
            DateHelper.showDatePickerDialog(requireActivity(), datePickerDialogListener)
        }

        binding.buttonAdd.setOnClickListener {
            if (isValidData()) {
                val game = createGame()
                gamesViewModel?.addGame(game)
                gameIdsSet.remove(game.id)

                dismiss()
            }
        }

        binding.buttonCancel.setOnClickListener {
            dismiss()
        }
    }

    private fun isValidData(): Boolean {
        val defaultScoreValue = "0"
        return when {
            binding.editTextPlayer1FirstName.text.isNullOrBlank() ||
                    binding.editTextPlayer1LastName.text.isNullOrBlank() ||
                    binding.editTextPlayer2FirstName.text.isNullOrBlank() ||
                    binding.editTextPlayer2LastName.text.isNullOrBlank() -> {
                binding.errorMessage.apply {
                    visibility = View.VISIBLE
                    text = getString(R.string.error_player_name_cant_be_empty)
                }
                false
            }
            binding.editTextScore1.text.isNullOrEmpty() ||
                    binding.editTextScore2.text.isNullOrEmpty() -> {
                binding.editTextScore1.setText(defaultScoreValue, TextView.BufferType.EDITABLE)
                binding.editTextScore2.setText(defaultScoreValue, TextView.BufferType.EDITABLE)
                true
            }
            else -> true
        }
    }

    private fun createGame(): Game {
        val player1 = Player(
            id = playerIdsSet.first(),
            firstName = binding.editTextPlayer1FirstName.text.toString(),
            lastName = binding.editTextPlayer1LastName.text.toString()
        )
        playerIdsSet.remove(player1.id)

        val player2 = Player(
            id = playerIdsSet.first(),
            firstName = binding.editTextPlayer2FirstName.text.toString(),
            lastName = binding.editTextPlayer2LastName.text.toString()
        )
        playerIdsSet.remove(player2.id)

        return Game(
            id = gameIdsSet.first(),
            date = DateHelper.convertStringDateToLong(binding.date.text.toString()),
            player1 = player1,
            player2 = player2,
            score1 = binding.editTextScore1.text.toString().toInt(),
            score2 = binding.editTextScore2.text.toString().toInt()
        )
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}