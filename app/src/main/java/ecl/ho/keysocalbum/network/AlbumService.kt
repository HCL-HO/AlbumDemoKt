/*
 *   Created by Eric Ho on 11/13/20 12:33 PM
 *   Copyright (c) 2020 . All rights reserved.
 *   Last modified 11/13/20 12:31 PM
 *   Email: clhoac@gmail.com
 */

package ecl.ho.keysocalbum.network

import com.jakewharton.retrofit2.adapter.kotlin.coroutines.CoroutineCallAdapterFactory
import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import ecl.ho.keysocalbum.models.ResAlbumApi
import kotlinx.coroutines.Deferred
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import retrofit2.http.GET
import retrofit2.http.Query


private const val BASE_URL = "https://itunes.apple.com/"

const val PARM_TERM = "jack+johnson"
const val PARM_ENTITY = "album"

private val moshi = Moshi.Builder()
    .add(KotlinJsonAdapterFactory())
    .build()

private val retrofit = Retrofit.Builder()
    .addConverterFactory(MoshiConverterFactory.create(moshi))
    .addCallAdapterFactory(CoroutineCallAdapterFactory())
    .baseUrl(BASE_URL)
    .build()

interface AlbumApiService {
    @GET("search")
    fun getAlbumsAsync(@Query("term") term: String, @Query("entity") entity: String):
            Deferred<ResAlbumApi>
}

object AlbumApi {
    val retrofitService: AlbumApiService by lazy { retrofit.create(AlbumApiService::class.java) }
}