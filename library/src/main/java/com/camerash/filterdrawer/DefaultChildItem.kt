package com.camerash.filterdrawer

import android.support.v4.content.ContextCompat
import android.view.View
import android.widget.TextView

abstract class DefaultChildItem: ChildItem() {

    override fun getLayoutRes(): Int = R.layout.default_filter_child

    override fun getRootViewId(): Int = R.id.root_view

    override fun getViewHolder(v: View): ViewHolder = ViewHolder(v)

    override fun getDefaultColorRes(): Int = android.R.color.black

    override fun getSelectedColorRes(): Int = android.R.color.holo_blue_dark

    override fun getDefaultBackgroundColorRes(): Int = android.R.color.transparent

    override fun getSelectedBackgroundColorRes(): Int = R.color.selected_gray

    inner class ViewHolder(v: View): ChildItem.ViewHolder(v) {

        private val childText: TextView = itemView.findViewById(R.id.child_text)

        override fun bindView(child: ChildItem) {
            childText.text = child.getTitle()
            childText.setTextColor(getDefaultColorRes())
            rootView.setBackgroundColor(getDefaultBackgroundColorRes())
        }

        override fun onSelect(child: ChildItem, colorRes: Int) {
            childText.setTextColor(ContextCompat.getColor(itemView.context, getSelectedColorRes()))
            rootView.setBackgroundColor(ContextCompat.getColor(itemView.context, getSelectedBackgroundColorRes()))
        }

        override fun onDeselect(child: ChildItem) {
            childText.setTextColor(getDefaultColorRes())
            rootView.setBackgroundColor(getDefaultBackgroundColorRes())
        }

        override fun onReset(child: ChildItem) {
            onDeselect(child)
        }
    }
}