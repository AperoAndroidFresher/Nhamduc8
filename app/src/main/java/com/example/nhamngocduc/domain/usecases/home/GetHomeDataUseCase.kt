package com.example.nhamngocduc.domain.usecases.home

import com.example.nhamngocduc.domain.model.AlbumDomain
import com.example.nhamngocduc.domain.model.ArtistDomain
import com.example.nhamngocduc.domain.model.TrackDomain
import com.example.nhamngocduc.domain.repository.HomeRepository

class GetHomeDataUseCase(private val homeRepository: HomeRepository) {
    suspend operator fun invoke(): Result<Triple<List<AlbumDomain>, List<ArtistDomain>, List<TrackDomain>>> {
        return homeRepository.getAllHomeData()
    }
}

