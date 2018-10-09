package com.camerash.filterdrawer

import android.support.v7.util.DiffUtil
import android.support.v7.widget.RecyclerView

abstract class FilterableRecyclerAdapter<Data, Parent, Child> :
        RecyclerView.Adapter<RecyclerView.ViewHolder>(), FilterDrawer.OnChildSelectListener<Parent, Child>, RecyclerAdapterFilter<Data, Parent, Child>
        where Data : DiffItemCallback<Data>, Parent : ParentItem, Child : ChildItem {

    abstract var dataList: List<Data>
    private val refDataList by lazy { dataList } // Reference data list for comparing filter
    private var filterDrawer: FilterDrawer<Parent, Child>? = null

    fun bindFilterDrawer(filterDrawer: FilterDrawer<Parent, Child>) {
        this.filterDrawer = filterDrawer
        filterDrawer.addChildSelectListener(this)
    }

    fun unbindFilterDrawer() {
        val fd = filterDrawer ?: return
        fd.removeChildSelectListener(this)
        filterDrawer = null
    }

    final override fun onChildSelect(parent: Parent, child: Set<Child>) = filter()

    final override fun onChildDeselect(parent: Parent, child: Set<Child>) = filter()

    final override fun onReset() = filter()

    override fun onDetachedFromRecyclerView(recyclerView: RecyclerView) {
        super.onDetachedFromRecyclerView(recyclerView)
        unbindFilterDrawer()
    }

    private fun filter() {
        val fd = filterDrawer ?: return
        filterWithFilterMap(fd.getSelectedChildrens())
    }

    private fun filterWithFilterMap(filterMap: Map<Parent, Set<Child>>) {
        val newDataList = if(filterMap.isEmpty()) refDataList else refDataList.filter { filterData(it, filterMap) }
        val diff = DiffUtil.calculateDiff(FilterRecyclerViewDiffCallback(dataList, newDataList))
        dataList = newDataList
        diff.dispatchUpdatesTo(this)
    }

    private fun filterData(data: Data, filterMap: Map<Parent, Set<Child>>): Boolean {
        // Immediately filter out the data if it doesn't match one of the categories
        filterMap.forEach { (parent, childSet) ->
            childSet.firstOrNull { filter(data, parent, it) } ?: return false
        }
        return true
    }

}