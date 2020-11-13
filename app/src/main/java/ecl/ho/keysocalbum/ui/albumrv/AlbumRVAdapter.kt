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
import ecl.ho.keysocalbum.models.AlbumDTO

class AlbumRVAdapter(val holderOnClickListener: AlbumViewHolder.Companion.OnClickListener) :
    RecyclerView.Adapter<AlbumViewHolder>() {

    var list: List<AlbumDTO> = arrayListOf()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AlbumViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val binding = VhAlbumListItemBinding.inflate(layoutInflater, parent, false)
        return AlbumViewHolder(binding)
    }

    override fun onBindViewHolder(holder: AlbumViewHolder, position: Int) {
        holder.bind(list[position], View.OnClickListener {
            holderOnClickListener.bookmarkClicked(position)
            list[position].bookmarked = !list[position].bookmarked
            notifyItemChanged(position)
        })
    }

    override fun getItemCount(): Int {
        return list.size
    }

    fun updateList(list: List<AlbumDTO>) {
        this.list = list
        notifyDataSetChanged()
    }


}