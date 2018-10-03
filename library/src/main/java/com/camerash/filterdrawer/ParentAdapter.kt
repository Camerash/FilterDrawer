package com.camerash.filterdrawer

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.ViewGroup

class ParentAdapter<Parent, Child>(private var parentItemList: List<Parent>, var childSelectListenerList: ArrayList<FilterDrawer.OnChildSelectListener<Parent, Child>>)
    : RecyclerView.Adapter<RecyclerView.ViewHolder>() where Parent : ParentItem, Child : ChildItem {

    private val parentViewPool = RecyclerView.RecycledViewPool()
    private val childAdapterList = arrayListOf<ChildAdapter<Parent, Child>>()

    override fun onCreateViewHolder(viewGroup: ViewGroup, viewType: Int): RecyclerView.ViewHolder =
            parentItemList.first().getViewHolder(LayoutInflater.from(viewGroup.context).inflate(R.layout.default_filter_parent, viewGroup, false))

    override fun getItemCount(): Int = parentItemList.size

    // Leave empty as we will be using the method that receives payloads
    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {}

    @Suppress("UNCHECKED_CAST")
    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int, payloads: MutableList<Any>) {
        val parentItem = parentItemList[position]
        val vh = holder as ParentItem.ViewHolder

        if (payloads.isEmpty()) {
            // Init ViewHolder for the first time
            vh.bindView(parentItem, parentViewPool, childAdapterList) { child, selected -> handleChildSelect(vh.adapterPosition, child, selected) }
        } else {
            when (payloads.first()) {
                is Pair<*, *> -> {
                    val pair = payloads.first() as Pair<*, *>
                    val childItem = pair.first as Child
                    val selected = pair.second as Boolean

                    if (selected) {
                        // One of the children selected
                        vh.onChildSelect(parentItem, childItem)
                        childSelectListenerList.forEach { it.onChildSelect(parentItem, childItem) }
                    } else {
                        // One of the children deselected
                        vh.onChildDeselect(parentItem, childItem)
                        childSelectListenerList.forEach { it.onChildDeselect(parentItem, childItem) }
                    }
                }
                else -> {
                    // Reset triggered
                    vh.onReset(parentItem)
                }
            }
        }
    }

    private fun handleChildSelect(adapterPosition: Int, childItem: ChildItem, selected: Boolean) {
        notifyItemChanged(adapterPosition, Pair(childItem, selected))
    }

    fun getSelectedChildren(): Map<Parent, Set<Child>> {
        val map = mutableMapOf<Parent, Set<Child>>()

        childAdapterList.forEach{
            map[it.parent] = it.getSelectedChildSet()
        }

        return map
    }

    fun updateItems(parentItemList: List<Parent>) {
        this.parentItemList = parentItemList
        notifyDataSetChanged()
    }

    fun reset() {
        childAdapterList.forEach { it.reset() }
        notifyItemRangeChanged(0, childAdapterList.size, true)
        childSelectListenerList.forEach { it.onReset() }
    }
}