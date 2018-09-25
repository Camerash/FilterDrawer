package com.camerash.library

import android.app.Activity

class DrawerBuilder() {

    private var activity: Activity? = null

    constructor(act: Activity): this() {
        this.activity = act
    }

    fun with(act: Activity): DrawerBuilder {
        this.activity
        return this
    }

}