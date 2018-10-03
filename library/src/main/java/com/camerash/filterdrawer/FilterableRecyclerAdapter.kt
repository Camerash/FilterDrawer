package com.camerash.filterdrawer

import android.support.v7.util.DiffUtil
import android.support.v7.widget.RecyclerView

abstract class FilterableRecyclerAdapter<Data, Parent, Child> :
        RecyclerView.Adapter<RecyclerView.ViewHolder>(), FilterDrawer.OnChildSelectListener<Parent, Child>, RecyclerAdapterFilter<Data, Parent, Child> where Parent : ParentItem, Child : ChildItem {

    abstract val dataList: List<Data>
    private var filteredDataList = listOf<Data>()
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

    final override fun onChildSelect(parent: Parent, child: Child) = filter()

    final override fun onChildDeselect(parent: Parent, child: Child) = filter()

    final override fun onReset() = filter()

    override fun onDetachedFromRecyclerView(recyclerView: RecyclerView) {
        super.onDetachedFromRecyclerView(recyclerView)
        unbindFilterDrawer()
    }

    private fun filter() {
        val fd = filterDrawer ?: return
        filterWithFilterMap(fd.getSelectedChildrens())
    }

    private fun filterWithFilterMap(filterMap: Map<Parent, Child>) {
        filteredDataList = dataList.filter { filter(it, filterMap) }
        DiffUtil.calculateDiff(FilterRecyclerViewDiffCallback(dataList, filteredDataList)).dispatchUpdatesTo(this)
    }

}