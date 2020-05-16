package com.exam.sampleproject.ext

import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.rxkotlin.subscribeBy

fun <T : Any> T.toObservable(clickable: (T) -> Unit): Disposable {
    return Observable.just(this)
        .observeOn(AndroidSchedulers.mainThread())
        .subscribeBy(
            onNext = {
                clickable.invoke(it)
            }
        )
}