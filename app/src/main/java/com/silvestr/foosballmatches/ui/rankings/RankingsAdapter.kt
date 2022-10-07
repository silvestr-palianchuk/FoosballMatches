package com.silvestr.foosballmatches.ui.rankings

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.silvestr.foosballmatches.R
import com.silvestr.foosballmatches.data.Ranking
import com.silvestr.foosballmatches.databinding.ListItemRankingBinding


class RankingsAdapter : RecyclerView.Adapter<RankingsViewHolder>() {

    val data: MutableList<Ranking> = mutableListOf()
    var sortType: SortType = RankingsFragment.DEFAULT_SORT_TYPE

    fun updateRankings(rankings: List<Ranking>, sortType: SortType) {
        this.sortType = sortType
        data.clear()
        data.addAll(rankings)
        notifyDataSetChanged()
    }

    override fun getItemCount(): Int = data.size

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RankingsViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = DataBindingUtil.inflate<ListItemRankingBinding>(
            inflater,
            R.layout.list_item_ranking,
            parent,
            false
        )
        return RankingsViewHolder(binding)
    }

    override fun onBindViewHolder(holder: RankingsViewHolder, position: Int) {
        holder.bind(data[position], position, sortType)
    }
}

class RankingsViewHolder(private val rankingsBinding: ListItemRankingBinding) :
    RecyclerView.ViewHolder(rankingsBinding.root) {

    fun bind(ranking: Ranking, position: Int, sortType: SortType) {
        rankingsBinding.ranking = ranking
        rankingsBinding.position.text = "${position + 1}"
        rankingsBinding.played.isActivated = sortType == SortType.PLAYED
        rankingsBinding.won.isActivated = sortType == SortType.WON
    }
}