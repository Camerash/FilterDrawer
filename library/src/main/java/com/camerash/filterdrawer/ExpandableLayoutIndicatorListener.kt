package com.camerash.filterdrawer

import android.widget.ImageView
import net.cachapa.expandablelayout.ExpandableLayout

/**
 * Used in conjunction with ExpansionLayout for rotation animation of the arrow indicator
 *
 * @author Camerash
 * @param iv ImageView for the animation to apply on
 * @param maxDegree Maximum rotation degree for the ImageView animation to turn in the ClockWise direction
 */
class ExpandableLayoutIndicatorListener(private val iv: ImageView, private val maxDegree: Int): ExpandableLayout.OnExpansionUpdateListener {
    override fun onExpansionUpdate(expansionFraction: Float, state: Int) {
        iv.rotation = expansionFraction * maxDegree
    }
}