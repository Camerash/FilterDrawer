package com.camerash.filterdrawer.app

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.util.Log
import com.camerash.filterdrawer.DrawerBuilder
import com.camerash.filterdrawer.FilterDrawer
import com.camerash.filterdrawer.R
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity(), FilterDrawer.OnFilterControlClickListener, FilterDrawer.OnChildSelectListener<PetFilterCategory, PetFilter> {

    private lateinit var filterDrawer: FilterDrawer<PetFilterCategory, PetFilter>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        setSupportActionBar(toolbar)

        val sampleItemList = constructSample()

        filterDrawer = DrawerBuilder<PetFilterCategory, PetFilter>(this)
                .displayToolbar(true)
                .setFilterControlClickListener(this)
                .addChildSelectListener(this)
                .withItems(sampleItemList)
                .build()
    }

    override fun onResetClick() {
        filterDrawer.resetFilter()
    }

    override fun onApplyClick() {
        filterDrawer.closeFilterDrawer()
    }

    override fun onChildSelect(parent: PetFilterCategory, child: PetFilter) {
        filterDrawer.getSelectedChildrens().forEach {
            Log.d(it.key.type.name, it.value.name)
        }
    }

    override fun onChildDeselect(parent: PetFilterCategory, child: PetFilter) {
        filterDrawer.getSelectedChildrens().forEach {
            Log.d(it.key.type.name, it.value.name)
        }
    }

    override fun onReset() {
        Log.d("reset", "true")
    }

    private fun constructSample(): ArrayList<PetFilterCategory> {
        val animal1 = PetFilter("Cats")
        val animal2 = PetFilter("Dogs")
        val animal3 = PetFilter("Rabbits")
        val animal4 = PetFilter("Hamsters")
        val animal5 = PetFilter("Snakes")

        val pet = PetFilterCategory(PetFilterCategory.FilterType.Pet, R.drawable.round_pets_24, arrayListOf(animal1, animal2, animal3, animal4, animal5))

        val size1 = PetFilter("Small")
        val size2 = PetFilter("Medium")
        val size3 = PetFilter("Large")

        val animal = PetFilterCategory(PetFilterCategory.FilterType.Size, R.drawable.round_size_24, arrayListOf(size1, size2, size3))

        return arrayListOf(pet, animal)
    }
}
