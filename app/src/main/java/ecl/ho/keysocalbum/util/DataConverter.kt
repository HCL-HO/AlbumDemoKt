/*
 *   Created by Eric Ho on 11/14/20 1:16 PM
 *   Copyright (c) 2020 . All rights reserved.
 *   Last modified 11/14/20 1:16 PM
 *   Email: clhoac@gmail.com
 */

package ecl.ho.keysocalbum.util

import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import ecl.ho.keysocalbum.dtos.AlbumDTO
import ecl.ho.keysocalbum.vos.AlbumCollectionVo

class DataConverter {
    companion object {
        fun albumDtoToAlbumVo(albumDTO: AlbumDTO): AlbumCollectionVo {
            val gson = Gson()
            val dtoString = gson.toJson(albumDTO)
            val vo = gson.fromJson(dtoString, AlbumCollectionVo::class.java)
            return vo
        }

        fun albumVosToAlbumDtos(vos: List<AlbumCollectionVo>?): List<AlbumDTO> {
            val gson = Gson()
            val dtoString = gson.toJson(vos)
            return gson.fromJson(dtoString, object : TypeToken<List<AlbumDTO>>() {}.type)
        }
    }
}