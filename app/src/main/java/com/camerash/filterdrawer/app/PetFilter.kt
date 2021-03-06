package com.camerash.filterdrawer.app

import com.camerash.filterdrawer.DefaultChildItem
import com.camerash.filterdrawer.R

class PetFilter(val filter: Enum<*>) : DefaultChildItem() {

    enum class Kind { Cats, Dogs, Rabbits, Hamsters, Birds }
    enum class Size { Small, Medium, Large }

    override fun getTitle(): String = this.filter.name

    override fun getSelectedTextColorRes(): Int = R.color.colorPrimary
}