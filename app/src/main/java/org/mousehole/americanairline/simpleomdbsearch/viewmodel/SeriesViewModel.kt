package org.mousehole.americanairline.simpleomdbsearch.viewmodel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers
import org.mousehole.americanairline.simpleomdbsearch.model.series.SeriesResponse
import org.mousehole.americanairline.simpleomdbsearch.network.OMDBRetrofit

class SeriesViewModel : ViewModel() {

    private val seriesCompositeDisposable = CompositeDisposable()

    private val seriesLiveData: MutableLiveData<SeriesResponse> = MutableLiveData()

    fun getSeriesResult(seriesName : String) : LiveData<SeriesResponse> {
        seriesCompositeDisposable.add(
            OMDBRetrofit.getSeries(seriesName)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribe ({
                    seriesLiveData.postValue(it)
                    seriesCompositeDisposable.clear()
                }, {
                    Log.e("TAG_X", it.message, it)
                }))
        return seriesLiveData
    }


}