package com.camerash.filterdrawer.app

import android.graphics.drawable.Drawable
import android.os.Build
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import com.bumptech.glide.Glide
import com.bumptech.glide.request.target.DrawableImageViewTarget
import com.bumptech.glide.request.transition.Transition
import com.camerash.filterdrawer.R
import com.liuguangqiang.swipeback.SwipeBackLayout
import kotlinx.android.synthetic.main.activity_photo_viewing.*

class PhotoViewingActivity: AppCompatActivity() {

    companion object {
        const val PHOTO_URL_KEY = "photo"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_photo_viewing)
        image_view.setOnClickListener { onBackPressed() }
        swipe_dismiss_layout.setDragEdge(SwipeBackLayout.DragEdge.BOTTOM)

        val url = getPhotoUrl()
        if(url != null) {
            postponeTransition()
            Glide.with(this)
                    .load(url)
                    .into(object: DrawableImageViewTarget(image_view) {
                        override fun onResourceReady(resource: Drawable, transition: Transition<in Drawable>?) {
                            super.onResourceReady(resource, transition)
                            resumePostponedEnterTransition()
                        }
                    })

        } else {
            onBackPressed()
        }
    }

    private fun getPhotoUrl(): String? {
        val i = intent
        i ?: return null
        if(!i.hasExtra(PHOTO_URL_KEY)) return null
        return i.getStringExtra(PHOTO_URL_KEY)
    }

    private fun postponeTransition() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            supportPostponeEnterTransition()
        }
    }

    private fun resumePostponedEnterTransition() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            supportStartPostponedEnterTransition()
        }
    }

}