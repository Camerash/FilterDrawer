package com.camerash.filterdrawer

interface DiffItemCallback <Data> {
    fun isIdentical(item: Data): Boolean
    fun hasSameRepresentation(item: Data): Boolean
}