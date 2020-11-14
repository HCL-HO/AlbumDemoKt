/*
 *   Created by Eric Ho on 11/14/20 1:13 PM
 *   Copyright (c) 2020 . All rights reserved.
 *   Last modified 11/14/20 1:13 PM
 *   Email: clhoac@gmail.com
 */

package ecl.ho.keysocalbum.vos

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "AlbumCollection")
class AlbumCollectionVo(
    @ColumnInfo(name = "wrapperType")
    var wrapperType: String? = "",
    @ColumnInfo(name = "collectionType")
    var collectionType: String? = "",
    @ColumnInfo(name = "artistId")
    var artistId: String? = "",
    @PrimaryKey
    @ColumnInfo(name = "collectionId")
    var collectionId: String = "",
    @ColumnInfo(name = "amgArtistId")
    var amgArtistId: String? = "",
    @ColumnInfo(name = "artistName")
    var artistName: String? = "",
    @ColumnInfo(name = "collectionName")
    var collectionName: String? = "",
    @ColumnInfo(name = "collectionCensoredName")
    var collectionCensoredName: String? = "",
    @ColumnInfo(name = "artistViewUrl")
    var artistViewUrl: String? = "",
    @ColumnInfo(name = "collectionViewUrl")
    var collectionViewUrl: String? = "",
    @ColumnInfo(name = "artworkUrl60")
    var artworkUrl60: String? = "",
    @ColumnInfo(name = "artworkUrl100")
    var artworkUrl100: String? = "",
    @ColumnInfo(name = "collectionPrice")
    var collectionPrice: String? = "",
    @ColumnInfo(name = "collectionExplicitness")
    var collectionExplicitness: String? = "",
    @ColumnInfo(name = "trackCount")
    var trackCount: String? = "",
    @ColumnInfo(name = "copyright")
    var copyright: String? = "",
    @ColumnInfo(name = "country")
    var country: String? = "",
    @ColumnInfo(name = "currency")
    var currency: String? = "",
    @ColumnInfo(name = "releaseDate")
    var releaseDate: String? = "",
    @ColumnInfo(name = "primaryGenreName")
    var primaryGenreName: String? = "",
)