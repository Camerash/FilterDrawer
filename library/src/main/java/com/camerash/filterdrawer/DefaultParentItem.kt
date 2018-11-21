package com.camerash.filterdrawer

import android.content.res.ColorStateList
import android.support.v4.widget.ImageViewCompat
import android.support.v7.widget.AppCompatImageView
import android.support.v7.widget.CardView
import android.view.View
import android.widget.ImageView
import android.widget.TextView

abstract class DefaultParentItem : ParentItem() {


    override fun getLayoutRes(): Int = R.layout.default_filter_parent

    override fun getRootLinearLayoutId(): Int = R.id.root_layout

    override fun getToggleExpandOnClickViewId(): Int = R.id.parent

    override fun getViewHolder(v: View): ViewHolder = ViewHolder(v)

    override fun getDefaultTextColorRes(): Int = R.color.black

    override fun getSelectedTextColorRes(): Int = R.color.dark_blue

    override fun getDefaultIconColorRes(): Int = getDefaultTextColorRes()

    override fun getSelectedIconColorRes(): Int = getSelectedTextColorRes()

    inner class ViewHolder(v: View) : ParentItem.ViewHolder(v) {

        private val headerIcon: AppCompatImageView = itemView.findViewById(R.id.parent_icon)
        private val headerText: TextView = itemView.findViewById(R.id.parent_text)
        private val headerLayout: CardView = itemView.findViewById(R.id.parent)
        private val indicatorIcon: ImageView = itemView.findViewById(R.id.parent_indicator)

        override fun bindView(parent: ParentItem) {
            headerIcon.visibility = if (parent.getParentIcon() != 0) {
                headerIcon.setImageResource(parent.getParentIcon())
                View.VISIBLE
            } else {
                View.GONE
            }

            headerText.text = parent.getParentTitle()

            headerLayout.setOnClickListener { expandableView.toggle() }
            expandableView.setOnExpansionUpdateListener(ExpandableLayoutIndicatorListener(indicatorIcon, 90))

            ImageViewCompat.setImageTintList(headerIcon, ColorStateList.valueOf(getColor(getDefaultIconColorRes())))
            headerText.setTextColor(getColor(getDefaultTextColorRes()))
        }

        override fun onChildSelect(parent: ParentItem, childSet: Set<ChildItem>) {
            ImageViewCompat.setImageTintList(headerIcon, ColorStateList.valueOf(getColor(getSelectedIconColorRes())))
            headerText.setTextColor(getColor(getSelectedTextColorRes()))
            headerText.text = if(childSet.size == parent.getChildCollection().size) "All" else childSet.joinToString{ it.getTitle() }
            if (!allowSelectMultiple()) {
                expandableView.collapse()
            }
        }

        override fun onChildDeselect(parent: ParentItem, childSet: Set<ChildItem>) {
            if(childSet.isEmpty()) {
                ImageViewCompat.setImageTintList(headerIcon, ColorStateList.valueOf(getColor(getDefaultIconColorRes())))
                headerText.setTextColor(getColor(getDefaultTextColorRes()))
                headerText.text = parent.getParentTitle()
            } else {
                headerText.text = childSet.joinToString{ it.getTitle() }
            }
        }

        override fun onReset(parent: ParentItem) {
            ImageViewCompat.setImageTintList(headerIcon, ColorStateList.valueOf(getColor(getDefaultIconColorRes())))
            headerText.setTextColor(getColor(getDefaultTextColorRes()))
            headerText.text = parent.getParentTitle()
            expandableView.collapse()
        }
    }
}