package com.silvestr.foosballmatches.ui.games

import android.view.View
import androidx.recyclerview.widget.DiffUtil
import com.silvestr.foosballmatches.R
import com.silvestr.foosballmatches.data.Game
import com.silvestr.foosballmatches.databinding.ListItemGameBinding
import com.silvestr.foosballmatches.ui.base.BaseBindingAdapter
import com.silvestr.foosballmatches.ui.base.BindableViewHolder


class GamesAdapter(
    private val deleteClickListener: ((View, Game) -> Unit),
    private val editClickListener: ((View, Game, Int) -> Unit)
) : BaseBindingAdapter() {

    private val data: MutableList<Game> = mutableListOf()

    fun updateGames(games: List<Game>) {
//        val diffCallback = DiffUtilCallback(data, games)
//        val diffResult = DiffUtil.calculateDiff(diffCallback)
        data.clear()
        data.addAll(games)
        notifyDataSetChanged()
//        diffResult.dispatchUpdatesTo(this)
    }

    override fun getLayoutResId(viewType: Int): Int = R.layout.list_item_game

    override fun onBind(holder: BindableViewHolder<*>, position: Int) {
        val binding = holder.bindings as ListItemGameBinding
        binding.game = data[position]

        binding.actionEdit.setOnClickListener {
            editClickListener.invoke(it, data[position], position)
        }

        binding.actionDelete.setOnClickListener {
            deleteClickListener.invoke(it, data[position])
        }
    }

    override fun getItemCount(): Int = data.size
}

class DiffUtilCallback(private val oldList: List<Any>, private val newList: List<Any>) :
    DiffUtil.Callback() {

    override fun getOldListSize(): Int = oldList.size

    override fun getNewListSize(): Int = newList.size

    override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        val oldItem: Game = oldList[oldItemPosition] as Game
        val newItem: Game = newList[newItemPosition] as Game
        return oldItem.id == newItem.id
    }

    override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        val oldItem = oldList[oldItemPosition]
        val newItem = newList[newItemPosition]

        return oldItem.hashCode() == newItem.hashCode()
    }
}