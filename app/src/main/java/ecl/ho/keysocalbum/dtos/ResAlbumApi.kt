/*
 *   Created by Eric Ho on 11/14/20 1:13 PM
 *   Copyright (c) 2020 . All rights reserved.
 *   Last modified 11/13/20 12:33 PM
 *   Email: clhoac@gmail.com
 */

package ecl.ho.keysocalbum.dtos

import com.squareup.moshi.Json

data class ResAlbumApi(
    @Json(name = "resultCount")
    val resultCount: String,
    @Json(name = "results")
    val results: List<AlbumDTO>
)