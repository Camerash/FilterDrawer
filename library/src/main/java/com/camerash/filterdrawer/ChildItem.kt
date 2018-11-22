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
     * @return Title of ChildItem
     */
    abstract fun getTitle(): String

    /**
     * Supply the layout resource for the customization of ChildItem
     *
     * @return The layout resource used by ChildItem
     */
    abstract fun getLayoutRes(): Int

    /**
     * Supply the view holder used by the ChildItem
     *
     * @return The view holder
     */
    abstract fun getViewHolder(v: View): ViewHolder

    /**
     * Supply the color resource used in title text when the ChildItem is not selected
     *
     * @return The color resource
     */
    abstract fun getDefaultTextColorRes(): Int

    /**
     * Supply the color resource used in title text when the ChildItem is selected
     *
     * @return The color resource
     */
    abstract fun getSelectedTextColorRes(): Int

    /**
     * Supply the color resource used in background when the ChildItem is not selected
     *
     * @return The color resource
     */
    abstract fun getDefaultBackgroundColorRes(): Int

    /**
     * Supply the color resource used in background when the ChildItem is selected
     *
     * @return The color resource
     */
    abstract fun getSelectedBackgroundColorRes(): Int

    /**
     * Base implementation of the ViewHolder of ChildItem used in the FilterDrawer's ParentItems' child RecyclerView
     *
     * @param v View used in constructing ViewHolder
     * @see ChildAdapter
     */
    abstract inner class ViewHolder(private val v: View) : RecyclerView.ViewHolder(v) {

        /**
         * Called when ViewHolder binds with ChildItem
         *
         * @param child The ChildItem to bind with ViewHolder
         * @param onClickListener The onClickListener to be set on root view
         */
        internal fun bindView(child: ChildItem, onClickListener: (View) -> Unit) {
            bindView(child)
            v.setOnClickListener(onClickListener)
        }

        /**
         * Called when ViewHolder binds with ChildItem
         *
         * @param child The ChildItem to bind with ViewHolder
         */
        abstract fun bindView(child: ChildItem)

        /**
         * Called when the ChildItem got selected
         *
         * @param child The ChildItem that bound with this ViewHolder
         */
        abstract fun onSelect(child: ChildItem)


        /**
         * Called when the ChildItem got deselected
         *
         * @param child The ChildItem that bound with this ViewHolder
         */
        abstract fun onDeselect(child: ChildItem)


        /**
         * Called when the FilterDrawer resets
         *
         * @param child The ChildItem that bound with this ViewHolder
         */
        abstract fun onReset(child: ChildItem)

        /**
         * Helper method for Supply color from color resource
         *
         * @param color The color resource
         * @return The color
         */
        fun getColor(@ColorRes color: Int) = ContextCompat.getColor(itemView.context, color)
    }
}