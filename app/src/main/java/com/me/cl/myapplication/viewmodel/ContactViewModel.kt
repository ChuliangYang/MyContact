package com.me.cl.myapplication.viewmodel

import android.arch.lifecycle.LiveData
import android.arch.lifecycle.MediatorLiveData
import android.arch.lifecycle.Transformations
import android.arch.lifecycle.ViewModel
import com.me.cl.myapplication.model.Contact
import com.me.cl.myapplication.repo.ContactRepository
import com.me.cl.myapplication.utils.ListEvent
import com.me.cl.myapplication.utils.ModifyType

class ContactViewModel(val contactRepository: ContactRepository) : ViewModel() {
    val listEventLiveData = MediatorLiveData<ListEvent<Contact>>()//Emit new item event manually

    fun insertNewItem(contact: Contact) {
        contactRepository.insertNewItem(contact)
        listEventLiveData.value = ListEvent(ModifyType.Insert, arrayListOf(contact))
    }

    fun removeItem(position: Int) {
        contactRepository.removeItem(position)
        listEventLiveData.value = ListEvent(ModifyType.Delete, position = position)
    }

    fun getSortedContactList() {
        Transformations.switchMap(contactRepository.getContactList()) {
            contactRepository.sortContactList(it?.toMutableList() ?: mutableListOf())
        }.apply {
            listEventLiveData.addSource(this) {
                listEventLiveData.removeSource(this)
                listEventLiveData.value = ListEvent(ModifyType.Init, it)
            }
        }

    }

    fun observeListEvent(): LiveData<ListEvent<Contact>> {
        return listEventLiveData
    }
}
