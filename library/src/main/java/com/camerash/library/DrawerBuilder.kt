package com.camerash.library

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


class DrawerBuilder() {

    private var rootView: ViewGroup? = null
    private var activity: Activity? = null
    private var translucentStatusBar = true
    private var displayBelowStatusBar = false
    private var fullscreen = false
    private var systemUIHidden = false
    private var displayToolbar = true
    private var toolbarTitle = "Filters"
    private var toolbarMenuResId = 0
    private var gravity = GravityCompat.END
    private val customDrawerMap = mutableMapOf<Int, View>()

    internal var drawerLayout: DrawerLayout? = null
    internal var filterView: View? = null

    constructor(@NonNull act: Activity) : this() {
        this.activity = act
        this.rootView = act.findViewById(android.R.id.content)
    }

    fun with(@NonNull act: Activity): DrawerBuilder {
        this.activity = act
        this.rootView = act.findViewById(android.R.id.content)
        return this
    }

    fun setTranslucentStatusBar(enable: Boolean): DrawerBuilder {
        this.translucentStatusBar = enable
        return this
    }

    fun setDisplayBelowStatusBar(enable: Boolean): DrawerBuilder {
        this.displayBelowStatusBar = enable
        return this
    }

    fun setFullscreen(enable: Boolean): DrawerBuilder {
        this.fullscreen = enable
        return this
    }

    fun setSystemUIHidden(enable: Boolean): DrawerBuilder {
        this.systemUIHidden = enable
        return this
    }


    fun displayToolbar(display: Boolean): DrawerBuilder {
        this.displayToolbar = display
        return this
    }

    fun setToolbarTitle(title: String): DrawerBuilder {
        this.displayToolbar(true)
        this.toolbarTitle = title
        return this
    }

    fun setToolbarMenu(@MenuRes menuId: Int): DrawerBuilder {
        this.displayToolbar(true)
        this.toolbarMenuResId = menuId
        return this
    }

    fun setGravity(gravity: Int): DrawerBuilder {
        this.gravity = gravity
        return this
    }

    fun appendCustomDrawer(view: View, gravity: Int): DrawerBuilder {
        this.customDrawerMap[gravity] = view // Use map here as each gravity can only host one drawer
        return this
    }

    fun withDrawerLayout(@LayoutRes resLayout: Int): DrawerBuilder {
        val act = this.activity ?: throw RuntimeException("Please pass an activity reference to the builder first")

        this.drawerLayout = when(resLayout) {
            -1 -> {
                if(Build.VERSION.SDK_INT < 21)
                    act.layoutInflater.inflate(R.layout.drawer_layout, rootView, false) as DrawerLayout
                else
                    act.layoutInflater.inflate(R.layout.drawer_layout_fit_system_windows, rootView, false) as DrawerLayout
            }
            else -> act.layoutInflater.inflate(resLayout, rootView, false) as DrawerLayout
        }

        return this
    }

    fun build(): FilterDrawer {
        this.activity ?: throw RuntimeException("Please pass an activity reference to the builder first")

        if(this.drawerLayout == null) withDrawerLayout(-1)

        with(MaterializeBuilder()){
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

        return FilterDrawer(this)
    }

    private fun clearOverlapDrawers() {
        this.customDrawerMap.remove(this.gravity)
    }

    private fun addCustomDrawers() {
        val drawerLayout = this.drawerLayout ?: return
        this.customDrawerMap.forEach { gravity, view ->
            drawerLayout.addView(view, 1)

            DrawerUtils.setLayoutGravity(view, gravity)
        }
    }

    private fun buildFilterDrawer() {
        val act = activity ?: return
        filterView = act.layoutInflater.inflate(R.layout.filter_drawer, this.drawerLayout, false)

        filterView?.let { DrawerUtils.setLayoutGravity(it, this.gravity) }
    }
}