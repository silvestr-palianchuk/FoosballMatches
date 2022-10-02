package com.silvestr.foosballmatches.ui.rankings

import com.silvestr.foosballmatches.R
import com.silvestr.foosballmatches.data.Ranking
import com.silvestr.foosballmatches.databinding.ListItemRankingBinding
import com.silvestr.foosballmatches.ui.base.BaseBindingAdapter
import com.silvestr.foosballmatches.ui.base.BindableViewHolder


class RankingsAdapter : BaseBindingAdapter() {

    val data: MutableList<Ranking> = mutableListOf()
    var sortType: SortType = RankingsFragment.DEFAULT_SORT_TYPE

    fun updateRankings(rankings: List<Ranking>, sortType: SortType) {
        this.sortType = sortType
        data.clear()
        data.addAll(rankings)
        notifyDataSetChanged()
    }

    override fun getLayoutResId(viewType: Int): Int = R.layout.list_item_ranking

    override fun onBind(holder: BindableViewHolder<*>, position: Int) {
        val binding = holder.bindings as ListItemRankingBinding
        binding.ranking = data[position]
        binding.position.text = "${position + 1}"

        binding.played.isActivated = sortType == SortType.PLAYED
        binding.won.isActivated = sortType == SortType.WON
    }

    override fun getItemCount(): Int = data.size
}