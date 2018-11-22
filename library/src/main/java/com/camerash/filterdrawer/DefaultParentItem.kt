package com.camerash.filterdrawer

import android.content.res.ColorStateList
import android.support.v4.widget.ImageViewCompat
import android.support.v7.widget.AppCompatImageView
import android.support.v7.widget.CardView
import android.view.View
import android.widget.ImageView
import android.widget.TextView

/**
 * Default implementation of ChildItem
 *
 * @author Camerash
 * @see ChildItem
 * @see ChildAdapter
 */
abstract class DefaultParentItem : ParentItem() {

    /**
     * Supply default layout resource id
     *
     * @return The default layout resource id
     */
    override fun getLayoutRes(): Int = R.layout.default_filter_parent

    /**
     * Supply default root LinearLayout resource id
     *
     * @return The default root LinearLayout resource id
     */
    override fun getRootLinearLayoutId(): Int = R.id.root_layout

    /**
     * Supply the resource id of the view who is responsible for receiving
     * the onClick event for toggling expansion of ParentItem.
     *
     * @return Id of the view
     */
    override fun getToggleExpandOnClickViewId(): Int = R.id.parent

    /**
     * Supply the default implementation of ViewHolder used by the ParentItem
     *
     * @return The view holder
     */
    override fun getViewHolder(v: View): ViewHolder = ViewHolder(v)

    /**
     * Supply the resource id of the color used in title text
     * when none of the ChildItems under this ParentItem are selected
     *
     * @return The resource id of the color
     */
    override fun getDefaultTextColorRes(): Int = R.color.black

    /**
     * Supply the resource id of the color used in title text
     * when one or more ChildItems under this ParentItem are selected
     *
     * @return The resource id of the color
     */
    override fun getSelectedTextColorRes(): Int = R.color.dark_blue

    /**
     * Supply the resource id of the color of icon used
     * when none of the ChildItems under this ParentItem are selected
     *
     * @return The resource id of the color
     */
    override fun getDefaultIconColorRes(): Int = getDefaultTextColorRes()

    /**
     * Supply the resource id of the color of icon used
     * when one or more ChildItems under this ParentItem are selected
     *
     * @return The resource id of the color
     */
    override fun getSelectedIconColorRes(): Int = getSelectedTextColorRes()

    /**
     * Default implementation of the ViewHolder of ParentItem used in the FilterDrawer's RecyclerView
     *
     * @param v View used in constructing ViewHolder
     * @see ParentAdapter
     * @see ParentItem.ViewHolder
     */
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