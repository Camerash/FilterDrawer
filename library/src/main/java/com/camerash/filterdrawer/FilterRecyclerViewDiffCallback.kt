package com.camerash.filterdrawer

import android.support.v7.util.DiffUtil

class FilterRecyclerViewDiffCallback<Data>(private val oldDataList: List<Data>, private val newDataList: List<Data>): DiffUtil.Callback() where Data : DiffItemCallback<Data> {

    override fun getOldListSize(): Int = oldDataList.size

    override fun getNewListSize(): Int = newDataList.size

    override fun areItemsTheSame(oldItemPos: Int, newItemPos: Int): Boolean = oldDataList[oldItemPos].isIdentical(newDataList[newItemPos])

    override fun areContentsTheSame(oldItemPos: Int, newItemPos: Int): Boolean = oldDataList[oldItemPos].hasSameRepresentation(newDataList[newItemPos])
}