package com.camerash.filterdrawer.app

import com.camerash.filterdrawer.DiffItemCallback

class Pet(val name: String, val imageUrl: String, val kind: PetFilter.Kind, val size: PetFilter.Size) : DiffItemCallback<Pet> {
    override fun isIdentical(item: Pet): Boolean {
        return name == item.name
    }

    override fun hasSameRepresentation(item: Pet): Boolean {
        return imageUrl == item.imageUrl
    }
}