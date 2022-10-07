package com.silvestr.foosballmatches.ui.games

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.silvestr.foosballmatches.R
import com.silvestr.foosballmatches.data.Game
import com.silvestr.foosballmatches.databinding.ListItemGameBinding


class GamesAdapter(
    private val deleteClickListener: ((View, Game) -> Unit),
    private val editClickListener: ((View, Game, Int) -> Unit)
) : RecyclerView.Adapter<GameViewHolder>() {

    private val data: MutableList<Game> = mutableListOf()

    fun updateGames(games: List<Game>) {
        val diffCallback = DiffUtilCallback(data, games)
        val diffResult = DiffUtil.calculateDiff(diffCallback)
        data.clear()
        data.addAll(games)
        diffResult.dispatchUpdatesTo(this)
    }

    override fun getItemCount(): Int = data.size

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
        holder.bind(game = data[position], position)
    }
}

class GameViewHolder(
    private val gameBinding: ListItemGameBinding,
    private val editClickListener: (View, Game, Int) -> Unit,
    private val deleteClickListener: (View, Game) -> Unit
) : RecyclerView.ViewHolder(gameBinding.root) {

    fun bind(game: Game, position: Int) {
        gameBinding.game = game
        gameBinding.executePendingBindings()

        gameBinding.actionEdit.setOnClickListener {
            editClickListener.invoke(it, game, position)
        }

        gameBinding.actionDelete.setOnClickListener {
            deleteClickListener.invoke(it, game)
        }
    }
}

class DiffUtilCallback(private val oldList: List<Game>, private val newList: List<Game>) :
    DiffUtil.Callback() {

    override fun getOldListSize(): Int = oldList.size

    override fun getNewListSize(): Int = newList.size

    override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        val oldItem: Game = oldList[oldItemPosition]
        val newItem: Game = newList[newItemPosition]
        return oldItem.id == newItem.id
    }

    override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        val oldItem = oldList[oldItemPosition]
        val newItem = newList[newItemPosition]

        return oldItem.hashCode() == newItem.hashCode()
    }
}