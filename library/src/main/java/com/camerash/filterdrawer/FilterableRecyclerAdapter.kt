package com.camerash.filterdrawer

import android.support.v7.util.DiffUtil
import android.support.v7.widget.RecyclerView

/**
 * Base implementation of RecyclerAdapter that is filterable by FilterDrawer
 *
 * @author Camerash
 * @param Data Your custom data type that implements DiffItemCallback
 * @param Parent Your custom parent type that extends ParentItem
 * @param Child Your custom child type that extends ChildItem
 * @see DiffItemCallback
 * @see RecyclerAdapterFilter
 * @see FilterRecyclerViewDiffCallback
 */
abstract class FilterableRecyclerAdapter<Data, Parent, Child> :
        RecyclerView.Adapter<RecyclerView.ViewHolder>(), FilterDrawer.OnChildSelectListener<Parent, Child>, RecyclerAdapterFilter<Data, Parent, Child>
        where Data : DiffItemCallback<Data>, Parent : ParentItem, Child : ChildItem {

    /**
     * The list of data with type List<Data>
     *
     * Must be defined in custom implementation
     */
    abstract var dataList: List<Data>

    /**
     * A copy of original data list for filtering
     */
    private val refDataList by lazy { dataList }

    /**
     * Reference to the bound FilterDrawer
     */
    private var filterDrawer: FilterDrawer<Parent, Child>? = null

    /**
     * Binds a FilterDrawer with this RecyclerAdapter
     *
     * @param filterDrawer The FilterDrawer that binds with this RecyclerAdapter
     */
    fun bindFilterDrawer(filterDrawer: FilterDrawer<Parent, Child>) {
        this.filterDrawer = filterDrawer
        filterDrawer.addChildSelectListener(this)
    }

    /**
     * Unbinds the bound FilterDrawer
     */
    fun unbindFilterDrawer() {
        val fd = filterDrawer ?: return
        fd.removeChildSelectListener(this)
        filterDrawer = null
    }

    /**
     * Set RecyclerView to have fixed size for filtering
     *
     * @param recyclerView The RecyclerView to be attached with
     */
    override fun onAttachedToRecyclerView(recyclerView: RecyclerView) {
        super.onAttachedToRecyclerView(recyclerView)
        recyclerView.setHasFixedSize(true)
    }

    /**
     * Unbinds FilterDrawer when detached from RecyclerView
     *
     * @param recyclerView The RecyclerView to be detached with
     */
    override fun onDetachedFromRecyclerView(recyclerView: RecyclerView) {
        super.onDetachedFromRecyclerView(recyclerView)
        unbindFilterDrawer()
    }

    /**
     * Call internal filter method
     */
    private fun filter() {
        val fd = filterDrawer ?: return
        filterWithFilterMap(fd.getSelectedChildrens())
    }

    /**
     * Filter the data list with FilterDrawer's selected filters
     *
     * @param filterMap Selected filter map from bound FilterDrawer
     */
    private fun filterWithFilterMap(filterMap: Map<Parent, Set<Child>>) {
        val newDataList = if(filterMap.isEmpty()) refDataList else refDataList.filter { filterData(it, filterMap) }
        val diff = DiffUtil.calculateDiff(FilterRecyclerViewDiffCallback(dataList, newDataList))
        dataList = newDataList
        diff.dispatchUpdatesTo(this)
    }

    /**
     * Filter data with custom implementation
     *
     * @param data The data item to be filtered
     * @param filterMap Selected filter map from bound FilterDrawer
     */
    private fun filterData(data: Data, filterMap: Map<Parent, Set<Child>>): Boolean {
        // Immediately filter out the data if it doesn't match one of the categories
        filterMap.forEach { (parent, childSet) ->
            childSet.firstOrNull { filter(data, parent, it) } ?: return false
        }
        return true
    }

    /**
     * Method for OnChildSelectListener interface
     *
     * Call filter method when child changes
     */
    final override fun onChildSelect(parent: Parent, child: Set<Child>) = filter()

    /**
     * Method for OnChildSelectListener interface
     *
     * Call filter method when child changes
     */
    final override fun onChildDeselect(parent: Parent, child: Set<Child>) = filter()

    /**
     * Method for OnChildSelectListener interface
     *
     * Call filter method when child changes
     */
    final override fun onReset() = filter()

    /**
     * Return size of filtered list
     */
    final override fun getItemCount(): Int = dataList.size

}