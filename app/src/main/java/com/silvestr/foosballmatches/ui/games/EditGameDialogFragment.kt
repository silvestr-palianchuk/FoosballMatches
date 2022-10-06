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
import com.silvestr.foosballmatches.data.Game
import com.silvestr.foosballmatches.databinding.FragmentEditGameBinding
import com.silvestr.foosballmatches.di.ViewModelFactory
import com.silvestr.foosballmatches.utils.DateHelper
import com.silvestr.foosballmatches.utils.FragmentArg
import java.util.*
import javax.inject.Inject


class EditGameDialogFragment : DialogFragment() {

    companion object {
        private val TAG = EditGameDialogFragment::class.java.simpleName

        fun showDialog(fragmentManager: FragmentManager, game: Game, position: Int) {
            val fragment = EditGameDialogFragment()
            fragment.game = game
            fragment.position = position
            fragment.show(fragmentManager, TAG)
        }
    }

    @Inject
    lateinit var viewModelFactory: ViewModelFactory
    var gamesViewModel: GamesViewModel? = null

    private var _binding: FragmentEditGameBinding? = null
    private val binding get() = _binding!!

    private var game by FragmentArg<Game>()
    private var position by FragmentArg<Int>()

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

        binding.buttonUpdate.setOnClickListener {
            if (isValidData()) {
                val updatedPlayer1 = game?.player1?.copy(
                    firstName = binding.editTextPlayer1FirstName.text.toString(),
                    lastName = binding.editTextPlayer1LastName.text.toString()
                )
                val updatedPlayer2 = game?.player2?.copy(
                    firstName = binding.editTextPlayer2FirstName.text.toString(),
                    lastName = binding.editTextPlayer2LastName.text.toString()
                )
                val updatedGame = game?.copy(
                    date = DateHelper.convertStringDateToLong(binding.date.text.toString()),
                    player1 = updatedPlayer1,
                    player2 = updatedPlayer2,
                    score1 = binding.editTextScore1.text.toString().toInt(),
                    score2 = binding.editTextScore2.text.toString().toInt()
                )

                if (updatedGame != null && (updatedGame != game))
                    gamesViewModel?.editGame(updatedGame, position!!)

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
                binding.editTextScore1.setText(
                    (game?.score1 ?: defaultScoreValue).toString(),
                    TextView.BufferType.EDITABLE
                )
                binding.editTextScore2.setText(
                    (game?.score2 ?: defaultScoreValue).toString(),
                    TextView.BufferType.EDITABLE
                )
                true
            }
            else -> true
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}