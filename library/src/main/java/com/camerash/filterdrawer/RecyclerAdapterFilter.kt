package com.camerash.filterdrawer

interface RecyclerAdapterFilter <Data, Parent, Child> where Parent : ParentItem, Child : ChildItem {
    fun filter(data: Data, parent: Parent, child: Child): Boolean
}