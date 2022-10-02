package com.silvestr.foosballmatches.ui.players

import com.silvestr.foosballmatches.R
import com.silvestr.foosballmatches.data.Game
import com.silvestr.foosballmatches.data.Player
import com.silvestr.foosballmatches.databinding.ListItemPlayerBinding
import com.silvestr.foosballmatches.ui.base.BaseBindingAdapter
import com.silvestr.foosballmatches.ui.base.BindableViewHolder


class PlayersAdapter : BaseBindingAdapter() {

    val data: MutableList<Player> = mutableListOf()

    fun updatePlayers(players: List<Player>) {
        data.clear()
        data.addAll(players)
        notifyDataSetChanged()
    }

    override fun getLayoutResId(viewType: Int): Int = R.layout.list_item_player

    override fun onBind(holder: BindableViewHolder<*>, position: Int) {
        val binding = holder.bindings as ListItemPlayerBinding
        binding.player = data[position]
    }

    override fun getItemCount(): Int = data.size

}
