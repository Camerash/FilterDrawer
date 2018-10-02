package com.camerash.filterdrawer

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.ViewGroup

class ChildAdapter<Parent, Child>(val parent: Parent, val childItemList: ArrayList<Child>, private val callback: (ChildItem, Boolean) -> Unit)
    : RecyclerView.Adapter<RecyclerView.ViewHolder>() where Parent : ParentItem, Child : ChildItem {

    private var selectedItem: Pair<Int, Child>? = null

    override fun onCreateViewHolder(viewGroup: ViewGroup, viewType: Int): RecyclerView.ViewHolder =
        childItemList.first().getViewHolder(LayoutInflater.from(viewGroup.context).inflate(R.layout.default_filter_child, viewGroup, false))

    override fun getItemCount(): Int = childItemList.size

    // Leave empty as we will be using the method that receives payloads
    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {}

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int, payloads: MutableList<Any>) {
        val childItem = childItemList[position]
        val vh = holder as ChildItem.ViewHolder

        if (payloads.isEmpty()) {
            vh.bindView(childItem) { onChildClicked(vh.adapterPosition, childItem) }
        } else {
            when (payloads.first()) {
                is Boolean -> {
                    if (payloads.first() as Boolean) {
                        vh.onSelect(childItem, R.color.colorPrimary)
                    } else {
                        vh.onDeselect(childItem)
                    }
                }
                is Int -> {
                    // Reset
                    vh.onReset(childItem)
                }
            }
        }
    }

    private fun onChildClicked(adapterPosition: Int, childItem: Child) {
        val item = selectedItem
        if (item == null) {
            // Item selected
            notifyItemChanged(adapterPosition, true)
            selectedItem = Pair(adapterPosition, childItem)
            callback(childItem, true)
        } else {
            selectedItem = if (item.first == adapterPosition) {
                // Item deselected
                notifyItemChanged(adapterPosition, false)
                callback(item.second, false)
                null
            } else {
                // New item selected
                // Deselect old item first
                notifyItemChanged(item.first, false)
                // Select new item
                notifyItemChanged(adapterPosition, true)
                callback(childItem, true)
                Pair(adapterPosition, childItem)
            }
        }
    }

    fun reset() {
        selectedItem?.let {
            notifyItemChanged(it.first, RESET_FLAG)
        }
    }

    fun getSelectedChild(): Child? {
        return selectedItem?.second
    }

    companion object {
        const val RESET_FLAG = -1
    }
}