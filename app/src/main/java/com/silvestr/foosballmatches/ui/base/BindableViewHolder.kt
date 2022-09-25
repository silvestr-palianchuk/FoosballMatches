package com.silvestr.foosballmatches.ui.base

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.recyclerview.widget.RecyclerView


class BindableViewHolder<T : ViewDataBinding>(val bindings: T) : RecyclerView.ViewHolder(bindings.root) {

    companion object {

        fun <B : ViewDataBinding> create(inflater: LayoutInflater,
                                         layoutResId: Int, parent: ViewGroup): BindableViewHolder<B> {
            val binding = DataBindingUtil.inflate<B>(inflater, layoutResId, parent, false)
            return BindableViewHolder(binding)
        }
    }

    val context: Context
        get() = itemView.context


}