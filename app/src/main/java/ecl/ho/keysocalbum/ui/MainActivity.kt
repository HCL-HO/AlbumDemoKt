/*
 *   Created by Eric Ho on 11/13/20 12:33 PM
 *   Copyright (c) 2020 . All rights reserved.
 *   Last modified 11/13/20 12:31 PM
 *   Email: clhoac@gmail.com
 */

package ecl.ho.keysocalbum.ui

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import android.view.Menu
import android.view.MenuItem
import android.view.View.GONE
import android.view.View.VISIBLE
import androidx.lifecycle.ViewModelProvider
import com.google.android.material.appbar.AppBarLayout
import com.google.android.material.appbar.CollapsingToolbarLayout
import ecl.ho.keysocalbum.R
import ecl.ho.keysocalbum.network.AlbumApi
import ecl.ho.keysocalbum.network.PARM_ENTITY
import ecl.ho.keysocalbum.network.PARM_TERM
import ecl.ho.keysocalbum.ui.albumrv.AlbumRVAdapter
import ecl.ho.keysocalbum.ui.albumrv.AlbumViewHolder
import kotlinx.android.synthetic.main.content_main.*
import kotlinx.android.synthetic.main.vh_album_header.*
import kotlinx.android.synthetic.main.vh_album_list_item.*

class MainActivity : AppCompatActivity() {

    private val ALBUM_NAME: CharSequence? = PARM_TERM.replace("+", " ") + " " + PARM_ENTITY

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setSupportActionBar(findViewById(R.id.toolbar))

        val viewModel: AlbumListViewModel = getViewModel()

        val adapter = AlbumRVAdapter(object : AlbumViewHolder.Companion.OnClickListener {
            override fun bookmarkClicked(posistion: Int) {
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
            album_rv.visibility = if (it) GONE else VISIBLE
        })

        viewModel.count.observe(this, {
            tv_total.text = it
        })

        setupHeader()

        setupCollapseBar()

    }

    private fun setupHeader() {
        album_title.text = ALBUM_NAME
        vh_header_collection_icon.setOnClickListener {
            val intent = Intent(this, BookedMarkedActivity::class.java)
            startActivity(intent)
        }
    }

    private fun getViewModel(): AlbumListViewModel {
        val viewModelFactory = AlbumListViewModelFactory(this.application)
        val viewModel =
            ViewModelProvider(
                this, viewModelFactory
            ).get(AlbumListViewModel::class.java)
        return viewModel

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
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        return when (item.itemId) {
            R.id.action_settings -> true
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


