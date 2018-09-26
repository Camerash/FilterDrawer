package com.camerash.library

import android.support.v7.widget.RecyclerView
import android.view.Gravity
import android.view.View
import android.widget.LinearLayout
import net.cachapa.expandablelayout.ExpandableLayout

abstract class CustomHeaderItem<HeaderItem, ChildItem> {
    abstract fun getLayoutRes(): Int

    abstract fun getRootLinearLayoutId(): Int

    abstract fun getViewHolder(v: View): ViewHolder

    abstract inner class ViewHolder(v: View) : RecyclerView.ViewHolder(v) {

        private var root: LinearLayout
        private var expandableView: ExpandableLayout = ExpandableLayout(v.context)
        private var recyclerView: RecyclerView = RecyclerView(v.context)

        init{
            try {
                root = v.findViewById(getRootLinearLayoutId())
            } catch (e: ClassCastException) {
                throw IllegalArgumentException("Please use LinearLayout as the root layout")
            }

            expandableView.addView(recyclerView)

            // TODO: remove params as linearlayout adds child at the bottom with the index specified like this
            val params = LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT)
            params.gravity = Gravity.BOTTOM
            root.addView(expandableView, root.childCount, params)
        }

        abstract fun bindView(header: HeaderItem)
        abstract fun onChildSelected(header: HeaderItem, child: ChildItem)
        abstract fun onChildDeselcted(header: HeaderItem, child: ChildItem)
        abstract fun onChildReselcted(header: HeaderItem, child: ChildItem)
    }
}