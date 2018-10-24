package com.me.cl.myapplication.viewmodel

import android.arch.lifecycle.ViewModel
import android.arch.lifecycle.ViewModelProvider
import com.me.cl.myapplication.repo.ContactRepository


/**
 * Created by CL on 10/23/18.
 */
class ContactViewModelFactory: ViewModelProvider.NewInstanceFactory() {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return ContactViewModel(ContactRepository()) as T
    }
}