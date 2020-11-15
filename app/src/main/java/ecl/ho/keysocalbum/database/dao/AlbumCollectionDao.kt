/*
 *   Created by Eric Ho on 11/14/20 1:22 PM
 *   Copyright (c) 2020 . All rights reserved.
 *   Last modified 11/14/20 1:22 PM
 *   Email: clhoac@gmail.com
 */

package ecl.ho.keysocalbum.database.dao

import androidx.lifecycle.LiveData
import androidx.room.*
import ecl.ho.keysocalbum.dtos.AlbumDTO
import ecl.ho.keysocalbum.vos.AlbumCollectionVo

@Dao
interface AlbumCollectionDao {

    @Update
    suspend fun upsertAlbumCollection(vo: AlbumCollectionVo)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun addAlbumCollection(vo: AlbumCollectionVo): Long

    @Delete
    suspend fun deleteCollection(vo: AlbumCollectionVo)

    @Query("select collectionId from albumcollection")
    fun getCollectionIds(): LiveData<List<String>>

    @Query("select * from albumcollection")
    fun getCollections(): LiveData<List<AlbumCollectionVo>>
}
