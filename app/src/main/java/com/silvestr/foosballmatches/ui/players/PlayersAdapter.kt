package com.silvestr.foosballmatches.ui.players

import com.silvestr.foosballmatches.R
import com.silvestr.foosballmatches.data.Player
import com.silvestr.foosballmatches.databinding.ListItemPlayerBinding
import com.silvestr.foosballmatches.ui.base.BaseBindingAdapter
import com.silvestr.foosballmatches.ui.base.BindableViewHolder


class PlayersAdapter : BaseBindingAdapter() {

    val data: MutableSet<Player> = mutableSetOf()

    fun updatePlayers(players: Set<Player>) {
        data.clear()
        data.addAll(players)
        notifyDataSetChanged()
    }

    override fun getLayoutResId(viewType: Int): Int = R.layout.list_item_player

    override fun onBind(holder: BindableViewHolder<*>, position: Int) {
        val binding = holder.bindings as ListItemPlayerBinding
        binding.player = data.elementAt(position)
        binding.position.text = "${position + 1}."
    }

    override fun getItemCount(): Int = data.size

}
