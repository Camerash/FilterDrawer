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
     * Supply the drawable icon resource id for the ParentItem
     *
     * @return resource id for icon drawable
     */
    abstract fun getParentIcon(): Int

    /**
     * Supply title for the ParentItem
     *
     * @return title of ChildItem
     */
    abstract fun getParentTitle(): String

    /**
     * Supply the layout resource id for the ParentItem
     *
     * @return id of layout resource used by ParentItem
     */
    abstract fun getLayoutRes(): Int

    abstract fun getRootLinearLayoutId(): Int

    abstract fun getToggleExpandOnClickViewId(): Int

    abstract fun getChildCollection(): List<ChildItem>

    abstract fun getViewHolder(v: View): ViewHolder

    abstract fun getDefaultColorRes(): Int

    abstract fun getSelectedColorRes(): Int

    abstract fun getDefaultIconColorRes(): Int

    abstract fun getSelectedIconColorRes(): Int

    abstract fun allowSelectMultiple(): Boolean

    abstract inner class ViewHolder(v: View) : RecyclerView.ViewHolder(v) {

        private var root: LinearLayout
        private var headerView: View = itemView.findViewById(getToggleExpandOnClickViewId())
        var expandableView: ExpandableLayout = ExpandableLayout(v.context)
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

        abstract fun bindView(parent: ParentItem)
        abstract fun onChildSelect(parent: ParentItem, childSet: Set<ChildItem>)
        abstract fun onChildDeselect(parent: ParentItem, childSet: Set<ChildItem>)
        abstract fun onReset(parent: ParentItem)

        fun getColor(@ColorRes color: Int) = ContextCompat.getColor(itemView.context, color)
    }
}