package com.schaefer.mymovies.data.repository

import com.schaefer.mymovies.core.Mapper
import com.schaefer.mymovies.data.api.TvMazeAPI
import com.schaefer.mymovies.data.model.EpisodeResponse
import com.schaefer.mymovies.domain.model.ListEpisodeDomain
import com.schaefer.mymovies.domain.repository.EpisodeRepository
import io.reactivex.rxjava3.core.Single

class EpisodeRepositoryImpl(
    private val service: TvMazeAPI,
    private val episodeDomainMapper: Mapper<EpisodeResponse, ListEpisodeDomain>
) : EpisodeRepository {
    override fun getEpisodeList(showId: Int): Single<ListEpisodeDomain> {
        return service.getEpisodeList(showId).map { episodeDomainMapper.map(it) }
    }
}