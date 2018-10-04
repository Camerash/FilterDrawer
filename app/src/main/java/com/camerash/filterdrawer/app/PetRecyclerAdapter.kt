package com.camerash.filterdrawer.app

import android.support.v7.widget.RecyclerView
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import com.camerash.filterdrawer.FilterableRecyclerAdapter
import com.camerash.filterdrawer.R

class PetRecyclerAdapter(override val dataList: List<Pet>) : FilterableRecyclerAdapter<Pet, PetFilterCategory, PetFilter>() {

    override fun filter(data: Pet, filterMap: Map<PetFilterCategory, Set<PetFilter>>): Boolean {
        var match = false
        filterMap.forEach { (petFilterCategory, filterSet) ->
            Log.d(petFilterCategory.type.name, filterSet.toString())
            if(filterSet.firstOrNull {
                it.filter == when (petFilterCategory.type) {
                    PetFilterCategory.FilterType.Kind -> {
                        data.kind
                    }
                    PetFilterCategory.FilterType.Size -> {
                        data.size
                    }
                }
            } != null) { if(!match) match = true }
        }
        return match
    }

    override fun onCreateViewHolder(container: ViewGroup, viewType: Int): RecyclerView.ViewHolder =
            ViewHolder(LayoutInflater.from(container.context).inflate(R.layout.item_pet, container, false))

    override fun getItemCount(): Int = filteredDataList.size

    override fun onBindViewHolder(viewHolder: RecyclerView.ViewHolder, position: Int) {
        val vh = viewHolder as ViewHolder
        val pet = filteredDataList[position]

        vh.bindView(pet)
    }

    inner class ViewHolder(v: View) : RecyclerView.ViewHolder(v) {
        val name: TextView = itemView.findViewById(R.id.name)
        val kind: TextView = itemView.findViewById(R.id.kind)
        val size: TextView = itemView.findViewById(R.id.size)

        fun bindView(pet: Pet) {
            name.text = pet.name
            kind.text = pet.kind.name
            size.text = pet.size.name
        }
    }
}