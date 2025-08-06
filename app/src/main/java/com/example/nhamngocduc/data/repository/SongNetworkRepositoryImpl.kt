package com.example.nhamngocduc.data.repository

import com.example.nhamngocduc.data.remote.api.SongApiService
import com.example.nhamngocduc.data.remote.mapper.SongDtoMapper
import com.example.nhamngocduc.domain.model.Song
import com.example.nhamngocduc.domain.repository.SongNetworkRepository
class SongNetworkRepositoryImpl(
    private val songApiService: SongApiService,
    private val songDtoMapper: SongDtoMapper,
) : SongNetworkRepository {
    override suspend fun getSongsResult(): Result<List<Song>> {
        return try {
            val response = songApiService.getSongData()

            if (response.isSuccessful) {
                val songDtos = response.body()
                if (songDtos != null) {
                    // Assuming your mapper function is mapToDomain
                    val songs = songDtos.map { songDto -> songDtoMapper.mapFromEntity(songDto) }
                    Result.success(songs)
                } else {
                    Result.failure(Exception("API returned a successful response with an empty body."))
                }
            } else {
                val errorBody = response.errorBody()?.string()
                val errorMessage = "API error: ${response.code()} - $errorBody"
                Result.failure(Exception(errorMessage))
            }
        } catch (e: Exception) {
            Result.failure(e)
        }
    }
}