package com.schaefer.mymovies.di

import com.schaefer.mymovies.data.api.TvMazeAPI
import com.schaefer.mymovies.data.repository.EpisodeRepositoryImpl
import com.schaefer.mymovies.data.repository.ShowsRepositoryImpl
import com.schaefer.mymovies.domain.mapper.ListDomainShowMapper
import com.schaefer.mymovies.domain.mapper.ListEpisodeDomainMapper
import com.schaefer.mymovies.domain.mapper.SearchResponseItemMapper
import com.schaefer.mymovies.domain.repository.EpisodeRepository
import com.schaefer.mymovies.domain.repository.ShowsRepository
import com.schaefer.mymovies.domain.usecase.GetEpisodeListUseCase
import com.schaefer.mymovies.domain.usecase.GetSearchShowsUseCase
import com.schaefer.mymovies.domain.usecase.GetShowsUseCase
import com.schaefer.mymovies.presentation.mapper.ListEpisodeMapper
import com.schaefer.mymovies.presentation.mapper.ListShowMapper
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ApplicationComponent
import kotlinx.serialization.ExperimentalSerializationApi

@ExperimentalSerializationApi
@Module
@InstallIn(ApplicationComponent::class)
class RepositoryModule {
    @Provides
    fun providesShowsRepository(
        service: TvMazeAPI,
        mapperDomain: ListDomainShowMapper,
        mapperSearch: SearchResponseItemMapper
    ): ShowsRepository = ShowsRepositoryImpl(service, mapperDomain, mapperSearch)

    @Provides
    fun providesEpisodeRepository(
        service: TvMazeAPI,
        episodeDomainMapper: ListEpisodeDomainMapper
    ): EpisodeRepository = EpisodeRepositoryImpl(service, episodeDomainMapper)

    @Provides
    fun providesGetShowUseCase(repository: ShowsRepository, mapper: ListShowMapper) =
        GetShowsUseCase(repository, mapper)

    @Provides
    fun providesGetSearchShowsUseCase(repository: ShowsRepository, mapper: ListShowMapper) =
        GetSearchShowsUseCase(repository, mapper)

    @Provides
    fun providesGetEpisodeListUseCase(repository: EpisodeRepository, mapper: ListEpisodeMapper) =
        GetEpisodeListUseCase(repository, mapper)
}