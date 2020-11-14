/*
 *   Created by Eric Ho on 11/13/20 12:33 PM
 *   Copyright (c) 2020 . All rights reserved.
 *   Last modified 11/13/20 12:31 PM
 *   Email: clhoac@gmail.com
 */

package ecl.ho.keysocalbum.dtos

data class AlbumDTO(
    var wrapperType: String? = "",
    var collectionType: String? = "",
    var artistId: String? = "",
    var collectionId: String? = "",
    var artistName: String? = "",
    var collectionName: String? = "",
    var collectionCensoredName: String? = "",
    var artistViewUrl: String? = "",
    var collectionViewUrl: String? = "",
    var artworkUrl60: String? = "",
    var artworkUrl100: String? = "",
    var collectionPrice: String? = "",
    var collectionExplicitness: String? = "",
    var trackCount: String? = "",
    var copyright: String? = "",
    var country: String? = "",
    var currency: String? = "",
    var releaseDate: String? = "",
    var primaryGenreName: String? = "",
    var bookmarked: Boolean = false
)