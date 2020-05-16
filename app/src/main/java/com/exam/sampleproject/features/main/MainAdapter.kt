package com.exam.sampleproject.features.main

import com.exam.domain.poko.Driver
import com.exam.domain.poko.Trip
import com.exam.sampleproject.R
import com.exam.sampleproject.base.BaseRecyclerMultiViewAdapter
import com.exam.sampleproject.base.MultiViewHolder

class MainAdapter(
    private val items: MutableList<Driver>
) : BaseRecyclerMultiViewAdapter<Driver>() {


    override fun getItemForPosition(position: Int): Driver {
        return items[position]
    }

    override fun getLayoutIdForPosition(position: Int): Int {
        return when (items[position]) {
            is Trip -> {
                R.layout.item_driver_trip
            }
            else -> {
                R.layout.item_driver
            }
        }
    }

    override fun getItemCount(): Int {
        return items.size
    }

    fun addItem(item: Driver) {
        this.items.add(item)
        notifyDataSetChanged()
    }

    fun updateList(items: List<Driver>) {
        this.items.clear()
        this.items.addAll(if (!items.isNullOrEmpty()) items else emptyList())
        notifyDataSetChanged()
    }

    override fun onBindViewHolder(holder: MultiViewHolder, position: Int) {
        super.onBindViewHolder(holder, position)
        val item = getItemForPosition(position)
        when (item) {
            is Trip -> {
                holder.bind(item)
            }
            else -> {
                holder.bind(item)
            }
        }

    }
}