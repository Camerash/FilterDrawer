package com.camerash.filterdrawer

import android.support.annotation.ColorRes
import android.support.v4.content.ContextCompat
import android.support.v7.widget.DividerItemDecoration
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.View
import android.widget.LinearLayout
import net.cachapa.expandablelayout.ExpandableLayout

abstract class ParentItem {

    abstract fun getParentIcon(): Int

    abstract fun getParentTitle(): String

    abstract fun getLayoutRes(): Int

    abstract fun getRootLinearLayoutId(): Int

    abstract fun getToggleExpandOnClickViewId(): Int

    abstract fun getChildCollection(): List<ChildItem>

    abstract fun getViewHolder(v: View): ViewHolder

    abstract fun getDefaultColorRes(): Int

    abstract fun getSelectedColorRes(): Int

    abstract fun getDefaultIconColorRes(): Int

    abstract fun getSelectedIconColorRes(): Int

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

        internal fun bindView(parent: ParentItem, parentViewPool: RecyclerView.RecycledViewPool, childAdapterList: ArrayList<ChildAdapter>, callback: (ChildItem, Boolean) -> Unit) {
            bindView(parent)
            // Construct filter recycler
            val llm = LinearLayoutManager(itemView.context)
            val did = DividerItemDecoration(itemView.context, llm.orientation)
            val adapter = ChildAdapter(ArrayList(parent.getChildCollection())) { childItem, selected -> callback(childItem, selected) }
            recyclerView.setRecycledViewPool(parentViewPool)
            recyclerView.layoutManager = llm
            recyclerView.addItemDecoration(did)
            recyclerView.adapter = adapter
            recyclerView.isNestedScrollingEnabled = false

            childAdapterList.add(adapterPosition, adapter)
        }

        abstract fun bindView(parent: ParentItem)
        abstract fun onChildSelect(parent: ParentItem, child: ChildItem)
        abstract fun onChildDeselect(parent: ParentItem, child: ChildItem)
        abstract fun onReset(parent: ParentItem)

        fun getColor(@ColorRes color: Int) = ContextCompat.getColor(itemView.context, color)
    }
}