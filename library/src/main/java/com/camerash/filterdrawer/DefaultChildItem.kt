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
     * Supply default layout resource id
     *
     * @return the default layout resource id
     */
    override fun getLayoutRes(): Int = R.layout.default_filter_child

    /**
     * Supply the ViewHolder implementation
     *
     * @return the constructed ViewHolder
     */
    override fun getViewHolder(v: View): ViewHolder = ViewHolder(v)

    /**
     * Supply the default color resource id used by ChildItem text (Unselected)
     *
     * @return the color resource id
     */
    override fun getDefaultTextColorRes(): Int = R.color.black

    /**
     * Supply the selected color resource id used by ChildItem text
     *
     * @return the color resource id
     */
    override fun getSelectedTextColorRes(): Int = R.color.dark_blue

    /**
     * Supply the default color resource id used by ChildItem background (Unselected)
     *
     * @return the color resource id
     */
    override fun getDefaultBackgroundColorRes(): Int = R.color.transparent

    /**
     * Supply the selected color resource id used by ChildItem background
     *
     * @return the color resource id
     */
    override fun getSelectedBackgroundColorRes(): Int = R.color.gray

    /**
     * Default implementation of ViewHolder of ChildItem
     *
     * @param v view used in constructing ViewHolder
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