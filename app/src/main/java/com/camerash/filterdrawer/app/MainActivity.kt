package com.camerash.filterdrawer.app

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.util.Log
import com.camerash.filterdrawer.DrawerBuilder
import com.camerash.filterdrawer.FilterDrawer
import com.camerash.filterdrawer.R
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity(), FilterDrawer.OnFilterControlClickListener, FilterDrawer.OnChildSelectListener<SampleParentItem, SampleChildItem> {

    private lateinit var filterDrawer: FilterDrawer<SampleParentItem, SampleChildItem>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        setSupportActionBar(toolbar)

        val sampleItemList = constructSample()

        filterDrawer = DrawerBuilder<SampleParentItem, SampleChildItem>(this)
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

    override fun onChildSelect(parent: SampleParentItem, childItem: SampleChildItem) {
        filterDrawer.getSelectedChildrens().forEach {
            Log.d(it.key.type.name, it.value.name)
        }
    }

    override fun onChildDeselect(parent: SampleParentItem, childItem: SampleChildItem) {
        filterDrawer.getSelectedChildrens().forEach {
            Log.d(it.key.type.name, it.value.name)
        }
    }

    override fun onReset() {
        Log.d("reset", "true")
    }

    private fun constructSample(): ArrayList<SampleParentItem> {
        val animal1 = SampleChildItem("Cats")
        val animal2 = SampleChildItem("Dogs")
        val animal3 = SampleChildItem("Rabbits")
        val animal4 = SampleChildItem("Hamsters")
        val animal5 = SampleChildItem("Snakes")

        val pet = SampleParentItem(SampleParentItem.FilterType.Pet, R.drawable.round_pets_24, arrayListOf(animal1, animal2, animal3, animal4, animal5))

        val size1 = SampleChildItem("Small")
        val size2 = SampleChildItem("Medium")
        val size3 = SampleChildItem("Large")

        val animal = SampleParentItem(SampleParentItem.FilterType.Size, R.drawable.round_size_24, arrayListOf(size1, size2, size3))

        return arrayListOf(pet, animal)
    }
}
