package com.camerash.filterdrawer

import android.content.res.ColorStateList
import android.graphics.Color
import android.support.v4.content.ContextCompat
import android.support.v4.widget.ImageViewCompat
import android.support.v7.widget.AppCompatImageView
import android.support.v7.widget.CardView
import android.view.View
import android.widget.ImageView
import android.widget.TextView

abstract class DefaultParentItem: ParentItem() {

    // Default implementation
    override fun getLayoutRes(): Int = R.layout.default_filter_parent

    override fun getRootLinearLayoutId(): Int = R.id.root_layout

    override fun getViewHolder(v: View): ViewHolder = ViewHolder(v)

    inner class ViewHolder(v: View) : ParentItem.ViewHolder(v) {

        private val headerIcon: AppCompatImageView = itemView.findViewById(R.id.parent_icon)
        private val headerText: TextView = itemView.findViewById(R.id.parent_text)
        private val headerLayout: CardView = itemView.findViewById(R.id.parent)
        private val indicatorIcon: ImageView = itemView.findViewById(R.id.parent_indicator)

        override fun bindView(parent: ParentItem) {
            headerIcon.visibility = if(parent.getParentIcon() != 0) {
                headerIcon.setImageResource(parent.getParentIcon())
                View.VISIBLE
            } else {
                View.GONE
            }

            headerText.text = parent.getParentTitle()

            headerLayout.setOnClickListener { expandableView.toggle() }
            expandableView.setOnExpansionUpdateListener(ExpandableLayoutIndicatorListener(indicatorIcon, 90))
        }

        override fun onChildSelect(parent: ParentItem, child: ChildItem, colorRes: Int) {
            val color = ContextCompat.getColor(itemView.context, colorRes)
            ImageViewCompat.setImageTintList(headerIcon, ColorStateList.valueOf(color))
            headerText.setTextColor(color)
            headerText.text = child.getTitle()
            expandableView.collapse()
        }

        override fun onChildDeselect(parent: ParentItem, child: ChildItem) {
            ImageViewCompat.setImageTintList(headerIcon, ColorStateList.valueOf(Color.BLACK))
            headerText.setTextColor(Color.BLACK)
            headerText.text = parent.getParentTitle()
        }

        override fun onReset(parent: ParentItem) {
            ImageViewCompat.setImageTintList(headerIcon, ColorStateList.valueOf(Color.BLACK))
            headerText.setTextColor(Color.BLACK)
            headerText.text = parent.getParentTitle()
            expandableView.collapse()
        }
    }
}