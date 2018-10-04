package com.camerash.filterdrawer

import android.support.v7.util.DiffUtil

class FilterRecyclerViewDiffCallback<Data>(private val oldDataList: List<Data>, private val newDataList: List<Data>): DiffUtil.Callback() {

    override fun getOldListSize(): Int = oldDataList.size

    override fun getNewListSize(): Int = newDataList.size

    override fun areItemsTheSame(oldItemPos: Int, newItemPos: Int): Boolean = false

    override fun areContentsTheSame(oldItemPos: Int, newItemPos: Int): Boolean = false
}