package com.silvestr.foosballmatches.ui.games

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.silvestr.foosballmatches.R
import com.silvestr.foosballmatches.data.Game
import com.silvestr.foosballmatches.databinding.ListItemGameBinding


class GamesAdapter(
    private val deleteClickListener: ((View, Game) -> Unit),
    private val editClickListener: ((View, Game, Int) -> Unit)
) : ListAdapter<Game, GameViewHolder>(DiffUtilCallback()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): GameViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = DataBindingUtil.inflate<ListItemGameBinding>(
            inflater,
            R.layout.list_item_game,
            parent,
            false
        )
        return GameViewHolder(binding, editClickListener, deleteClickListener)
    }

    override fun onBindViewHolder(holder: GameViewHolder, position: Int) {
        holder.bind(game = getItem(position), position)
    }
}

class GameViewHolder(
    private val gameBinding: ListItemGameBinding,
    private val editClickListener: (View, Game, Int) -> Unit,
    private val deleteClickListener: (View, Game) -> Unit
) : RecyclerView.ViewHolder(gameBinding.root) {

    fun bind(game: Game, position: Int) {
        gameBinding.game = game

        gameBinding.actionEdit.setOnClickListener {
            editClickListener.invoke(it, game, position)
        }

        gameBinding.actionDelete.setOnClickListener {
            deleteClickListener.invoke(it, game)
        }

        gameBinding.executePendingBindings()
    }
}

class DiffUtilCallback() : DiffUtil.ItemCallback<Game>() {

    override fun areItemsTheSame(oldItem: Game, newItem: Game): Boolean {
        return oldItem.id == newItem.id
    }

    override fun areContentsTheSame(oldItem: Game, newItem: Game): Boolean {
        return oldItem == newItem
    }
}