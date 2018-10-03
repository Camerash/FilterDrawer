package com.camerash.filterdrawer

interface RecyclerAdapterFilter <Data, Parent, Child> where Parent : ParentItem, Child : ChildItem {
    fun filter(data: Data, filterMap: Map<Parent, Set<Child>>): Boolean
}