package com.camerash.filterdrawer

import android.app.Activity
import android.graphics.Color
import android.os.Build
import android.support.annotation.ColorRes
import android.support.annotation.LayoutRes
import android.support.annotation.MenuRes
import android.support.annotation.NonNull
import android.support.v4.content.ContextCompat
import android.support.v4.view.GravityCompat
import android.support.v4.widget.DrawerLayout
import android.view.View
import android.view.ViewGroup
import com.mikepenz.materialize.MaterializeBuilder

/**
 * Builder for building the FilterDrawer
 *
 * @author Camerash
 * @param Parent Type of your custom type that extends ParentItem
 * @param Child Type of your custom type that extends ChildItem
 * @see FilterDrawer
 */
class DrawerBuilder<Parent, Child>() where Parent: ParentItem, Child: ChildItem {

    internal var activity: Activity? = null
    internal var itemList = listOf<Parent>()
    internal var displayToolbar = true
    internal var toolbarTitle = "Filters"
    internal var toolbarMenuResId = 0

    internal var resetText = "RESET"
    internal var applyText = "APPLY"
    internal var resetColor = Color.parseColor("#ffcc0000")
    internal var applyColor = Color.parseColor("#2196F3")

    private var rootView: ViewGroup? = null
    private var translucentStatusBar = true
    private var displayBelowStatusBar = false
    private var fullscreen = false
    private var systemUIHidden = false
    private var gravity = GravityCompat.END
    private var drawerLockMode = DrawerLayout.LOCK_MODE_UNLOCKED
    private val customDrawerMap = mutableMapOf<Int, View>()

    var drawerListener: DrawerLayout.DrawerListener? = null
    var childSelectListenerList = arrayListOf<FilterDrawer.OnChildSelectListener<Parent, Child>>()
    var filterControlClickListener: FilterDrawer.OnFilterControlClickListener? = null

    private var drawerLayout: DrawerLayout? = null
    private var filterView: View? = null

    /**
     * Give builder a reference to the activity
     *
     * @param act Reference to the activity
     * @return this
     */
    constructor(@NonNull act: Activity) : this() {
        with(act)
    }

    /**
     * Give builder a reference to the activity
     *
     * @param act Reference to the activity
     * @return this
     */
    fun with(@NonNull act: Activity): DrawerBuilder<Parent, Child> {
        this.activity = act
        this.rootView = act.findViewById(android.R.id.content)

        return this
    }

    /**
     * If enabled, status bar will be translucent
     *
     * Default to true
     * @param enable Whether to make status bar to be translucent
     * @return this
     */
    fun setTranslucentStatusBar(enable: Boolean): DrawerBuilder<Parent, Child> {
        this.translucentStatusBar = enable
        return this
    }

    /**
     * If enabled, FilterDrawer will be drawn behind status bar
     *
     * Default to false
     * @param enable Whether to draw FilterDrawer behind status bar
     * @return this
     */
    fun setDisplayBelowStatusBar(enable: Boolean): DrawerBuilder<Parent, Child> {
        this.displayBelowStatusBar = enable
        return this
    }

    /**
     * If enabled, activity will be set to fullscreen
     *
     * Default to false
     * @param enable Whether to make activity fullscreen
     * @return this
     */
    fun setFullscreen(enable: Boolean): DrawerBuilder<Parent, Child> {
        this.fullscreen = enable
        return this
    }

    /**
     * If enabled, system UI will be hidden
     *
     * Default to false
     * @param enable Whether to make system UI hidden
     * @return this
     */
    fun setSystemUIHidden(enable: Boolean): DrawerBuilder<Parent, Child> {
        this.systemUIHidden = enable
        return this
    }

    /**
     * If enabled, toolbar in FilterDrawer will be displayed
     *
     * Default to true
     * @param enable Whether to display toolbar
     * @return this
     */
    fun displayToolbar(display: Boolean): DrawerBuilder<Parent, Child> {
        this.displayToolbar = display
        return this
    }

