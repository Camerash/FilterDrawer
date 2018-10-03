package com.camerash.filterdrawer

import android.app.Activity
import android.os.Build
import android.support.annotation.LayoutRes
import android.support.annotation.MenuRes
import android.support.annotation.NonNull
import android.support.v4.view.GravityCompat
import android.support.v4.widget.DrawerLayout
import android.view.View
import android.view.ViewGroup
import com.mikepenz.materialize.MaterializeBuilder


class DrawerBuilder<Parent, Child>() where Parent: ParentItem, Child: ChildItem {

    internal var activity: Activity? = null
    internal var itemList = listOf<Parent>()
    internal var displayToolbar = true
    internal var toolbarTitle = "Filters"
    internal var toolbarMenuResId = 0

    private var rootView: ViewGroup? = null
    private var translucentStatusBar = true
    private var displayBelowStatusBar = false
    private var fullscreen = false
    private var systemUIHidden = false
    private var gravity = GravityCompat.END
    private var drawerLockMode = DrawerLayout.LOCK_MODE_UNLOCKED
    private val customDrawerMap = mutableMapOf<Int, View>()

    var drawerListener: DrawerLayout.DrawerListener? = null
    var childSelectListener: FilterDrawer.OnChildSelectListener<Parent, Child>? = null
    var filterControlClickListener: FilterDrawer.OnFilterControlClickListener? = null

    private var drawerLayout: DrawerLayout? = null
    private var filterView: View? = null


    constructor(@NonNull act: Activity) : this() {
        this.activity = act
        this.rootView = act.findViewById(android.R.id.content)
    }

    fun with(@NonNull act: Activity): DrawerBuilder<Parent, Child> {
        this.activity = act
        this.rootView = act.findViewById(android.R.id.content)
        return this
    }

    fun setTranslucentStatusBar(enable: Boolean): DrawerBuilder<Parent, Child> {
        this.translucentStatusBar = enable
        return this
    }

    fun setDisplayBelowStatusBar(enable: Boolean): DrawerBuilder<Parent, Child> {
        this.displayBelowStatusBar = enable
        return this
    }

    fun setFullscreen(enable: Boolean): DrawerBuilder<Parent, Child> {
        this.fullscreen = enable
        return this
    }

    fun setSystemUIHidden(enable: Boolean): DrawerBuilder<Parent, Child> {
        this.systemUIHidden = enable
        return this
    }

    fun displayToolbar(display: Boolean): DrawerBuilder<Parent, Child> {
        this.displayToolbar = display
        return this
    }

    fun setToolbarTitle(title: String): DrawerBuilder<Parent, Child> {
        this.displayToolbar(true)
        this.toolbarTitle = title
        return this
    }

    fun setToolbarMenu(@MenuRes menuId: Int): DrawerBuilder<Parent, Child> {
        this.displayToolbar(true)
        this.toolbarMenuResId = menuId
        return this
    }

    fun setGravity(gravity: Int): DrawerBuilder<Parent, Child> {
        this.gravity = gravity
        return this
    }

    fun setChildSelectListener(childSelectListener: FilterDrawer.OnChildSelectListener<Parent, Child>): DrawerBuilder<Parent, Child> {
        this.childSelectListener = childSelectListener
        return this
    }

    fun setDrawerListener(drawerListener: DrawerLayout.DrawerListener): DrawerBuilder<Parent, Child> {
        this.drawerListener = drawerListener
        return this
    }

    fun setFilterControlClickListener(filterControlClickListener: FilterDrawer.OnFilterControlClickListener): DrawerBuilder<Parent, Child> {
        this.filterControlClickListener = filterControlClickListener
        return this
    }

    fun setDrawerLockMode(lockMode: Int): DrawerBuilder<Parent, Child> {
        this.drawerLockMode = lockMode
        return this
    }

    fun appendCustomDrawer(view: View, gravity: Int): DrawerBuilder<Parent, Child> {
        this.customDrawerMap[gravity] = view // Use map here as each gravity can only host one drawer
        return this
    }

    fun withItems(items: Collection<Parent>): DrawerBuilder<Parent, Child> {
        this.itemList = ArrayList(items)
        return this
    }

    fun withDrawerLayout(@LayoutRes resLayout: Int): DrawerBuilder<Parent, Child> {
        val act = this.activity
                ?: throw RuntimeException("Please pass an activity reference to the builder first")

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

    fun build(): FilterDrawer<Parent, Child> {
        this.activity
                ?: throw RuntimeException("Please pass an activity reference to the builder first")

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

    private fun clearOverlapDrawers() {
        this.customDrawerMap.remove(this.gravity)
    }

    private fun addCustomDrawers() {
        val drawerLayout = this.drawerLayout ?: return
        customDrawerMap.forEach { (gravity, view) ->
            drawerLayout.addView(view, 1)

            DrawerUtils.setLayoutGravity(view, gravity)
        }
    }

    private fun buildFilterDrawer() {
        val act = activity ?: return
        filterView = act.layoutInflater.inflate(R.layout.filter_drawer, this.drawerLayout, false)
        drawerLayout?.addView(filterView)

        filterView?.let {
            DrawerUtils.setLayoutGravity(it, this.gravity)
            drawerLayout?.setDrawerLockMode(drawerLockMode, it)
            it.fitsSystemWindows = displayBelowStatusBar
        }
    }
}