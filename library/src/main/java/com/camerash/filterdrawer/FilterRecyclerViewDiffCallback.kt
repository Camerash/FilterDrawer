package com.camerash.filterdrawer

import android.support.v7.util.DiffUtil

/**
 * DiffUtil Callback used for calculating changes in FilterableRecyclerAdapter
 *
 * @author Camerash
 * @param Data Your custom data type that implements DiffItemCallback
 * @param oldDataList The old list of data item
 * @param newDataList The new list of data item
 * @see DiffUtil.Callback
 * @see DiffItemCallback
 */
class FilterRecyclerViewDiffCallback<Data>(private val oldDataList: List<Data>, private val newDataList: List<Data>): DiffUtil.Callback() where Data : DiffItemCallback<Data> {

    /**
     * Return the size of the old list of data item
     *
     * @return Size of the old list of data item
     */
    override fun getOldListSize(): Int = oldDataList.size

    /**
     * Return the size of the new list of data item
     *
     * @return Size of the new list of data item
     */
    override fun getNewListSize(): Int = newDataList.size

    /**
     * Return whether two data items are identical at a data level
     *
     * Uses the data item's DiffItemCallback's implementation of the method isIdentical
     * @param oldItemPos The position of the old data item
     * @param newItemPos The position of the new data item
     * @return Whether two data items are identical at a data level
     * @see DiffItemCallback.isIdentical
     */
    override fun areItemsTheSame(oldItemPos: Int, newItemPos: Int): Boolean = oldDataList[oldItemPos].isIdentical(newDataList[newItemPos])

    /**
     * Return whether two data items looks the same when shown to users
     *
     * Uses the data item's DiffItemCallback's implementation of the method hasSameRepresentation
     * @param oldItemPos The position of the old data item
     * @param newItemPos The position of the new data item
     * @return Whether two data items looks the same when shown to users
     * @see DiffItemCallback.hasSameRepresentation
     */
    override fun areContentsTheSame(oldItemPos: Int, newItemPos: Int): Boolean = oldDataList[oldItemPos].hasSameRepresentation(newDataList[newItemPos])
}