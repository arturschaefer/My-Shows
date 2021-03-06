package com.schaefer.mymovies.presentation.mapper

import com.schaefer.mymovies.core.Mapper
import com.schaefer.mymovies.domain.model.LinksDomain
import com.schaefer.mymovies.presentation.model.Links

class LinksMapper(
    private val previousEpisodeMapper: PreviousEpisodeMapper,
    private val selfMapper: SelfMapper
) : Mapper<LinksDomain, Links> {
    override fun map(source: LinksDomain): Links {
        return Links(
            self = source.selfDomain?.let { selfMapper.map(it) },
            previousEpisode = source.previousEpisodeDomain?.let { previousEpisodeMapper.map(it) }
        )
    }
}