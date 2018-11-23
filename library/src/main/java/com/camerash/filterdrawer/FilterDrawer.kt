package com.camerash.filterdrawer

import android.support.annotation.ColorRes
import android.support.annotation.MenuRes
import android.support.v4.content.ContextCompat
import android.support.v4.widget.DrawerLayout
import android.support.v7.widget.DividerItemDecoration
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.support.v7.widget.Toolbar
import android.view.View
import android.widget.Button
import android.widget.TextView

/**
 * The actual FilterDrawer itself
 *
 * @author Camerash
 * @param builder A reference of DrawerBuilder used to build this FilterDrawer
 * @param drawerLayout The DrawerLayout that the FilterDrawer relies on
 * @param filterView The View of FilterDrawer
 * @param Parent Type that extends ParentItem
 * @param Child Type that extends ChildItem
 * @see DrawerBuilder
 */
class FilterDrawer<Parent, Child>(private val builder: DrawerBuilder<Parent, Child>, private val drawerLayout: DrawerLayout, private val filterView: View) where Parent: ParentItem, Child: ChildItem {

    val toolbar: Toolbar = filterView.findViewById(R.id.toolbar)
    val toolbarTitle: TextView
    val recyclerView: RecyclerView
    val resetBtn: Button
    val applyBtn: Button
    val adapter = ParentAdapter(builder.itemList, builder.childSelectListenerList)

    init {
        toolbarTitle = toolbar.findViewById(R.id.toolbar_title)
        recyclerView = filterView.findViewById(R.id.filter_recycler_view)
        resetBtn = filterView.findViewById(R.id.filter_reset_btn)
        applyBtn = filterView.findViewById(R.id.filter_apply_btn)
        constructFilter()
    }

    /**
     * Internal method used for constructing the FilterDrawer
     */
    private fun constructFilter() {
        applyToolbarOptions()
        applyButtonsOptions()
        setupListeners()
        setupRecyclerView()
    }

    /**
     * Internal method used to apply toolbar options from the builder
     */
    private fun applyToolbarOptions() {
        showToolbar(builder.displayToolbar)
        setToolbarTitle(builder.toolbarTitle)
        inflateToolbarMenu(builder.toolbarMenuResId)
    }

    /**
     * Internal method used to apply button options from the builder
     */
    private fun applyButtonsOptions() {
        resetBtn.text = builder.resetText
        resetBtn.setTextColor(builder.resetColor)
        applyBtn.text = builder.applyText
        applyBtn.setTextColor(builder.applyColor)
    }

    /**
     * Internal method used to setup listeners from the builder
     */
    private fun setupListeners() {
        builder.drawerListener?.let { addDrawerListener(it) }
        builder.filterControlClickListener?.let { setFilterControlClickListener(it) }
    }

    /**
     * Internal method used to setup RecyclerView of the FilterDrawer
     */
    private fun setupRecyclerView() {
        val llm = LinearLayoutManager(builder.activity)
        val did = DividerItemDecoration(builder.activity, llm.orientation)
        recyclerView.layoutManager = llm
        recyclerView.addItemDecoration(did)
        recyclerView.adapter = adapter
    }

    /**
     * Set visibility of toolbar
     *
     * @param show Whether to show the toolbar
     */
    fun showToolbar(show: Boolean) {
        toolbar.visibility = if (show) View.VISIBLE else View.GONE
    }

    /**
     * Set the title of the toolbar
     *
     * @param title Well, the title
     */
    fun setToolbarTitle(title: String) {
        toolbarTitle.text = title
    }

    /**
     * Inflate a menu inside the toolbar
     *
     * @param menuRes Menu resource to inflate
     */
    fun inflateToolbarMenu(@MenuRes menuRes: Int) {
        if (menuRes == 0) return
        toolbar.inflateMenu(menuRes)
    }

    /**
     * Update the list of filter
     *
     * @param itemList The new list of ParentItem
     */
    fun updateItems(itemList: Collection<Parent>) {
        adapter.updateItems(ArrayList(itemList))
    }

    /**
     * Add a OnMenuItemClickListener to the toolbar
     *
     * @param listener The OnMenuItemClickListener to be added
     */
    fun addToolbarMenuListener(listener: Toolbar.OnMenuItemClickListener) {
        toolbar.setOnMenuItemClickListener(listener)
    }

