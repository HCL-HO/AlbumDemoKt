/*
 *   Created by Eric Ho on 11/13/20 12:33 PM
 *   Copyright (c) 2020 . All rights reserved.
 *   Last modified 11/13/20 12:31 PM
 *   Email: clhoac@gmail.com
 */

package ecl.ho.keysocalbum.ui.albumrv

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import ecl.ho.keysocalbum.databinding.VhAlbumListItemBinding
import ecl.ho.keysocalbum.dtos.AlbumDTO
import ecl.ho.keysocalbum.ui.main.AlbumListViewModel
import ecl.ho.keysocalbum.vos.AlbumCollectionVo

class AlbumRVAdapter(val holderOnClickListener: AlbumViewHolder.Companion.OnClickListener) :
    RecyclerView.Adapter<AlbumViewHolder>() {

    var list: List<AlbumDTO> = arrayListOf()
    var collections: List<AlbumCollectionVo> = arrayListOf()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AlbumViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val binding = VhAlbumListItemBinding.inflate(layoutInflater, parent, false)
        return AlbumViewHolder(binding)
    }

    override fun onBindViewHolder(holder: AlbumViewHolder, position: Int) {
        val item = list[position]
        val bookmarked = AlbumListViewModel.isContainCollection(collections, item.collectionId)
        item.bookmarked = bookmarked
        holder.bind(item, View.OnClickListener {
            holderOnClickListener.bookmarkClicked(list[position], bookmarked)
        })
    }

    override fun getItemCount(): Int {
        return list.size
    }

    fun updateList(list: List<AlbumDTO>) {
        this.list = list
        notifyDataSetChanged()
    }

    fun updateCollections(it: List<AlbumCollectionVo>) {
        this.collections = it
    }


}