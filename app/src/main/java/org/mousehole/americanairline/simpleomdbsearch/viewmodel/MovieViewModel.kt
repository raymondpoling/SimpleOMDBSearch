package org.mousehole.americanairline.simpleomdbsearch.viewmodel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers
import org.mousehole.americanairline.simpleomdbsearch.model.movie.MovieResponse
import org.mousehole.americanairline.simpleomdbsearch.model.series.SeriesResponse
import org.mousehole.americanairline.simpleomdbsearch.network.OMDBRetrofit

class MovieViewModel : ViewModel() {

    private val movieCompositeDisposable = CompositeDisposable()

    private val movieLiveData: MutableLiveData<MovieResponse> = MutableLiveData()

    fun getMovieResult(movieName : String) : LiveData<MovieResponse> {
        movieCompositeDisposable.add(
            OMDBRetrofit.getMovie(movieName)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribe ({
                    movieLiveData.postValue(it)
                    movieCompositeDisposable.clear()
                }, {
                    Log.e("TAG_X", it.message, it)
                }))
        return movieLiveData
    }


}