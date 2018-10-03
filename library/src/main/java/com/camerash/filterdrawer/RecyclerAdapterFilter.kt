package com.camerash.filterdrawer

interface RecyclerAdapterFilter <Parent, Child, Data> where Parent : ParentItem, Child : ChildItem {
    fun filter(data: Data, filterMap: Map<Parent, Child>): Boolean
}