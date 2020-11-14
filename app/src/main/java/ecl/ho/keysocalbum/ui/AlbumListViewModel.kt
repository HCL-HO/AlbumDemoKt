/*
 *   Created by Eric Ho on 11/13/20 12:33 PM
 *   Copyright (c) 2020 . All rights reserved.
 *   Last modified 11/13/20 12:31 PM
 *   Email: clhoac@gmail.com
 */

package ecl.ho.keysocalbum.ui

import android.app.Application
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import ecl.ho.keysocalbum.database.dao.AlbumCollectionDao
import ecl.ho.keysocalbum.dtos.AlbumDTO
import ecl.ho.keysocalbum.dtos.ResAlbumApi
import ecl.ho.keysocalbum.network.AlbumApi
import ecl.ho.keysocalbum.network.PARM_ENTITY
import ecl.ho.keysocalbum.network.PARM_TERM
import ecl.ho.keysocalbum.vos.AlbumCollectionVo
import kotlinx.coroutines.launch
import timber.log.Timber
import java.lang.Exception

class AlbumListViewModel(val dao: AlbumCollectionDao, application: Application) : ViewModel() {

    var bookmarkFilter = false

    var apiResp: ResAlbumApi? = null

    val albumsList = MutableLiveData<List<AlbumDTO>>()

    val loading = MutableLiveData<Boolean>()

    val loadingError = MutableLiveData<Boolean>()

    val count = MutableLiveData<String>()

    val collections: LiveData<List<String>> = dao.getCollectionIds()


    init {
        getAlbumFromApi()
    }

    fun getAlbumFromApi(): LiveData<Boolean> {
        val isFinshed = MutableLiveData<Boolean>(false)
        val api = AlbumApi.retrofitService.getAlbumsAsync(PARM_TERM, PARM_ENTITY)
        viewModelScope.launch {
            try {
                loading.postValue(true)
                loadingError.postValue(false)
                apiResp = api.await()
                val list = apiResp?.results
                count.postValue(apiResp?.resultCount)
                showAlbumList(apiResp)
            } catch (ex: Exception) {
                Timber.e(ex.message.toString())
                loadingError.postValue(true)
            } finally {
                loading.postValue(false)
                isFinshed.postValue(true)
            }
        }
        return isFinshed
    }

    fun addToCollection(vo: AlbumCollectionVo) {
        viewModelScope.launch {
            if (dao.addAlbumCollection(vo) < 0) {
                dao.upsertAlbumCollection(vo)
            }
        }
    }

    fun deleteCollection(vo: AlbumCollectionVo) {
        viewModelScope.launch {
            dao.deleteCollection(vo)
        }
    }

    fun toggleBookmarkFilter() {
        bookmarkFilter = !bookmarkFilter
        showAlbumList(apiResp)
    }

    private fun showAlbumList(apiResp: ResAlbumApi?) {
        apiResp?.let { resAlbumApi ->
            val list = resAlbumApi.results
            when (bookmarkFilter) {
                true -> {
                    val newList = arrayListOf<AlbumDTO>()
                    val collections = collections.value
                    list.forEach {
                        if (collections != null && collections.contains(it.collectionId)) {
                            newList.add(it)
                        }
                    }
                    albumsList.postValue(newList)
                }
                false -> {
                    albumsList.postValue(list)
                }
            }
        }
    }

}