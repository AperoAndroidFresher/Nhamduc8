package com.example.nhamngocduc.domain.repository

import com.example.nhamngocduc.domain.model.AlbumDomain
import com.example.nhamngocduc.domain.model.ArtistDomain
import com.example.nhamngocduc.domain.model.TrackDomain

interface HomeRepository {
    suspend fun getTopAlbums(): Result<List<AlbumDomain>>
    suspend fun getTopArtists(): Result<List<ArtistDomain>>
    suspend fun getTopTracks(): Result<List<TrackDomain>>
    suspend fun getAllHomeData(): Result<Triple<List<AlbumDomain>, List<ArtistDomain>, List<TrackDomain>>>
}