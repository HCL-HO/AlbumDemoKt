/*
 *   Created by Eric Ho on 11/13/20 12:33 PM
 *   Copyright (c) 2020 . All rights reserved.
 *   Last modified 11/13/20 12:31 PM
 *   Email: clhoac@gmail.com
 */

package ecl.ho.keysocalbum.util

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

class ShowcaseViewModel(val dao: AlbumCollectionDao, application: Application) : ViewModel() {


    val collections: LiveData<List<AlbumCollectionVo>> = dao.getCollections()


}