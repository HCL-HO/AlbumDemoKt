/*
 *   Created by Eric Ho on 11/13/20 12:33 PM
 *   Copyright (c) 2020 . All rights reserved.
 *   Last modified 11/13/20 12:32 PM
 *   Email: clhoac@gmail.com
 */

package ecl.ho.keysocalbum.ui.albumrv

import android.view.View
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import ecl.ho.keysocalbum.R
import ecl.ho.keysocalbum.databinding.VhAlbumListItemBinding
import ecl.ho.keysocalbum.models.AlbumDTO

class AlbumViewHolder(
    private val binding: VhAlbumListItemBinding
) :
    RecyclerView.ViewHolder(binding.root) {

    val icon = binding.vhIcons
    val albumName = binding.vhTitleTv
    val artist = binding.vhArtist
    val price = binding.vhPrice
    val releaseDate = binding.vhReleaseDate
    val country = binding.vhCountry
    val bookmark = binding.vhBookmark

    val RES_MARKED = R.drawable.ic_baseline_bookmark_24
    val RES_UNMARKED = R.drawable.ic_baseline_bookmark_border_24

    fun bind(albumDTO: AlbumDTO, onClickListener: View.OnClickListener) {
        albumName.text = albumDTO.collectionName

        val option = RequestOptions()
            .fitCenter()
            .placeholder(R.drawable.ic_baseline_cloud_download_24)

        Glide
            .with(binding.root)
            .load(albumDTO.artworkUrl100)
            .apply(option)
            .into(icon)

        artist.text = albumDTO.artistName

        releaseDate.text = albumDTO.releaseDate?.substring(0, 10)

        country.text = albumDTO.country

        price.text = albumDTO.collectionPrice

        bookmark.setOnClickListener(onClickListener)

        when (albumDTO.bookmarked) {
            true -> bookmark.setImageResource(RES_MARKED)
            false -> bookmark.setImageResource(RES_UNMARKED)
        }
    }

    companion object {
        interface OnClickListener {
            fun bookmarkClicked(posistion: Int)
        }
    }

}