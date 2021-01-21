package org.mousehole.americanairline.simpleomdbsearch.viewmodel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers
import org.mousehole.americanairline.simpleomdbsearch.model.season.SeasonResponse
import org.mousehole.americanairline.simpleomdbsearch.model.series.SeriesResponse
import org.mousehole.americanairline.simpleomdbsearch.network.OMDBRetrofit
import org.mousehole.americanairline.simpleomdbsearch.util.Constants.LOG_TAG

class SeasonViewModel : ViewModel() {

    private val seasonCompositeDisposable = CompositeDisposable()

    private val seasonLiveData: MutableLiveData<SeasonResponse> = MutableLiveData()

    fun getSeasonResult(seriesName : String, season : String) : LiveData<SeasonResponse> {
        seasonCompositeDisposable.add(
            OMDBRetrofit.getSeason(seriesName, season)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribe ({
                    seasonLiveData.postValue(it)
                    seasonCompositeDisposable.clear()
                }, {
                    Log.e(LOG_TAG, it.message, it)
                    seasonCompositeDisposable.clear()
                }))
        return seasonLiveData
    }


}