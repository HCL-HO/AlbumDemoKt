/*
 *   Created by Eric Ho on 11/15/20 9:51 AM
 *   Copyright (c) 2020 . All rights reserved.
 *   Last modified 11/14/20 3:51 PM
 *   Email: clhoac@gmail.com
 */

package ecl.ho.keysocalbum.util

import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import ecl.ho.keysocalbum.database.dao.AlbumCollectionDao

class ShowcaseModelFactory(
    private val dao: AlbumCollectionDao,
    private val application: Application
) : ViewModelProvider.Factory {
    @Suppress("unchecked_cast")
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(ShowcaseViewModel::class.java)) {
            return ShowcaseViewModel(dao, application) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}