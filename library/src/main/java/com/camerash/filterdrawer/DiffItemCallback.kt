package com.camerash.filterdrawer

/**
 * Interface used for adapting DiffCallback to custom data type
 *
 * @author Camerash
 * @param Data the class adaping this interface
 */
interface DiffItemCallback <Data> {

    /**
     * Provides information on whether the two items are identical at a data level
     * You should compare IDs or item-specific variables here
     *
     * @param item the other item
     * @return whether the items are identical
     */
    fun isIdentical(item: Data): Boolean

    /**
     * Provides information on whether the two items looks the same when shown to users
     * You should compare resources shown to users here
     *
     * @param item the other item
     * @return whether the items' presentations are the same
     */
    fun hasSameRepresentation(item: Data): Boolean
}