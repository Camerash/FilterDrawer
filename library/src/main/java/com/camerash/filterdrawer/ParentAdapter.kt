package com.camerash.filterdrawer

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.ViewGroup

/**
 * Adapter for ParentItem used in the FilterDrawer
 *
 * @author Camerash
 * @param parentItemList list of ParentItem
 * @param childSelectListenerList list of OnChildSelectListener to be called when changes happens
 * @see ParentItem
 * @see ChildAdapter
 */
class ParentAdapter<Parent, Child>(private var parentItemList: List<Parent>, var childSelectListenerList: ArrayList<FilterDrawer.OnChildSelectListener<Parent, Child>>)
    : RecyclerView.Adapter<RecyclerView.ViewHolder>() where Parent : ParentItem, Child : ChildItem {

    /**
     * The RecycledViewPool shared by all child RecyclerViews
     */
    private val parentViewPool = RecyclerView.RecycledViewPool()

    /**
     * The list of ChildAdapter in FilterDrawer
     */
    private val childAdapterList = arrayListOf<ChildAdapter<Parent, Child>>()

    /**
     * Inflate view and create view holder
     */
    override fun onCreateViewHolder(viewGroup: ViewGroup, viewType: Int): RecyclerView.ViewHolder =
            parentItemList.first().getViewHolder(LayoutInflater.from(viewGroup.context).inflate(R.layout.default_filter_parent, viewGroup, false))

    /**
     * @return Number of child filters
     */
    override fun getItemCount(): Int = parentItemList.size

    /**
     * Called when view holder request binding
     * Leave empty as we will be using the method that receives payloads
     */
    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {}

    /**
     * Called when view holder request binding
     */
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
                    val childItem = pair.first as Set<Child>
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

    /**
     * Called when status of any ChildItem changes
     */
    private fun handleChildSelect(adapterPosition: Int, childItem: Set<Child>, selected: Boolean) {
        notifyItemChanged(adapterPosition, Pair(childItem, selected))
    }

    /**
     * Get the map of ParentItem to set of currently selected ChildItem
     */
    fun getSelectedChildren(): Map<Parent, Set<Child>> {
        val map = mutableMapOf<Parent, Set<Child>>()

        childAdapterList.forEach{
            val selectedChildSet = it.getSelectedChildSet()
            if(selectedChildSet.isNotEmpty()) map[it.parent] = selectedChildSet
        }

        return map
    }

    /**
     * Update list of ParentItem
     */
    fun updateItems(parentItemList: List<Parent>) {
        this.parentItemList = parentItemList
        notifyDataSetChanged()
    }

    /**
     * Reset FilterDrawer
     */
    fun reset() {
        childAdapterList.forEach { it.reset() }
        notifyItemRangeChanged(0, childAdapterList.size, RESET_FLAG)
        childSelectListenerList.forEach { it.onReset() }
    }

    companion object {
        const val RESET_FLAG = -1
    }
}