package com.camerash.filterdrawer

/**
 * Interface used for adapting DiffCallback to custom data type
 *
 * @author Camerash
 * @param Data The class adaping this interface
 */
interface DiffItemCallback <Data> {

    /**
     * Provides information on whether the two items are identical at a data level
     * You should compare IDs or item-specific variables here
     *
     * @param item The other item
     * @return Whether the items are identical
     */
    fun isIdentical(item: Data): Boolean

    /**
     * Provides information on whether the two items looks the same when shown to users
     * You should compare resources shown to users here
     *
     * @param item The other item
     * @return Whether the items' presentations are the same
     */
    fun hasSameRepresentation(item: Data): Boolean
}