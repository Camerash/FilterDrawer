package com.camerash.filterdrawer

import android.view.View
import android.widget.TextView

/**
 * Default implementation of ChildItem
 *
 * @author Camerash
 * @see ChildItem
 * @see ChildAdapter
 */
abstract class DefaultChildItem: ChildItem() {

    /**
     * Supply default layout resource
     *
     * @return The default layout resource
     */
    override fun getLayoutRes(): Int = R.layout.default_filter_child

    /**
     * Supply the ViewHolder implementation
     *
     * @return The constructed ViewHolder
     */
    override fun getViewHolder(v: View): ViewHolder = ViewHolder(v)

    /**
     * Supply the default color resource used by ChildItem text (Unselected)
     *
     * @return The color resource
     */
    override fun getDefaultTextColorRes(): Int = R.color.black

    /**
     * Supply the selected color resource used by ChildItem text
     *
     * @return The color resource
     */
    override fun getSelectedTextColorRes(): Int = R.color.dark_blue

    /**
     * Supply the default color resource used by ChildItem background (Unselected)
     *
     * @return The color resource id
     */
    override fun getDefaultBackgroundColorRes(): Int = R.color.transparent

    /**
     * Supply the selected color resource used by ChildItem background
     *
     * @return The color resource id
     */
    override fun getSelectedBackgroundColorRes(): Int = R.color.gray

    /**
     * Default implementation of ViewHolder of ChildItem
     *
     * @param v View used in constructing ViewHolder
     * @see ChildAdapter
     * @see ChildItem.ViewHolder
     */
    inner class ViewHolder(private val v: View): ChildItem.ViewHolder(v) {

        private val childText: TextView = itemView.findViewById(R.id.child_text)

        override fun bindView(child: ChildItem) {
            childText.text = child.getTitle()
            childText.setTextColor(getColor(getDefaultTextColorRes()))
            v.setBackgroundColor(getColor(getDefaultBackgroundColorRes()))
        }

        override fun onSelect(child: ChildItem) {
            childText.setTextColor(getColor(getSelectedTextColorRes()))
            v.setBackgroundColor(getColor(getSelectedBackgroundColorRes()))
        }

        override fun onDeselect(child: ChildItem) {
            childText.setTextColor(getColor(getDefaultTextColorRes()))
            v.setBackgroundColor(getColor(getDefaultBackgroundColorRes()))
        }

        override fun onReset(child: ChildItem) {
            onDeselect(child)
        }
    }
}