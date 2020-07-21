package com.phooper.yammynyammy.utils

import android.content.Context
import io.reactivex.rxjava3.core.Observable
import io.reactivex.rxjava3.subjects.ReplaySubject

class SystemMessageNotifier(private val context: Context) {

    private val _notifier = ReplaySubject.create<String>()
    val notifier: Observable<String> = _notifier.share()

    fun sendMessage(msg: String) = _notifier.onNext(msg)
    fun sendMessage(msgResId: Int) = _notifier.onNext(context.resources.getString(msgResId))

}