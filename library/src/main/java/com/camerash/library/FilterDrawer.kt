package com.camerash.library

class FilterDrawer {
    interface DefaultHeaderBinder {
        fun getHeaderIcon(): Int
        fun getHeaderTitle(): String
    }

    interface DefaultItemBinder {
        fun getItemTitle(): String
    }
}