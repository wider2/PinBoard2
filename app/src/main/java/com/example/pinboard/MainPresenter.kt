package com.example.pinboard

import android.annotation.SuppressLint
import com.example.pinboard.dagger.OnDestroy
import com.example.pinboard.data.repo.PinsRepository
import com.example.pinboard.model.PinModel
import io.reactivex.Completable
import io.reactivex.android.schedulers.AndroidSchedulers
import javax.inject.Inject

class MainPresenter @Inject constructor(
    private val view: MainContract.View,
    private val repository: PinsRepository,
    @OnDestroy private val onDestroyCompletable: Completable
) : MainContract.Presenter {

    @SuppressLint("CheckResult")
    override fun subscribeEvent() {
        view.showProgress()

        repository.getPins()
            .takeUntil(onDestroyCompletable)
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({ eventList ->
                onSuccess(eventList)
            }, { throwable -> onFailure(throwable) })
    }

    private fun onSuccess(pinList: List<PinModel>?) {
        pinList?.let {
            view.showEventList(pinList)
        }
    }

    private fun onFailure(throwable: Throwable?) {
        throwable?.let {
            view.resultFailed(throwable.message.toString())
        }
    }
}