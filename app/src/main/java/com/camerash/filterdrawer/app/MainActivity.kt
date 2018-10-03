package com.camerash.filterdrawer.app

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.util.Log
import com.camerash.filterdrawer.DrawerBuilder
import com.camerash.filterdrawer.FilterDrawer
import com.camerash.filterdrawer.R
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity(), FilterDrawer.OnFilterControlClickListener, FilterDrawer.OnChildSelectListener<SampleFilterParentItem, SampleFilterChildItem> {

    private lateinit var filterDrawer: FilterDrawer<SampleFilterParentItem, SampleFilterChildItem>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        setSupportActionBar(toolbar)

        val sampleItemList = constructSample()

        filterDrawer = DrawerBuilder<SampleFilterParentItem, SampleFilterChildItem>(this)
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

    override fun onChildSelect(parent: SampleFilterParentItem, childItem: SampleFilterChildItem) {
        filterDrawer.getSelectedChildrens().forEach {
            Log.d(it.key.type.name, it.value.name)
        }
    }

    override fun onChildDeselect(parent: SampleFilterParentItem, childItem: SampleFilterChildItem) {
        filterDrawer.getSelectedChildrens().forEach {
            Log.d(it.key.type.name, it.value.name)
        }
    }

    override fun onReset() {
        Log.d("reset", "true")
    }

    private fun constructSample(): ArrayList<SampleFilterParentItem> {
        val animal1 = SampleFilterChildItem("Cats")
        val animal2 = SampleFilterChildItem("Dogs")
        val animal3 = SampleFilterChildItem("Rabbits")
        val animal4 = SampleFilterChildItem("Hamsters")
        val animal5 = SampleFilterChildItem("Snakes")

        val pet = SampleFilterParentItem(SampleFilterParentItem.FilterType.Pet, R.drawable.round_pets_24, arrayListOf(animal1, animal2, animal3, animal4, animal5))

        val size1 = SampleFilterChildItem("Small")
        val size2 = SampleFilterChildItem("Medium")
        val size3 = SampleFilterChildItem("Large")

        val animal = SampleFilterParentItem(SampleFilterParentItem.FilterType.Size, R.drawable.round_size_24, arrayListOf(size1, size2, size3))

        return arrayListOf(pet, animal)
    }
}
