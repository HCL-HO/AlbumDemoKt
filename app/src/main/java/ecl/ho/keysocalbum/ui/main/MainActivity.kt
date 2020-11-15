/*
 *   Created by Eric Ho on 11/15/20 9:51 AM
 *   Copyright (c) 2020 . All rights reserved.
 *   Last modified 11/15/20 9:41 AM
 *   Email: clhoac@gmail.com
 */

package ecl.ho.keysocalbum.ui.main

import android.content.Intent
import android.content.res.Configuration
import android.os.Bundle
import android.os.PersistableBundle
import androidx.appcompat.app.AppCompatActivity
import android.view.Menu
import android.view.MenuItem
import android.view.View.*
import androidx.lifecycle.ViewModelProvider
import com.google.android.material.appbar.AppBarLayout
import com.google.android.material.appbar.CollapsingToolbarLayout
import ecl.ho.keysocalbum.R
import ecl.ho.keysocalbum.database.AlbumDatabase
import ecl.ho.keysocalbum.dtos.AlbumDTO
import ecl.ho.keysocalbum.network.PARM_ENTITY
import ecl.ho.keysocalbum.network.PARM_TERM
import ecl.ho.keysocalbum.ui.bookmark.BookedMarkedActivity
import ecl.ho.keysocalbum.ui.albumrv.AlbumRVAdapter
import ecl.ho.keysocalbum.ui.albumrv.AlbumViewHolder
import ecl.ho.keysocalbum.util.DataConverter
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.content_main.*
import kotlinx.android.synthetic.main.vh_album_header.*

class MainActivity : AppCompatActivity() {

    private val TAG_CONFIGCHANGE: String = "CONFIGCAHNGE"
    private val ALBUM_NAME: CharSequence? = PARM_TERM.replace("+", " ") + " " + PARM_ENTITY
    lateinit var viewModel: AlbumListViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_main)
        setSupportActionBar(findViewById(R.id.toolbar))

        viewModel = getViewModel()
        val adapter = AlbumRVAdapter(object : AlbumViewHolder.Companion.OnClickListener {
            override fun bookmarkClicked(item: AlbumDTO, exist: Boolean) {
                val vo = DataConverter.albumDtoToAlbumVo(item)
                when (exist) {
                    true -> {
                        viewModel.deleteCollection(vo)
                    }
                    false -> {
                        viewModel.addToCollection(vo)
                    }
                }
            }
        })
        album_rv.adapter = adapter

        viewModel.albumsList.observe(this, {
            adapter.updateList(it)
        })

        viewModel.loading.observe(this, {
            isLoading(it)
        })

        viewModel.loadingError.observe(this, {
            album_fail_loading.visibility = if (it) VISIBLE else GONE
            album_rv.visibility = if (it) INVISIBLE else VISIBLE
            adapter.updateList(arrayListOf())
        })

        viewModel.count.observe(this, {
            tv_total.text = it
        })

        viewModel.collections.observe(this, {
            tv_collection_count.text = it.size.toString()
            adapter.updateCollections(it)
            adapter.notifyDataSetChanged()
        })

        setupHeader()

        setupCollapseBar()

        setupSwipeReferesh()

    }

    override fun onResume() {
        super.onResume()
        viewModel.getAlbumFromApi()
    }

    private fun setupSwipeReferesh() {
        album_swipe_refresh.setOnRefreshListener {
            album_swipe_refresh.isRefreshing = true
            viewModel.getAlbumFromApi().observe(this, {
                album_swipe_refresh.isRefreshing = false
            })
        }
    }

    private fun setupHeader() {
        album_title.text = ALBUM_NAME
        vh_header_collection_icon.setOnClickListener {
            val intent = Intent(this, BookedMarkedActivity::class.java)
            startActivity(intent)
        }
    }

    @JvmName("getViewModel1")
    private fun getViewModel(): AlbumListViewModel {
        val dao = AlbumDatabase.getInstance(application).albumCollectionDao
        val viewModelFactory = AlbumListViewModelFactory(dao, this.application)
        return ViewModelProvider(
            this, viewModelFactory
        ).get(AlbumListViewModel::class.java)

    }

    private fun setupCollapseBar() {
        var isShow = true
        var scrollRange = -1
        val appBarLayout = findViewById<AppBarLayout>(R.id.app_barlayout)
        val collapsingToolbarLayout = findViewById<CollapsingToolbarLayout>(R.id.collapsebar_layout)
        appBarLayout.addOnOffsetChangedListener(AppBarLayout.OnOffsetChangedListener { barLayout, verticalOffset ->
            if (scrollRange == -1) {
                scrollRange = barLayout?.totalScrollRange!!
            }
            if (scrollRange + verticalOffset == 0) {
                collapsingToolbarLayout.title = ALBUM_NAME
                isShow = true
            } else if (isShow) {
                collapsingToolbarLayout.title =
                    " " //careful there should a space between double quote otherwise it wont work
                isShow = false
            }
        })
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        // Inflate the menu; this adds items to the action bar if it is present.
        menuInflater.inflate(R.menu.menu_main, menu)
        val item = menu.findItem(R.id.action_bookmark)
        viewModel.bookmarkFilter.observe(this, {
            val res =
                if (it) R.drawable.ic_baseline_view_list_24 else R.drawable.ic_baseline_bookmark_24
            item.setIcon(res)
        })
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        return when (item.itemId) {
            R.id.action_bookmark -> {
                viewModel.toggleBookmarkFilter()
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }

    fun isLoading(loading: Boolean) {
        album_rv.visibility = if (loading) GONE else VISIBLE
        when (loading) {
            true -> album_loading.show()
            false -> album_loading.hide()
        }
    }

}


