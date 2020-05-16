package com.exam.sampleproject.ext

import android.view.View
import android.widget.Button
import android.widget.EditText
import com.jakewharton.rxbinding3.view.clicks
import com.jakewharton.rxbinding3.widget.textChangeEvents
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.rxkotlin.subscribeBy
import java.util.*
import java.util.concurrent.TimeUnit


fun Button.enabledWithAlpha() {
    isEnabled = true
    alpha = 1f
}

fun Button.disabledWithAlpha() {
    isEnabled = false
    alpha = .3f
}

fun EditText.capitalizeFirstLetter(): Disposable {
    return this.textChangeEvents()
        .skipInitialValue()
        .observeOn(AndroidSchedulers.mainThread())
        .map {
            val typeString = it.text.toString()
            if (typeString.length == 1) {
                if (typeString.single().isLowerCase()) {
                    setText(typeString.toUpperCase(Locale.getDefault()))
                }
            }
            return@map typeString
        }
        .subscribeBy(
            onNext = {
                setSelection(it.length)
            }
        )
}

fun View.ninjaTap(onNext: (View) -> Unit): Disposable {
    return this.clicks().throttleFirst(250, TimeUnit.MILLISECONDS)
        .observeOn(AndroidSchedulers.mainThread())
        .subscribeBy(
            onNext = {
                onNext.invoke(this)
            }
        )
}
