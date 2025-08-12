package com.example.nhamngocduc.data.repository

import android.util.Log
import com.example.nhamngocduc.data.remote.api.HomeApiService
import com.example.nhamngocduc.data.remote.mapper.AlbumMapper
import com.example.nhamngocduc.data.remote.mapper.ArtistMapper
import com.example.nhamngocduc.data.remote.mapper.TrackMapper
import com.example.nhamngocduc.domain.model.AlbumDomain
import com.example.nhamngocduc.domain.model.ArtistDomain
import com.example.nhamngocduc.domain.model.TrackDomain
import com.example.nhamngocduc.domain.repository.HomeRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.withContext

class HomeRepositoryImpl(
    private val homeApiService: HomeApiService,
    private val albumMapper: AlbumMapper,
    private val artistMapper: ArtistMapper,
    private val trackMapper: TrackMapper
) : HomeRepository {

    override suspend fun getTopAlbums(): Result<List<AlbumDomain>> {
        return withContext(Dispatchers.IO) {
            try {
                val response = homeApiService.getTopAlbums()

                if (response.isSuccessful && response.body() != null) {
                    val albumDtos = response.body()?.topalbums?.album ?: emptyList()
                    val mappedAlbums = albumDtos.map { albumMapper.mapFromEntity(it) }
                    Result.success(mappedAlbums)
                } else {
                    val errorBody = response.errorBody()?.string() ?: "Unknown error"
                    Result.failure(Exception("API Error: ${response.code()} - $errorBody"))
                }
            } catch (e: Exception) {
                Result.failure(e)
            }
        }
    }

    override suspend fun getTopArtists(): Result<List<ArtistDomain>> {
        return withContext(Dispatchers.IO) {
            try {
                val response = homeApiService.getTopArtists()
                if (response.isSuccessful && response.body() != null) {
                    val artistDtos = response.body()?.topArtists?.artist ?: emptyList()
                    val mappedArtists = artistDtos.map { artistMapper.mapFromEntity(it) }
                    Result.success(mappedArtists)
                } else {
                    val errorBody = response.errorBody()?.string() ?: "Unknown error"
                    Result.failure(Exception("API Error: ${response.code()} - $errorBody"))
                }
            } catch (e: Exception) {
                Result.failure(e)
            }
        }
    }

    override suspend fun getTopTracks(): Result<List<TrackDomain>> {
        return withContext(Dispatchers.IO) {
            try {
                val response = homeApiService.getTopTracks()
                if (response.isSuccessful && response.body() != null) {
                    val trackDtos = response.body()?.topTracks?.track ?: emptyList()
                    val mappedTracks = trackDtos.map { trackMapper.mapFromEntity(it) }
                        Result.success(mappedTracks)
                } else {
                    val errorBody = response.errorBody()?.string() ?: "Unknown error"
                    Result.failure(Exception("API Error: ${response.code()} - $errorBody"))
                }
            } catch (e: Exception) {
                Result.failure(e)
            }
        }
    }

    override suspend fun getAllHomeData(): Result<Triple<List<AlbumDomain>, List<ArtistDomain>, List<TrackDomain>>> {
        return coroutineScope {
            val albumsDeferred = async { getTopAlbums() }
            val artistsDeferred = async { getTopArtists() }
            val tracksDeferred = async { getTopTracks() }

            try {
                val albums = albumsDeferred.await().getOrThrow()
                val artists = artistsDeferred.await().getOrThrow()
                val tracks = tracksDeferred.await().getOrThrow()

                Result.success(Triple(albums, artists, tracks))
            } catch (e: Exception) {
                Log.e("HomeRepositoryImpl", "Error fetching home data", e)
                Result.failure(e)
            }
        }
    }
}