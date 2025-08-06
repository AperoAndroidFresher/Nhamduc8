package com.example.nhamngocduc.data.remote.api

import com.example.nhamngocduc.data.remote.model.SongDto
import com.example.nhamngocduc.util.K
import retrofit2.Response
import retrofit2.http.GET

interface SongApiService{
    @GET(K.END_POINT)
    suspend fun getSongData(): Response<List<SongDto>>
}