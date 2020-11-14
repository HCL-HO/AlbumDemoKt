/*
 *   Created by Eric Ho on 11/13/20 1:44 PM
 *   Copyright (c) 2020 . All rights reserved.
 *   Last modified 11/13/20 1:44 PM
 *   Email: clhoac@gmail.com
 */

package ecl.ho.keysocalbum.ui

import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import ecl.ho.keysocalbum.database.dao.AlbumCollectionDao

class AlbumListViewModelFactory(
    private val dao: AlbumCollectionDao,
    private val application: Application
) : ViewModelProvider.Factory {
    @Suppress("unchecked_cast")
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(AlbumListViewModel::class.java)) {
            return AlbumListViewModel(dao, application) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}