package com.camerash.filterdrawer

import android.support.annotation.ColorRes
import android.support.v4.content.ContextCompat
import android.support.v7.widget.DividerItemDecoration
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.View
import android.widget.LinearLayout
import net.cachapa.expandablelayout.ExpandableLayout

/**
 * Abstract class for base implementation of ParentItem used in FilterDrawer
 *
 * @author Camerash
 * @see FilterDrawer
 */
abstract class ParentItem {

    /**
     * Supply the drawable icon resource for the ParentItem
     *
     * @return The icon drawable resource
     */
    abstract fun getParentIcon(): Int

    /**
     * Supply title for the ParentItem
     *
     * @return Title of ChildItem
     */
    abstract fun getParentTitle(): String

    /**
     * Supply the layout resource for the customization of ParentItem
     *
     * @return The layout resource used by ParentItem
     */
    abstract fun getLayoutRes(): Int

    /**
     * Supply the root linear layout id for the customization of ParentItem
     *
     * @return Id of root linear layout
     */
    abstract fun getRootLinearLayoutId(): Int

    /**
     * Supply the view id who is responsible for receiving
     * the onClick event for toggling expansion of ParentItem.
     *
     * Used for the customization of ParentItem
     * @return Id of the view
     */
    abstract fun getToggleExpandOnClickViewId(): Int

    /**
     * Supply the list of ChildItem under this ParentItem
     *
     * @return List of ChileItem under this ParentItem
     * @see ChildItem
     */
    abstract fun getChildCollection(): List<ChildItem>

    /**
     * Supply the ViewHolder used by the ParentItem
     *
     * @return The view holder
     */
    abstract fun getViewHolder(v: View): ViewHolder

    /**
     * Supply the color resource used in title text
     * when none of the ChildItems under this ParentItem are selected
     *
     * @return The color resource
     */
    abstract fun getDefaultTextColorRes(): Int

    /**
     * Supply the color resource used in title text
     * when one or more ChildItems under this ParentItem are selected
     *
     * @return The color resource
     */
    abstract fun getSelectedTextColorRes(): Int

    /**
     * Supply the color resource of icon used
     * when none of the ChildItems under this ParentItem are selected
     *
     * @return The color resource
     */
    abstract fun getDefaultIconColorRes(): Int

    /**
     * Supply the color resource of icon used
     * when one or more ChildItems under this ParentItem are selected
     *
     * @return The color resource
     */
    abstract fun getSelectedIconColorRes(): Int

    /**
     * Supply the option of whether the ChildItems of this ParentItem
     * can be multi-selected
     *
     * @return Whether multi-selected ChildItems are allowed
     */
    abstract fun allowSelectMultiple(): Boolean

    /**
     * Base implementation of the ViewHolder of ParentItem used in the FilterDrawer's RecyclerView
     *
     * @param v View used in constructing ViewHolder
     * @see ParentAdapter
     */
    abstract inner class ViewHolder(v: View) : RecyclerView.ViewHolder(v) {

        /**
         * The root LinearLayout to add our ExpandableLayout into
         */
        private var root: LinearLayout

        /**
         * The view responsible for receiving onClick event to toggle expansion of the ParentItem
         */
        private var headerView: View = itemView.findViewById(getToggleExpandOnClickViewId())

        /**
         * The ExpandableLayout which host the child RecyclerView
         */
        var expandableView: ExpandableLayout = ExpandableLayout(v.context)

        /**
         * The ChildItem RecyclerView
         */
        var recyclerView: RecyclerView = RecyclerView(v.context)

        init {
            try {
                root = itemView.findViewById(getRootLinearLayoutId())
            } catch (e: ClassCastException) {
                throw IllegalArgumentException("Please use LinearLayout as the root layout")
            }

            expandableView.addView(recyclerView)
            headerView.setOnClickListener { expandableView.toggle() }

            root.addView(expandableView, root.childCount)
            expandableView.collapse()
            expandableView.orientation = ExpandableLayout.VERTICAL
        }

        /**
         * Called when ViewHolder binds with ParentItem
         *
         * @param parent The ParentItem to bind with ViewHolder
         * @param parentViewPool The RecycledViewPool to be set for the ChildItem RecyclerView for better ViewHolder re-usability
         * @param childAdapterList The list of ChildAdapter in this FilterDrawer
         * @param callback Callback to ParentAdapter when the related ChildAdapter's selection changes.
         */
        @Suppress("UNCHECKED_CAST")
        internal fun <Parent, Child> bindView(parent: Parent, parentViewPool: RecyclerView.RecycledViewPool, childAdapterList: ArrayList<ChildAdapter<Parent, Child>>, callback: (Set<Child>, Boolean) -> Unit)
                where Parent : ParentItem, Child : ChildItem {
            bindView(parent)
            // Construct filter recycler
            val llm = LinearLayoutManager(itemView.context)
            val did = DividerItemDecoration(itemView.context, llm.orientation)
            val adapter = ChildAdapter(parent, parent.getChildCollection() as List<Child>) { childItem, selected -> callback(childItem, selected) }
            recyclerView.setRecycledViewPool(parentViewPool)
            recyclerView.layoutManager = llm
            recyclerView.addItemDecoration(did)
            recyclerView.adapter = adapter
            recyclerView.isNestedScrollingEnabled = false

            childAdapterList.add(adapterPosition, adapter)
        }

        /**
         * Called when ViewHolder binds with ParentItem
         *
         * @param parent The ParentItem to bind with ViewHolder
         */
        abstract fun bindView(parent: ParentItem)

        /**
         * Called when one of the ChildItem is selected
         *
         * @param parent The ParentItem that bound with this ViewHolder
         * @param childSet The currently selected set of ChildItem
         */
        abstract fun onChildSelect(parent: ParentItem, childSet: Set<ChildItem>)


        /**
         * Called when one of the ChildItem is deselected
         *
         * @param parent The ParentItem that bound with this ViewHolder
         * @param childSet The currently selected set of ChildItem
         */
        abstract fun onChildDeselect(parent: ParentItem, childSet: Set<ChildItem>)


        /**
         * Called when the FilterDrawer resets
         *
         * @param parent The ParentItem that bound with this ViewHolder
         */
        abstract fun onReset(parent: ParentItem)

        /**
         * Helper method for Supply color from color resource
         *
         * @param color The color resource
         * @return The color
         */
        fun getColor(@ColorRes color: Int) = ContextCompat.getColor(itemView.context, color)
    }
}