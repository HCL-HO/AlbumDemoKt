/*
 *   Created by Eric Ho on 11/15/20 9:57 AM
 *   Copyright (c) 2020 . All rights reserved.
 *   Last modified 11/15/20 9:51 AM
 *   Email: clhoac@gmail.com
 */

package ecl.ho.keysocalbum.ui.bookmark

import android.R.attr.path
import android.app.ActionBar
import android.content.res.Configuration
import android.graphics.Bitmap
import android.graphics.drawable.Drawable
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.bumptech.glide.request.target.CustomTarget
import com.bumptech.glide.request.target.SimpleTarget
import com.bumptech.glide.request.transition.Transition
import ecl.ho.keysocalbum.R
import ecl.ho.keysocalbum.database.AlbumDatabase
import ecl.ho.keysocalbum.util.FastBlur
import ecl.ho.keysocalbum.util.ShowcaseModelFactory
import ecl.ho.keysocalbum.util.ShowcaseViewModel
import github.hellocsl.layoutmanager.gallery.GalleryLayoutManager
import kotlinx.android.synthetic.main.activity_booked_marked.*


class BookedMarkedActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_booked_marked)

        setupGallery()
    }


    private fun setupGallery() {
        val layoutManager = GalleryLayoutManager(GalleryLayoutManager.HORIZONTAL)
        layoutManager.attach(showcase_rv, 30)
        layoutManager.setItemTransformer(CurveTransformer())
//        showcase_rv.setFlingAble(false)
        //        layoutManager.attach(mPagerRecycleView);

        val cards: MutableList<ImageCardAdapter.CardItem> = mutableListOf()
        getViewModel().collections.observe(this, { list ->
            list.forEach { albumCollectionVo ->
                albumCollectionVo.artworkUrl100?.let {
                    val card = ImageCardAdapter.CardItem(it, albumCollectionVo.collectionName ?: "")
                    cards.add(card)
                }
            }
            layoutManager.setOnItemSelectedListener { _, _, position ->

                val option = RequestOptions()
                    .fitCenter()

                Glide.with(this)
                    .asBitmap()
                    .load(list[position].artworkUrl100)
                    .apply(option)
                    .into(object : CustomTarget<Bitmap>() {
                        override fun onResourceReady(
                            resource: Bitmap,
                            transition: Transition<in Bitmap>?
                        ) {
                            showcase_bg.setImageBitmap(FastBlur.doBlur(resource, 20, false))
                        }

                        override fun onLoadCleared(placeholder: Drawable?) {
                            showcase_bg.setImageDrawable(placeholder)
                        }

                    })
            }
            val displayMetrics = resources.displayMetrics

            val length =
                if (displayMetrics.widthPixels > displayMetrics.heightPixels) (displayMetrics.heightPixels * 0.7f).toInt()
                else (displayMetrics.widthPixels * 0.7f).toInt()

            showcase_rv.adapter = ImageCardAdapter(
                cards,
                length,
                length
            )

        })
    }

    fun getViewModel(): ShowcaseViewModel {
        val dao = AlbumDatabase.getInstance(application).albumCollectionDao
        val viewModelFactory = ShowcaseModelFactory(dao, this.application)
        return ViewModelProvider(
            this, viewModelFactory
        ).get(ShowcaseViewModel::class.java)
    }

}