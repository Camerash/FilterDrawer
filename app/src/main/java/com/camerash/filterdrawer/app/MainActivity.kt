package com.camerash.filterdrawer.app

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.LinearLayoutManager
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

        initFilter()
        initRecyclerView()
    }

    private fun initFilter() {
        val petFilterList = constructFilterItems()

        filterDrawer = DrawerBuilder<PetFilterCategory, PetFilter>(this)
                .displayToolbar(true)
                .setFilterControlClickListener(this)
                .addChildSelectListener(this)
                .withItems(petFilterList)
                .build()
    }

    private fun initRecyclerView() {
        val adapter = PetRecyclerAdapter(constructPetItems())
        adapter.bindFilterDrawer(filterDrawer)

        recycler_view.layoutManager = LinearLayoutManager(this)
        recycler_view.adapter = adapter
    }

    private fun constructFilterItems(): ArrayList<PetFilterCategory> {
        val animal1 = PetFilter(PetFilter.Kind.Cats)
        val animal2 = PetFilter(PetFilter.Kind.Dogs)
        val animal3 = PetFilter(PetFilter.Kind.Rabbits)
        val animal4 = PetFilter(PetFilter.Kind.Hamsters)
        val animal5 = PetFilter(PetFilter.Kind.Snakes)

        val pet = PetFilterCategory(PetFilterCategory.FilterType.Kind, R.drawable.round_pets_24, arrayListOf(animal1, animal2, animal3, animal4, animal5))

        val size1 = PetFilter(PetFilter.Size.Small)
        val size2 = PetFilter(PetFilter.Size.Medium)
        val size3 = PetFilter(PetFilter.Size.Large)

        val animal = PetFilterCategory(PetFilterCategory.FilterType.Size, R.drawable.round_size_24, arrayListOf(size1, size2, size3))

        return arrayListOf(pet, animal)
    }

    private fun constructPetItems(): ArrayList<Pet> {
        return arrayListOf(
                Pet("Pembroke Welsh Corgi", "https://upload.wikimedia.org/wikipedia/commons/thumb/2/2b/WelshCorgi.jpeg/482px-WelshCorgi.jpeg", PetFilter.Kind.Dogs, PetFilter.Size.Medium),
                Pet("British Shorthair Cat", "https://cdn1-www.cattime.com/assets/uploads/gallery/british-shorthair-cats-and-kittens/british-shorthair-cats-and-kittens-5.jpg", PetFilter.Kind.Cats, PetFilter.Size.Medium),
                Pet("Netherland Dwarf Rabbit", "https://cf.ltkcdn.net/small-pets/images/orig/204252-1697x1131-dwarf-rabbit_new.jpg", PetFilter.Kind.Rabbits, PetFilter.Size.Small)
        )
    }

    override fun onResetClick() {
        filterDrawer.resetFilter()
    }

    override fun onApplyClick() {
        filterDrawer.closeFilterDrawer()
    }

    override fun onChildSelect(parent: PetFilterCategory, child: Set<PetFilter>) {}

    override fun onChildDeselect(parent: PetFilterCategory, child: Set<PetFilter>) {}

    override fun onReset() {
        Log.d("reset", "true")
    }
}
