package com.camerash.filterdrawer

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.ViewGroup

class ParentAdapter(private var parentItemList: ArrayList<ParentItem>, var childSelectListener: FilterDrawer.OnChildSelectListener?) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    private val parentViewPool = RecyclerView.RecycledViewPool()
    private val childAdapterList = arrayListOf<ChildAdapter>()

    override fun onCreateViewHolder(viewGroup: ViewGroup, viewType: Int): RecyclerView.ViewHolder =
        parentItemList.first().getViewHolder(LayoutInflater.from(viewGroup.context).inflate(R.layout.default_filter_parent, viewGroup, false))

    override fun getItemCount(): Int = parentItemList.size

    // Leave empty as we will be using the method that receives payloads
    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {}

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
                    val childItem = pair.first as ChildItem
                    val selected = pair.second as Boolean

                    if (selected) {
                        // One of the children selected
                        vh.onChildSelect(parentItem, childItem)
                        childSelectListener?.onChildSelect(parentItem, childItem)
                    } else {
                        // One of the children deselected
                        vh.onChildDeselect(parentItem, childItem)
                    }
                }
                else -> // Reset triggered
                    vh.onReset(parentItem)
            }
        }
    }

    private fun handleChildSelect(adapterPosition: Int, childItem: ChildItem, selected: Boolean) {
        notifyItemChanged(adapterPosition, Pair(childItem, selected))
    }

    fun updateItems(parentItemList: ArrayList<ParentItem>) {
        this.parentItemList = parentItemList
        notifyDataSetChanged()
    }

    fun reset() {
        childAdapterList.forEach{ it.reset() }
        notifyItemRangeChanged(0, childAdapterList.size, true)
    }
}