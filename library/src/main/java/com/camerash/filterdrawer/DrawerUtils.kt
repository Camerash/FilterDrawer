package com.camerash.filterdrawer

import android.support.v4.widget.DrawerLayout
import android.view.View

class DrawerUtils {
    companion object {
        fun setLayoutGravity(view: View, gravity: Int) {
            val params = view.layoutParams as DrawerLayout.LayoutParams
            params.gravity = gravity
            view.layoutParams = params
        }
    }
}