package com.silvestr.foosballmatches.ui.base

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.annotation.LayoutRes
import androidx.databinding.ViewDataBinding
import androidx.recyclerview.widget.RecyclerView

abstract class BaseBindingAdapter : RecyclerView.Adapter<BindableViewHolder<*>>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BindableViewHolder<*> {
        return BindableViewHolder.create<ViewDataBinding>(
            LayoutInflater.from(parent.context),
            getLayoutResId(viewType),
            parent
        )
    }

    @LayoutRes
    protected abstract fun getLayoutResId(viewType: Int): Int

    override fun onBindViewHolder(holder: BindableViewHolder<*>, position: Int) {
        onBind(holder, position)
        holder.bindings.executePendingBindings()
    }

    abstract fun onBind(holder: BindableViewHolder<*>, position: Int)

}
