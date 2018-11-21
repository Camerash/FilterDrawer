package com.camerash.filterdrawer.app

import android.support.annotation.DrawableRes
import com.camerash.filterdrawer.ChildItem
import com.camerash.filterdrawer.DefaultParentItem
import com.camerash.filterdrawer.R

class PetFilterCategory(val type: FilterType, @DrawableRes val icon: Int, private val childList: List<PetFilter>): DefaultParentItem() {

    enum class FilterType { Kind, Size }

    override fun getParentIcon(): Int = this.icon

    override fun getParentTitle(): String = this.type.name

    override fun getChildCollection(): List<ChildItem> = this.childList

    override fun getSelectedTextColorRes(): Int = R.color.colorPrimary

    override fun allowSelectMultiple(): Boolean = true
}