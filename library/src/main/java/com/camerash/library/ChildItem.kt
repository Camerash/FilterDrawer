package com.camerash.library

import android.support.v7.widget.RecyclerView
import android.view.View

abstract class ChildItem {

    abstract fun getTitle(): String

    abstract fun getLayoutRes(): Int

    abstract fun getViewHolder(v: View): ViewHolder

    abstract inner class ViewHolder(v: View) : RecyclerView.ViewHolder(v) {
        abstract fun bindView(child: ChildItem)
        abstract fun onSelect(child: ChildItem)
        abstract fun onDeselect(child: ChildItem)
        abstract fun onReset(child: ChildItem)
    }
}