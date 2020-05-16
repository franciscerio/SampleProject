package com.exam.sampleproject.base

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.LayoutRes
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.recyclerview.widget.RecyclerView
import com.exam.sampleproject.BR
import com.exam.sampleproject.R
import io.reactivex.subjects.PublishSubject

/**
 * @param I item class type.
 */
abstract class BaseRecyclerMultiViewAdapter<I : Any> : RecyclerView.Adapter<MultiViewHolder>() {

    val itemClickListener = PublishSubject.create<I>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MultiViewHolder {
        return MultiViewHolder(
                DataBindingUtil.inflate(
                        LayoutInflater.from(parent.context),
                        viewType,
                        parent,
                        false
                )
        )
    }

    override fun getItemViewType(position: Int): Int {
        return getLayoutIdForPosition(position)
    }

    override fun onBindViewHolder(holder: MultiViewHolder, position: Int) {
        holder.bind(getItemForPosition(position))
        val clickableItem = holder
                .binding
                .root.findViewById<View>(R.id.itemClickable)
        clickableItem?.setOnClickListener {
            itemClickListener.onNext(getItemForPosition(position))
        }
    }

    abstract fun getItemForPosition(position: Int): I

    @LayoutRes
    abstract fun getLayoutIdForPosition(position: Int): Int
}

open class MultiViewHolder(val binding: ViewDataBinding) : RecyclerView.ViewHolder(binding.root) {
    fun bind(item: Any) {
        binding.setVariable(BR.item, item)
        binding.executePendingBindings()
    }
}