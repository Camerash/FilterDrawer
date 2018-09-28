package com.camerash.filterdrawer

import android.graphics.Color
import android.support.v4.content.ContextCompat
import android.view.View
import android.widget.TextView

abstract class DefaultChildItem: ChildItem() {

    override fun getLayoutRes(): Int = R.layout.default_filter_child

    override fun getViewHolder(v: View): ViewHolder = ViewHolder(v)

    inner class ViewHolder(v: View): ChildItem.ViewHolder(v) {

        private val childText: TextView = itemView.findViewById(R.id.child_text)

        override fun bindView(child: ChildItem) {
            childText.text = child.getTitle()
            childText.setTextColor(Color.BLACK)
        }

        override fun onSelect(child: ChildItem, colorRes: Int) {
            childText.setTextColor(ContextCompat.getColor(itemView.context, R.color.colorPrimary))
            rootView.setBackgroundColor(ContextCompat.getColor(itemView.context, R.color.selected_gray))
        }

        override fun onDeselect(child: ChildItem) {
            childText.setTextColor(Color.BLACK)
            rootView.setBackgroundColor(Color.WHITE)
        }

        override fun onReset(child: ChildItem) {
            onDeselect(child)
        }
    }
}