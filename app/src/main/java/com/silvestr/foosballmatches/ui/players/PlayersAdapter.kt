package com.silvestr.foosballmatches.ui.players

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.silvestr.foosballmatches.R
import com.silvestr.foosballmatches.data.Player
import com.silvestr.foosballmatches.databinding.ListItemPlayerBinding


class PlayersAdapter : ListAdapter<Player, PlayerViewHolder>(DiffUtilCallback()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PlayerViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = DataBindingUtil.inflate<ListItemPlayerBinding>(
            inflater,
            R.layout.list_item_player,
            parent,
            false
        )
        return PlayerViewHolder(binding)
    }

    override fun onBindViewHolder(holder: PlayerViewHolder, position: Int) {
        holder.bind(getItem(position), position)
    }
}

class PlayerViewHolder(private val playerBinding: ListItemPlayerBinding) :
    RecyclerView.ViewHolder(playerBinding.root) {

    fun bind(player: Player, position: Int) {
        playerBinding.player = player
        playerBinding.position.text = "${position + 1}."
    }
}

class DiffUtilCallback() : DiffUtil.ItemCallback<Player>() {

    override fun areItemsTheSame(oldItem: Player, newItem: Player): Boolean {
        return oldItem.id == newItem.id
    }

    override fun areContentsTheSame(oldItem: Player, newItem: Player): Boolean {
        return oldItem == newItem
    }
}
