package com.camerash.filterdrawer.app

import com.camerash.filterdrawer.DefaultChildItem

class SampleChildItem(name: String) : DefaultChildItem() {

    var name = ""

    init { this.name = name }

    override fun getTitle(): String = this.name

    override fun getSelectedColorRes(): Int = getSelectedColorRes()
}