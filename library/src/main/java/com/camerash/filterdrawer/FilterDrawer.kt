package com.camerash.filterdrawer

import android.support.annotation.MenuRes
import android.support.v4.widget.DrawerLayout
import android.support.v7.widget.DividerItemDecoration
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.support.v7.widget.Toolbar
import android.view.View
import android.widget.Button
import android.widget.TextView

class FilterDrawer(private val builder: DrawerBuilder, val drawerLayout: DrawerLayout, val filterView: View) {

    val toolbar: Toolbar
    val toolbarTitle: TextView
    val recyclerView: RecyclerView
    val resetBtn: Button
    val applyBtn: Button
    val adapter = ParentAdapter(builder.itemList, builder.childSelectListener)

    init {
        toolbar = filterView.findViewById(R.id.toolbar)
        toolbarTitle = toolbar.findViewById(R.id.toolbar_title)
        recyclerView = filterView.findViewById(R.id.filter_recycler_view)
        resetBtn = filterView.findViewById(R.id.filter_reset_btn)
        applyBtn = filterView.findViewById(R.id.filter_apply_btn)
        constructFilter()
    }

    private fun constructFilter() {
        applyToolbarOptions()
        setupListeners()
        setupRecyclerView()
    }

    private fun applyToolbarOptions() {
        showToolbar(builder.displayToolbar)
        setToolbarTitle(builder.toolbarTitle)
        inflateToolbarMenu(builder.toolbarMenuResId)
    }

    private fun setupListeners() {
        builder.drawerListener?.let { addDrawerListener(it) }
        builder.filterControlClickListener?.let { setFilterControlClickListener(it) }
    }

    private fun setupRecyclerView() {
        val llm = LinearLayoutManager(builder.activity)
        val did = DividerItemDecoration(builder.activity, llm.orientation)
        recyclerView.layoutManager = llm
        recyclerView.addItemDecoration(did)
        recyclerView.adapter = adapter
    }

    fun showToolbar(show: Boolean) {
        toolbar.visibility = if (show) View.VISIBLE else View.GONE
    }

    fun setToolbarTitle(title: String) {
        toolbarTitle.text = title
    }

    fun inflateToolbarMenu(@MenuRes menuRes: Int) {
        if (menuRes == 0) return
        toolbar.inflateMenu(menuRes)
    }

    fun updateItems(itemList: Collection<ParentItem>) {
        adapter.updateItems(ArrayList(itemList))
    }

    fun addToolbarMenuListener(listener: Toolbar.OnMenuItemClickListener) {
        toolbar.setOnMenuItemClickListener(listener)
    }

    fun addDrawerListener(drawerListener: DrawerLayout.DrawerListener) {
        drawerLayout.addDrawerListener(drawerListener)
    }

    fun setFilterControlClickListener(filterControlClickListener: OnFilterControlClickListener) {
        resetBtn.setOnClickListener { filterControlClickListener.onResetClick() }
        applyBtn.setOnClickListener { filterControlClickListener.onApplyClick() }
    }

    fun setChildSelectListener(childSelectListener: OnChildSelectListener) {
        adapter.childSelectListener = childSelectListener
    }

    fun openFilterDrawer() {
        drawerLayout.openDrawer(filterView)
    }

    fun closeFilterDrawer() {
        drawerLayout.closeDrawer(filterView)
    }

    fun resetFilter() {
        adapter.reset()
    }

    interface OnChildSelectListener {
        fun onChildSelect(parentItem: ParentItem, childItem: ChildItem)
    }

    interface OnFilterControlClickListener {
        fun onResetClick()
        fun onApplyClick()
    }
}