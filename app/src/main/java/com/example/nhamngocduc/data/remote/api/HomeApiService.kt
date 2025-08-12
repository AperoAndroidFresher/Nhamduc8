package com.example.nhamngocduc.data.remote.api

import com.example.nhamngocduc.data.remote.model.album.TopAlbumDto
import com.example.nhamngocduc.data.remote.model.artist.TopArtistDto
import com.example.nhamngocduc.data.remote.model.track.TopTracksDto
import com.example.nhamngocduc.util.K
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface HomeApiService {
    @GET(K.HOME_END_POINT)
    suspend fun getTopAlbums(
        @Query("method") method: String = "artist.getTopAlbums",
        @Query("mbid") mbid: String = "f9b593e6-4503-414c-99a0-46595ecd2e23",
        @Query("api_key") apiKey: String = "e65449d181214f936368984d4f4d4ae8",
        @Query("format") format: String = "json"
    ): Response<TopAlbumDto>

    @GET(K.HOME_END_POINT)
    suspend fun getTopArtists(
        @Query("method") method: String = "chart.gettopartists",
        @Query("api_key") apiKey: String = "e65449d181214f936368984d4f4d4ae8",
        @Query("format") format: String = "json"
    ): Response<TopArtistDto>

    @GET(K.HOME_END_POINT)
    suspend fun getTopTracks(
        @Query("method") method: String = "artist.getTopTracks",
        @Query("mbid") mbid: String = "f9b593e6-4503-414c-99a0-46595ecd2e23",
        @Query("api_key") apiKey: String = "e65449d181214f936368984d4f4d4ae8",
        @Query("format") format: String = "json"
    ): Response<TopTracksDto>
}
