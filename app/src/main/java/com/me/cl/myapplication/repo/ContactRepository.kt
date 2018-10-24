package com.me.cl.myapplication.repo

import android.arch.lifecycle.LiveData
import com.me.cl.myapplication.model.Contact
import com.me.cl.myapplication.utils.ReactUtil

/**
 * Created by CL on 10/23/18.
 */
class ContactRepository {
    var data:MutableList<Contact> = arrayListOf()//Memory data source
    init {
        for (i in 1 until 20) {
            data.apply {
                add(Contact("Bname"))
                add(Contact("Cname"))
                add(Contact("Aname"))
            }
        }
    }

    fun getContactList(): LiveData<List<Contact>> {
        return ReactUtil.toLiveDataResource{data}
    }

    fun sortContactList(contacts:MutableList<Contact>):LiveData<List<Contact>>{
       return  ReactUtil.toLiveDataResource{
            contacts.apply {
                sortWith(Comparator { t1, t2 ->
                    t1.name.toUpperCase()[0] - t2.name.toUpperCase()[0]
                })
            }
        }
    }

    fun insertNewItem(contact:Contact){
        data.add(contact)
    }

    fun removeItem(position: Int){
        data.removeAt(position)
    }
}