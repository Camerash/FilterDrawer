package com.camerash.filterdrawer

import android.view.View
import android.widget.TextView

abstract class DefaultChildItem: ChildItem() {

    override fun getLayoutRes(): Int = R.layout.default_filter_child

    override fun getViewHolder(v: View): ViewHolder = ViewHolder(v)

    override fun getDefaultColorRes(): Int = R.color.black

    override fun getSelectedColorRes(): Int = R.color.dark_blue

    override fun getDefaultBackgroundColorRes(): Int = R.color.transparent

    override fun getSelectedBackgroundColorRes(): Int = R.color.gray

    inner class ViewHolder(private val v: View): ChildItem.ViewHolder(v) {

        private val childText: TextView = itemView.findViewById(R.id.child_text)

        override fun bindView(child: ChildItem) {
            childText.text = child.getTitle()
            childText.setTextColor(getColor(getDefaultColorRes()))
            v.setBackgroundColor(getColor(getDefaultBackgroundColorRes()))
        }

        override fun onSelect(child: ChildItem) {
            childText.setTextColor(getColor(getSelectedColorRes()))
            v.setBackgroundColor(getColor(getSelectedBackgroundColorRes()))
        }

        override fun onDeselect(child: ChildItem) {
            childText.setTextColor(getColor(getDefaultColorRes()))
            v.setBackgroundColor(getColor(getDefaultBackgroundColorRes()))
        }

        override fun onReset(child: ChildItem) {
            onDeselect(child)
        }
    }
}