    /**
     * Set the title in the toolbar
     *
     * Default to "Filters"
     * @param title The title for toolbar
     * @return this
     */
    fun setToolbarTitle(title: String): DrawerBuilder<Parent, Child> {
        this.displayToolbar(true)
        this.toolbarTitle = title
        return this
    }

    /**
     * Set the menu in toolbar
     *
     * Default to 0 (i.e.: no menu)
     * @param menuId The resource id of the menu
     * @return this
     */
    fun setToolbarMenu(@MenuRes menuId: Int): DrawerBuilder<Parent, Child> {
        this.displayToolbar(true)
        this.toolbarMenuResId = menuId
        return this
    }

    /**
     * Set the gravity of the FilterDrawer
     *
     * Default to Gravity.END
     * @param gravity The gravity enum
     * @return this
     * @see GravityCompat
     */
    fun setGravity(gravity: Int): DrawerBuilder<Parent, Child> {
        this.gravity = gravity
        return this
    }

    /**
     * Add a OnChildSelectListener to the FilterDrawer
     *
     * @param childSelectListener The OnChildSelectListener to be added
     * @return this
     * @see FilterDrawer.OnChildSelectListener
     */
    fun addChildSelectListener(childSelectListener: FilterDrawer.OnChildSelectListener<Parent, Child>): DrawerBuilder<Parent, Child> {
        this.childSelectListenerList.add(childSelectListener)
        return this
    }

    /**
     * Set the DrawerListener to the FilterDrawer
     *
     * @param drawerListener The DrawerListener to be set
     * @return this
     * @see DrawerLayout.DrawerListener
     */
    fun setDrawerListener(drawerListener: DrawerLayout.DrawerListener): DrawerBuilder<Parent, Child> {
        this.drawerListener = drawerListener
        return this
    }

    /**
     * Set the OnFilterControlClickListener to the FilterDrawer.
     *
     * Responsible for events of buttons in the bottom of the FilterDrawer (i.e.: RESET / APPLY)
     * @param filterControlClickListener The OnFilterControlClickListener to be set
     * @return this
     * @see FilterDrawer.OnFilterControlClickListener
     */
    fun setFilterControlClickListener(filterControlClickListener: FilterDrawer.OnFilterControlClickListener): DrawerBuilder<Parent, Child> {
        this.filterControlClickListener = filterControlClickListener
        return this
    }

    /**
     * Set the lock mode of FilterDrawer
     *
     * Default to DrawerLayout.LOCK_MODE_UNLOCKED
     * @param lockMode The lock mode of FilterDrawer
     * @return this
     * @see DrawerLayout
     */
    fun setDrawerLockMode(lockMode: Int): DrawerBuilder<Parent, Child> {
        this.drawerLockMode = lockMode
        return this
    }

    /**
     * Add a custom drawer to the layout
     *
     * Note that drawers with the same gravity as the FilterDrawer will be removed
     * @param view The view for the custom drawer to be added
     * @param gravity The gravity of the custom drawer
     * @return this
     */
    fun appendCustomDrawer(view: View, gravity: Int): DrawerBuilder<Parent, Child> {
        this.customDrawerMap[gravity] = view // Use map here as each gravity can only host one drawer
        return this
    }

    /**
     * Set text for the reset button
     *
     * @param text The text for the reset button
     * @return this
     */
    fun setResetText(text: String): DrawerBuilder<Parent, Child> {
        this.resetText = text
        return this
    }

    /**
     * Set color resource for the reset button
     *
     * @param color The color for the reset button
     * @return this
     */
    fun setResetColor(@ColorRes color: Int): DrawerBuilder<Parent, Child> {
        val act = this.activity ?: throw RuntimeException("Please pass an activity reference to the builder first")
        this.resetColor = ContextCompat.getColor(act, color)
        return this
    }

