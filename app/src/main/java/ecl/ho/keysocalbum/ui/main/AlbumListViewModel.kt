/*
 *   Created by Eric Ho on 11/15/20 11:15 AM
 *   Copyright (c) 2020 . All rights reserved.
 *   Last modified 11/15/20 9:51 AM
 *   Email: clhoac@gmail.com
 */

package ecl.ho.keysocalbum.ui.main

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
import ecl.ho.keysocalbum.util.DataConverter
import ecl.ho.keysocalbum.vos.AlbumCollectionVo
import kotlinx.coroutines.launch
import timber.log.Timber
import java.lang.Exception

class AlbumListViewModel(val dao: AlbumCollectionDao, val application: Application) : ViewModel() {

    var bookmarkFilter = MutableLiveData(false)

    var apiResp: ResAlbumApi? = null

    val albumsList = MutableLiveData<List<AlbumDTO>>()

    val loading = MutableLiveData<Boolean>()

    val loadingError = MutableLiveData<Boolean>()

    val count = MutableLiveData<String>()

    val collections: LiveData<List<AlbumCollectionVo>> = dao.getCollections()
    

    fun getAlbumFromApi(): LiveData<Boolean> {
        val isFinshed = MutableLiveData<Boolean>(false)
        val api = AlbumApi.retrofitService.getAlbumsAsync(PARM_TERM, PARM_ENTITY)
        viewModelScope.launch {
            try {
                bookmarkFilter.postValue(false)
                loading.postValue(true)
                loadingError.postValue(false)
                apiResp = api.await()
                count.postValue(apiResp?.resultCount)
                showAlbumList(apiResp, false)
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
        loadingError.postValue(false)
        val bookfilterOn = !bookmarkFilter.value!!
        showAlbumList(apiResp, bookfilterOn)
        bookmarkFilter.postValue(bookfilterOn)
    }

    private fun showAlbumList(apiResp: ResAlbumApi?, bookfilterOn: Boolean) {
        val list = apiResp?.results ?: arrayListOf()
        when (bookfilterOn) {
            true -> {
                val newList = DataConverter.albumVosToAlbumDtos(collections.value)
                albumsList.postValue(newList)
            }
            false -> {
                albumsList.postValue(list)
            }
        }
    }

    fun refresh() {
        showAlbumList(apiResp, false)
    }

    companion object {
        fun isContainCollection(
            collections: List<AlbumCollectionVo>?,
            collectionId: String?
        ): Boolean {
            collections?.forEach {
                if (it.collectionId == collectionId) {
                    return true
                }
            }
            return false
        }
    }

}