package com.camerash.filterdrawer.app

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import com.camerash.filterdrawer.DrawerBuilder
import com.camerash.filterdrawer.FilterDrawer
import com.camerash.filterdrawer.R
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity(), FilterDrawer.OnFilterControlClickListener {

    private lateinit var filterDrawer: FilterDrawer

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        setSupportActionBar(toolbar)

        val sampleItemList = constructSample()

        filterDrawer = DrawerBuilder(this)
                .setTranslucentStatusBar(true)
                .setDisplayBelowStatusBar(true)
                .displayToolbar(false)
                .setFilterControlClickListener(this)
                .withItems(sampleItemList)
                .build()
    }

    override fun onResetClick() {
        filterDrawer.resetFilter()
    }

    override fun onApplyClick() {
        filterDrawer.closeFilterDrawer()
    }

    private fun constructSample(): ArrayList<SampleParentItem> {
        val animal1 = SampleChildItem("Cats")
        val animal2 = SampleChildItem("Dogs")
        val animal3 = SampleChildItem("Rabbits")
        val animal4 = SampleChildItem("Hamsters")
        val animal5 = SampleChildItem("Snakes")

        val animalList = arrayListOf(animal1, animal2, animal3, animal4, animal5)

        val pet = SampleParentItem("Pets", animalList)
        val animal = SampleParentItem("Animals", animalList)

        return arrayListOf(pet, animal)
    }
}
