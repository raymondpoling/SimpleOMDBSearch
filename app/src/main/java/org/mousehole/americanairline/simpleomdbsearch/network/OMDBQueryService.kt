package org.mousehole.americanairline.simpleomdbsearch.network

import io.reactivex.Single
import org.mousehole.americanairline.simpleomdbsearch.model.episode.EpisodeResponse
import org.mousehole.americanairline.simpleomdbsearch.model.movie.MovieResponse
import org.mousehole.americanairline.simpleomdbsearch.model.season.SeasonResponse
import org.mousehole.americanairline.simpleomdbsearch.model.series.SeriesResponse
import org.mousehole.americanairline.simpleomdbsearch.util.Constants.EPISODE_PARAM
import org.mousehole.americanairline.simpleomdbsearch.util.Constants.MOVIE_QUERY_PATH
import org.mousehole.americanairline.simpleomdbsearch.util.Constants.SEARCH_PARAM
import org.mousehole.americanairline.simpleomdbsearch.util.Constants.SEASON_PARAM
import org.mousehole.americanairline.simpleomdbsearch.util.Constants.SERIES_QUERY_PATH
import retrofit2.http.GET
import retrofit2.http.Query

interface OMDBQueryService {

    @GET(SERIES_QUERY_PATH)
    fun getSeries(@Query(SEARCH_PARAM) seriesName : String) : Single<SeriesResponse>

    @GET(SERIES_QUERY_PATH)
    fun getSeason(@Query(SEARCH_PARAM) seriesName : String,
                  @Query(SEASON_PARAM) season : Int) : Single<SeasonResponse>

    @GET(SERIES_QUERY_PATH)
    fun getEpisode(@Query(SEARCH_PARAM) seriesName : String,
                   @Query(SEASON_PARAM) season : Int,
                   @Query(EPISODE_PARAM) episode : Int) : Single<EpisodeResponse>

    @GET(MOVIE_QUERY_PATH)
    fun getMovie(@Query(SEARCH_PARAM) movieName : String) : Single<MovieResponse>
}