package com.silvestr.foosballmatches.ui.games

import android.view.View
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
        data.clear()
        data.addAll(games)

        /*
        * Can be replaced with DiffUtils or need to modify update logic in order to use notifyItemChanged
        */
        notifyDataSetChanged()
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