package com.camerash.filterdrawer.app

import com.camerash.filterdrawer.ChildItem
import com.camerash.filterdrawer.DefaultParentItem
import com.camerash.filterdrawer.R

class SampleParentItem(name: String, childList: ArrayList<SampleChildItem>): DefaultParentItem() {

    var name = ""
    var childList = arrayListOf<SampleChildItem>()

    init {
        this.name = name
        this.childList = childList
    }

    override fun getParentIcon(): Int = R.drawable.round_work_24

    override fun getParentTitle(): String = name

    override fun getChildCollection(): List<ChildItem> = childList

    override fun getSelectedColorRes(): Int = R.color.colorPrimary
}