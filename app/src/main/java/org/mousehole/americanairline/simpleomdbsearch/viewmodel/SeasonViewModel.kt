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

class SeasonViewModel : ViewModel() {

    private val seasonCompositeDisposable = CompositeDisposable()

    private val seasonLiveData: MutableLiveData<SeasonResponse> = MutableLiveData()

    fun getSeasonResult(seriesName : String, season : Int) : LiveData<SeasonResponse> {
        seasonCompositeDisposable.add(
            OMDBRetrofit.getSeason(seriesName, season)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribe ({
                    seasonLiveData.postValue(it)
                    seasonCompositeDisposable.clear()
                }, {
                    Log.e("TAG_X", it.message, it)
                }))
        return seasonLiveData
    }


}