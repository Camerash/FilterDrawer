package com.camerash.library

import android.support.annotation.ColorRes
import android.support.v7.widget.DividerItemDecoration
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.Gravity
import android.view.View
import android.widget.LinearLayout
import net.cachapa.expandablelayout.ExpandableLayout

abstract class ParentItem {
    abstract fun getLayoutRes(): Int

    abstract fun getRootLinearLayoutId(): Int

    abstract fun getToggleExpandOnClickViewId(): Int

    abstract fun getChildCollection(): Collection<ChildItem>

    abstract fun getViewHolder(v: View): ViewHolder

    abstract inner class ViewHolder(v: View) : RecyclerView.ViewHolder(v) {

        private var root: LinearLayout
        protected var expandableView: ExpandableLayout = ExpandableLayout(v.context)
        protected var recyclerView: RecyclerView = RecyclerView(v.context)
        protected var headerView: View = itemView.findViewById(getToggleExpandOnClickViewId())

        init{
            try {
                root = itemView.findViewById(getRootLinearLayoutId())
            } catch (e: ClassCastException) {
                throw IllegalArgumentException("Please use LinearLayout as the root layout")
            }

            expandableView.addView(recyclerView)
            headerView.setOnClickListener { expandableView.toggle() }

            // TODO: remove params as linearlayout adds child at the bottom with the index specified like this
            val params = LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT)
            params.gravity = Gravity.BOTTOM
            root.addView(expandableView, root.childCount, params)
        }

        private fun bindView(parent: ParentItem, parentViewPool: RecyclerView.RecycledViewPool, childAdapterList: ArrayList<ChildAdapter>) {
            bindView(parent)
            // Construct filter recycler
            val llm = LinearLayoutManager(itemView.context)
            val did = DividerItemDecoration(itemView.context, llm.orientation)
            val adapter = ChildAdapter(getChildCollection())
            recyclerView.setRecycledViewPool(parentViewPool)
            recyclerView.layoutManager = llm
            recyclerView.addItemDecoration(did)
            recyclerView.adapter = adapter
            recyclerView.isNestedScrollingEnabled = false

            childAdapterList.add(adapterPosition, adapter)
        }

        abstract fun bindView(parent: ParentItem)
        abstract fun onChildSelected(parent: ParentItem, child: ChildItem, @ColorRes colorRes: Int)
        abstract fun onChildDeselcted(parent: ParentItem, child: ChildItem)
        abstract fun onChildReselcted(parent: ParentItem, child: ChildItem)
        abstract fun onFilterReset(parent: ParentItem)
    }
}