    /**
     * Set text for the apply button
     *
     * @param text The text for the apply button
     * @return this
     */
    fun setApplyText(text: String): DrawerBuilder<Parent, Child> {
        this.applyText = text
        return this
    }

    /**
     * Set color resource for the apply button
     *
     * @param color The color for the apply button
     * @return this
     */
    fun setApplyColor(@ColorRes color: Int): DrawerBuilder<Parent, Child> {
        val act = this.activity ?: throw RuntimeException("Please pass an activity reference to the builder first")
        this.applyColor = ContextCompat.getColor(act, color)
        return this
    }

    /**
     * Set the filter items
     *
     * @param items The items to be set to FilterDrawer
     * @return this
     */
    fun withItems(items: Collection<Parent>): DrawerBuilder<Parent, Child> {
        this.itemList = ArrayList(items)
        return this
    }

    /**
     * Define a custom layout for the drawer
     *
     * @param resLayout The layout resource of the custom layout
     * @return this
     */
    fun withDrawerLayout(@LayoutRes resLayout: Int): DrawerBuilder<Parent, Child> {
        val act = this.activity ?: throw RuntimeException("Please pass an activity reference to the builder first")

        this.drawerLayout = when (resLayout) {
            -1 -> {
                if (Build.VERSION.SDK_INT < 21)
                    act.layoutInflater.inflate(R.layout.drawer_layout, rootView, false) as DrawerLayout
                else
                    act.layoutInflater.inflate(R.layout.drawer_layout_fit_system_windows, rootView, false) as DrawerLayout
            }
            else -> act.layoutInflater.inflate(resLayout, rootView, false) as DrawerLayout
        }

        return this
    }

    /**
     * Construct the FilterDrawer
     *
     * @return FilterDrawer
     */
    fun build(): FilterDrawer<Parent, Child> {
        this.activity ?: throw RuntimeException("Please pass an activity reference to the builder first")

        if (this.drawerLayout == null) withDrawerLayout(-1)

        with(MaterializeBuilder()) {
            withActivity(activity)
            withRootView(rootView)
            withFullscreen(fullscreen)
            withSystemUIHidden(systemUIHidden)
            withUseScrimInsetsLayout(false)
            withTransparentStatusBar(translucentStatusBar)
            withContainer(drawerLayout)
            build()
        }

        clearOverlapDrawers()
        addCustomDrawers()

        buildFilterDrawer()

        val finalDrawerLayout = drawerLayout
        val finalFilterView = filterView

        if (finalDrawerLayout == null || finalFilterView == null) {
            throw IllegalArgumentException("Filter view or drawer layout not setup")
        }

        return FilterDrawer(this, finalDrawerLayout, finalFilterView)
    }

    /**
     * Internal method for clearing custom drawers with the same gravity as the FilterDrawer
     */
    private fun clearOverlapDrawers() {
        this.customDrawerMap.remove(this.gravity)
    }

    /**
     * Internal method for adding custom drawers
     */
    private fun addCustomDrawers() {
        val drawerLayout = this.drawerLayout ?: return
        customDrawerMap.forEach { (gravity, view) ->
            drawerLayout.addView(view, 1)
            setLayoutGravity(view, gravity)
        }
    }

    /**
     * Internal method for building the FilterDrawer
     */
    private fun buildFilterDrawer() {
        val act = activity ?: return
        filterView = act.layoutInflater.inflate(R.layout.filter_drawer, this.drawerLayout, false)
        drawerLayout?.addView(filterView)

        filterView?.let {
            setLayoutGravity(it, this.gravity)
            drawerLayout?.setDrawerLockMode(drawerLockMode, it)
            it.fitsSystemWindows = displayBelowStatusBar
        }
    }

    /**
     * Internal method for setting layout gravity of drawers
     */
    private fun setLayoutGravity(view: View, gravity: Int) {
        val params = view.layoutParams as DrawerLayout.LayoutParams
        params.gravity = gravity
        view.layoutParams = params
    }
}