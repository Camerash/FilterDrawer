package com.camerash.library

import android.support.v7.widget.RecyclerView
import android.widget.Button
import android.widget.TextView
import android.widget.Toolbar

class FilterDrawer(builder: DrawerBuilder) {

    public val filterView = builder.filterView
    public val drawerLayout = builder.drawerLayout
    public val toolbar: Toolbar
    public val toolbarTitle: TextView
    public val recyclerView: RecyclerView
    public val resetBtn: Button
    public val applyBtn: Button

    init {
        if(filterView == null || drawerLayout == null) {
            throw IllegalArgumentException("Filter view or drawer layout not setup")
        }
        toolbar = filterView.findViewById(R.id.toolbar)
        toolbarTitle = toolbar.findViewById(R.id.toolbar_title)
        recyclerView = filterView.findViewById(R.id.filter_recycler_view)
        resetBtn = filterView.findViewById(R.id.filter_reset_btn)
        applyBtn = filterView.findViewById(R.id.filter_apply_btn)
    }

    interface OnChildSelectListener {
        fun onChildSelect()
    }
}