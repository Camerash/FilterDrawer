package com.camerash.library

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.ViewGroup

class ParentAdapter(var parentItemList: List<ParentItem>) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    private val parentViewPool = RecyclerView.RecycledViewPool()
    private val childAdapterList = arrayListOf<ChildAdapter>()

    override fun onCreateViewHolder(viewGroup: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return parentItemList.first().getViewHolder(LayoutInflater.from(viewGroup.context).inflate(R.layout.default_filter_parent, viewGroup, false))
    }

    override fun getItemCount(): Int = parentItemList.size

    // Leave empty as we will be using the method that receives payloads
    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {}

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int, payloads: MutableList<Any>) {
        val parentItem = parentItemList[position]
        val vh = holder as ParentItem.ViewHolder

        if(payloads.isEmpty()) {
            // Init ViewHolder for the first time
            vh.bindView(parentItem, parentViewPool, childAdapterList)
        } else {
            when(payloads.first()) {
                is Pair<*, *> -> {
                    val pair = payloads.first() as Pair<*, *>
                    val childItem = pair.first as ChildItem
                    val selected = pair.second as Boolean

                    if(selected) {
                        // One of the children selected
                        vh.onChildSelected(parentItem, childItem, R.color.colorPrimary)
                    } else {
                        // One of the children deselected
                        vh.onChildDeselcted(parentItem, childItem)
                    }
                }
                else -> // Reset triggered
                    vh.onFilterReset(parentItem)
            }
        }
    }
}