    /**
     * Add a DrawerListener to the DrawerLayout
     *
     * @param drawerListener The DrawerListener to be added
     */
    fun addDrawerListener(drawerListener: DrawerLayout.DrawerListener) {
        drawerLayout.addDrawerListener(drawerListener)
    }

    /**
     * Set the OnFilterControlClickListener to the FilterDrawer
     *
     * @param filterControlClickListener The OnFilterControlClickListener to be set
     */
    fun setFilterControlClickListener(filterControlClickListener: OnFilterControlClickListener) {
        resetBtn.setOnClickListener { filterControlClickListener.onResetClick() }
        applyBtn.setOnClickListener { filterControlClickListener.onApplyClick() }
    }

    /**
     * Add a OnChildSelectListener to the FilterDrawer
     *
     * @param childSelectListener The OnChildSelectListener to be added
     */
    fun addChildSelectListener(childSelectListener: OnChildSelectListener<Parent, Child>) {
        adapter.childSelectListenerList.add(childSelectListener)
    }

    /**
     * Remove the OnChildSelectListener from the FilterDrawer
     *
     * @param childSelectListener The reference of OnChildSelectListener to be removed
     */
    fun removeChildSelectListener(childSelectListener: OnChildSelectListener<Parent, Child>) {
        adapter.childSelectListenerList.remove(childSelectListener)
    }

    /**
     * Set the text of the reset button in FilterDrawer
     *
     * @param text The text to be set for the reset button
     */
    fun setResetText(text: String) {
        builder.resetText = text
        resetBtn.text = builder.resetText
    }

    /**
     * Set the color of text of the reset button in FilterDrawer
     *
     * @param color The color of text to be set for the reset button
     */
    fun setResetColor(@ColorRes color: Int) {
        val act = builder.activity ?: return
        builder.resetColor = ContextCompat.getColor(act, color)
        resetBtn.setTextColor(builder.resetColor)
    }

    /**
     * Set the text of the apply button in FilterDrawer
     *
     * @param text The text to be set for the apply button
     */
    fun setApplyText(text: String) {
        builder.applyText = text
        applyBtn.text = builder.applyText
    }

    /**
     * Set the color of text of the apply button in FilterDrawer
     *
     * @param color The color of text to be set for the apply button
     */
    fun setApplyColor(@ColorRes color: Int) {
        val act = builder.activity ?: return
        builder.applyColor = ContextCompat.getColor(act, color)
        applyBtn.setTextColor(builder.applyColor)
    }

    /**
     * Shout 'Open Sesame' to the FilterDrawer
     */
    fun openFilterDrawer() {
        drawerLayout.openDrawer(filterView)
    }

    /**
     * Shout 'Close Sesame' to the FilterDrawer
     */
    fun closeFilterDrawer() {
        drawerLayout.closeDrawer(filterView)
    }

    /**
     * Reset the FilterDrawer
     */
    fun resetFilter() {
        adapter.reset()
    }

    /**
     * Get the map of the currently selected ParentItem to Set of ChildItem
     *
     * @return The map of the currently selected ParentItem to Set of ChildItem
     */
    fun getSelectedChildrens(): Map<Parent, Set<Child>> {
        return adapter.getSelectedChildren()
    }

    /**
     * Listener for events when status of ChildItem in FilterDrawer changes
     *
     * @param Parent Type that extends ParentItem
     * @param Child Type that extends ChildItem
     */
    interface OnChildSelectListener <Parent, Child> where Parent: ParentItem, Child: ChildItem {

        /**
         * Called when ChildItem get selected
         *
         * @param parent The ParentItem that the selected ChildItem is belongs to
         * @param child The ChildItem that got selected
         */
        fun onChildSelect(parent: Parent, child: Set<Child>)

        /**
         * Called when ChildItem get deselected
         *
         * @param parent The ParentItem that the deselected ChildItem is belongs to
         * @param child The ChildItem that got deselected
         */
        fun onChildDeselect(parent: Parent, child: Set<Child>)

        /**
         * Called when the FilterDrawer resets
         */
        fun onReset()
    }

    /**
     * Listener for event when FilterDrawer's control buttons are clicked
     */
    interface OnFilterControlClickListener {

        /**
         * Called when reset button is clicked
         */
        fun onResetClick()

        /**
         * Called when apply button is clicked
         */
        fun onApplyClick()
    }
}