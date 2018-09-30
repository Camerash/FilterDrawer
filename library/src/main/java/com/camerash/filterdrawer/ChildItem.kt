package com.camerash.filterdrawer

import android.support.annotation.ColorRes
import android.support.v7.widget.RecyclerView
import android.view.View
import android.view.ViewGroup

abstract class ChildItem {

    abstract fun getTitle(): String

    abstract fun getLayoutRes(): Int

    abstract fun getRootViewId(): Int

    abstract fun getViewHolder(v: View): ViewHolder

    abstract fun getDefaultColorRes(): Int

    abstract fun getSelectedColorRes(): Int

    abstract fun getDefaultBackgroundColorRes(): Int

    abstract fun getSelectedBackgroundColorRes(): Int

    abstract inner class ViewHolder(v: View) : RecyclerView.ViewHolder(v) {

        val rootView: ViewGroup = itemView.findViewById(getRootViewId())

        internal fun bindView(child: ChildItem, onClickListener: (View) -> Unit) {
            bindView(child)
            rootView.setOnClickListener(onClickListener)
        }

        abstract fun bindView(child: ChildItem)
        abstract fun onSelect(child: ChildItem, @ColorRes colorRes: Int)
        abstract fun onDeselect(child: ChildItem)
        abstract fun onReset(child: ChildItem)
    }
}