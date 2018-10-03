package com.camerash.filterdrawer.app

import android.support.annotation.DrawableRes
import com.camerash.filterdrawer.ChildItem
import com.camerash.filterdrawer.DefaultParentItem
import com.camerash.filterdrawer.R

class PetFilterCategory(val type: FilterType, @DrawableRes val icon: Int, val childList: ArrayList<PetFilter>): DefaultParentItem() {

    enum class FilterType { Pet, Size }

    override fun getParentIcon(): Int = this.icon

    override fun getParentTitle(): String = this.type.name

    override fun getChildCollection(): List<ChildItem> = this.childList

    override fun getSelectedColorRes(): Int = R.color.colorPrimary
}