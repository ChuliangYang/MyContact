package com.me.cl.myapplication.utils

import android.arch.lifecycle.LiveData
import android.arch.lifecycle.MutableLiveData
import android.support.annotation.WorkerThread
import io.reactivex.Single
import io.reactivex.schedulers.Schedulers

object ReactUtil {
    @WorkerThread
    fun <T> toLiveDataResource(dataProducer: () -> T): LiveData<T> {
        val temp = MutableLiveData<T>()
        Single.create<T> {
            it.onSuccess(dataProducer())
        }.subscribeOn(Schedulers.computation()).subscribe { t1, t2 ->
                temp.postValue(t1)
        }
        return temp
    }

}
