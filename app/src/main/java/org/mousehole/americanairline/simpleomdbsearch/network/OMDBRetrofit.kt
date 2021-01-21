package org.mousehole.americanairline.simpleomdbsearch.network

import io.reactivex.Single
import org.mousehole.americanairline.simpleomdbsearch.model.movie.MovieResponse
import org.mousehole.americanairline.simpleomdbsearch.model.season.SeasonResponse
import org.mousehole.americanairline.simpleomdbsearch.model.series.SeriesResponse
import org.mousehole.americanairline.simpleomdbsearch.util.Constants.BASE_URL
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory

object OMDBRetrofit {
    private val retrofit : Retrofit =
        Retrofit
            .Builder()
            .baseUrl(BASE_URL)
            .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
            .addConverterFactory(GsonConverterFactory.create())
            .build()

    private val omdbQueryService = retrofit.create(OMDBQueryService::class.java)

    fun getSeries(seriesName : String) : Single<SeriesResponse> =
        omdbQueryService.getSeries(seriesName)

    fun getSeason(seriesName : String, season : String) : Single<SeasonResponse> =
        omdbQueryService.getSeason(seriesName, season)

    fun getMovie(movieName : String) : Single<MovieResponse> =
            omdbQueryService.getMovie(movieName)
}