package com.silvestr.foosballmatches.ui.players

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.silvestr.foosballmatches.R
import com.silvestr.foosballmatches.data.Player
import com.silvestr.foosballmatches.databinding.ListItemPlayerBinding


class PlayersAdapter : RecyclerView.Adapter<PlayerViewHolder>() {

    val data: MutableList<Player> = mutableListOf()

    fun updatePlayers(players: List<Player>) {
        val diffCallback = DiffUtilCallback(data, players)
        val diffResult = DiffUtil.calculateDiff(diffCallback)
        data.clear()
        data.addAll(players)
        diffResult.dispatchUpdatesTo(this)
    }

    override fun getItemCount(): Int = data.size

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
        holder.bind(data[position], position)
    }
}

class PlayerViewHolder(private val playerBinding: ListItemPlayerBinding) :
    RecyclerView.ViewHolder(playerBinding.root) {

    fun bind(player: Player, position: Int) {
        playerBinding.player = player
        playerBinding.position.text = "${position + 1}."
    }
}

class DiffUtilCallback(private val oldList: List<Player>, private val newList: List<Player>) :
    DiffUtil.Callback() {

    override fun getOldListSize(): Int = oldList.size

    override fun getNewListSize(): Int = newList.size

    override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        val oldItem: Player = oldList[oldItemPosition]
        val newItem: Player = newList[newItemPosition]
        return oldItem.id == newItem.id
    }

    override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        val oldItem = oldList[oldItemPosition]
        val newItem = newList[newItemPosition]

        return oldItem.hashCode() == newItem.hashCode()
    }
}
