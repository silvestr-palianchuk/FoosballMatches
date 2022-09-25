package com.silvestr.foosballmatches.ui.games

import com.silvestr.foosballmatches.R
import com.silvestr.foosballmatches.data.Game
import com.silvestr.foosballmatches.databinding.ListItemGameBinding
import com.silvestr.foosballmatches.ui.base.BaseBindingAdapter
import com.silvestr.foosballmatches.ui.base.BindableViewHolder


class GamesAdapter : BaseBindingAdapter() {

    private val data: MutableList<Game> = mutableListOf()

    fun setGames(games: List<Game>) {
        data.clear()
        data.addAll(games)
        notifyDataSetChanged()
    }

    override fun getLayoutResId(viewType: Int): Int = R.layout.list_item_game

    override fun onBind(holder: BindableViewHolder<*>, position: Int) {
        val binding = holder.bindings as ListItemGameBinding
        binding.game = data[position]
    }

    override fun getItemCount(): Int = data.size
}