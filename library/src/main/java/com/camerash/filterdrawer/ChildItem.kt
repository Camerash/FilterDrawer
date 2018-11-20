package com.camerash.filterdrawer

import android.support.annotation.ColorRes
import android.support.v4.content.ContextCompat
import android.support.v7.widget.RecyclerView
import android.view.View

/**
 * Abstract class for base implementation of ChildItem used in FilterDrawer
 *
 * @author Camerash
 * @see FilterDrawer
 */
abstract class ChildItem {

    /**
     * Supply title for the ChildItem
     *
     * @return title of ChildItem
     */
    abstract fun getTitle(): String

    /**
     * Supply the layout resource id for the ChildItem
     *
     * @return id of layout resource used by ChildItem
     */
    abstract fun getLayoutRes(): Int

    /**
     * Supply the view holder used by the ChildItem
     *
     * @return the view holder
     */
    abstract fun getViewHolder(v: View): ViewHolder

    /**
     * Supply the resource id of the color used in title text when the ChildItem is not selected
     *
     * @return the resource id of the color
     */
    abstract fun getDefaultColorRes(): Int

    /**
     * Supply the resource id of the color used in title text when the ChildItem is selected
     *
     * @return the resource id of the color
     */
    abstract fun getSelectedColorRes(): Int

    /**
     * Supply the resource id of the color used when the ChildItem is not selected
     *
     * @return the resource id of the color
     */
    abstract fun getDefaultBackgroundColorRes(): Int

    /**
     * Supply the resource id of the color used when the ChildItem is selected
     *
     * @return the resource id of the color
     */
    abstract fun getSelectedBackgroundColorRes(): Int

    /**
     * Base implementation of the ViewHolder of ChildItem used in the FilterDrawer's ParentItems' child RecyclerView
     *
     * @param v view used in constructing ViewHolder
     * @see ChildAdapter
     */
    abstract inner class ViewHolder(private val v: View) : RecyclerView.ViewHolder(v) {

        /**
         * Called when ViewHolder binds with ChildItem
         *
         * @param child the ChildItem to bind with ViewHolder
         * @param onClickListener the onClickListener to be set on root view
         */
        internal fun bindView(child: ChildItem, onClickListener: (View) -> Unit) {
            bindView(child)
            v.setOnClickListener(onClickListener)
        }

        /**
         * Called when ViewHolder binds with ChildItem
         *
         * @param child the ChildItem to bind with ViewHolder
         */
        abstract fun bindView(child: ChildItem)

        /**
         * Called when the ChildItem got selected
         *
         * @param child the ChildItem that bound with this ViewHolder
         */
        abstract fun onSelect(child: ChildItem)


        /**
         * Called when the ChildItem got deselected
         *
         * @param child the ChildItem that bound with this ViewHolder
         */
        abstract fun onDeselect(child: ChildItem)


        /**
         * Called when the FilterDrawer resets
         *
         * @param child the ChildItem that bound with this ViewHolder
         */
        abstract fun onReset(child: ChildItem)

        /**
         * Helper method for Supply color from color resource id
         *
         * @param color the color resource id
         * @return the color
         */
        fun getColor(@ColorRes color: Int) = ContextCompat.getColor(itemView.context, color)
    }
}