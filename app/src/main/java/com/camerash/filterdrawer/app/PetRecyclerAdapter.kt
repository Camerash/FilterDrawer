package com.camerash.filterdrawer.app

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import butterknife.BindView
import com.camerash.filterdrawer.FilterableRecyclerAdapter
import com.camerash.filterdrawer.R

class PetRecyclerAdapter(override val dataList: List<Pet>) : FilterableRecyclerAdapter<Pet, PetFilterCategory, PetFilter>() {

    override fun filter(data: Pet, filterMap: Map<PetFilterCategory, Set<PetFilter>>): Boolean {
        filterMap.forEach { (petFilterCategory, filterSet) ->
            filterSet.firstOrNull {
                it.filter == when (petFilterCategory.type) {
                    PetFilterCategory.FilterType.Kind -> {
                        data.kind
                    }
                    PetFilterCategory.FilterType.Size -> {
                        data.size
                    }
                }
            } ?: return false
        }
        return true
    }

    override fun onCreateViewHolder(container: ViewGroup, viewType: Int): RecyclerView.ViewHolder =
            ViewHolder(LayoutInflater.from(container.context).inflate(R.layout.item_pet, container, false))

    override fun getItemCount(): Int = dataList.size

    override fun onBindViewHolder(viewHolder: RecyclerView.ViewHolder, position: Int) {
        val vh = viewHolder as ViewHolder
        val pet = dataList[position]

        vh.bindView(pet)
    }

    inner class ViewHolder(v: View) : RecyclerView.ViewHolder(v) {
        @BindView(R.id.name) lateinit var name: TextView
        @BindView(R.id.kind) lateinit var kind: TextView
        @BindView(R.id.size) lateinit var size: TextView

        fun bindView(pet: Pet) {
            name.text = pet.name
            kind.text = pet.kind.name
            size.text = pet.size.name
        }
    }
}