package com.camerash.filterdrawer

/**
 * Interface for implementation of filter logic used in FilterableRecyclerAdapter
 *
 * @author Camerash
 * @param Data Your custom data type that implements DiffItemCallback
 * @param Parent Your custom parent type that extends ParentItem
 * @param Child Your custom child type that extends ChildItem
 * @see FilterableRecyclerAdapter
 */
interface RecyclerAdapterFilter <Data, Parent, Child> where Parent : ParentItem, Child : ChildItem {

    /**
     * Called when filtering of data items happens
     *
     * @param data The data item to be filtered
     * @param parent The ParentItem (category) that the ChildItem (filter) belongs to
     * @param child The ChildItem (filter) to be compared with the data item
     * @return Whether the data should be included in the filtered list. If false, this item will be filtered out
     */
    fun filter(data: Data, parent: Parent, child: Child): Boolean
}