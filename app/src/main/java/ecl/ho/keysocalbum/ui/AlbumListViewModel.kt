/*
 *   Created by Eric Ho on 11/13/20 12:33 PM
 *   Copyright (c) 2020 . All rights reserved.
 *   Last modified 11/13/20 12:31 PM
 *   Email: clhoac@gmail.com
 */

package ecl.ho.keysocalbum.ui

import android.app.Application
import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import ecl.ho.keysocalbum.models.AlbumDTO
import ecl.ho.keysocalbum.network.AlbumApi
import ecl.ho.keysocalbum.network.PARM_ENTITY
import ecl.ho.keysocalbum.network.PARM_TERM
import kotlinx.coroutines.launch
import timber.log.Timber
import java.lang.Exception

class AlbumListViewModel(application: Application) : ViewModel() {

    val albumsList = MutableLiveData<List<AlbumDTO>>()

    val loading = MutableLiveData<Boolean>()

    val loadingError = MutableLiveData<Boolean>()

    val count = MutableLiveData<String>()

    init {
        getAlbumFromApi()
    }

    fun getAlbumFromApi() {
        val api = AlbumApi.retrofitService.getAlbumsAsync(PARM_TERM, PARM_ENTITY)
        viewModelScope.launch {
            try {
                loading.postValue(true)
                loadingError.postValue(false)
                val resp = api.await()
                val list = resp.results
                albumsList.postValue(list)
                count.postValue(resp.resultCount)
            } catch (ex: Exception) {
                Timber.e(ex.message.toString())
                loadingError.postValue(true)
            } finally {
                loading.postValue(false)
            }

        }
    }

}