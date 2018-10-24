package com.me.cl.myapplication.ui

import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import com.me.cl.myapplication.R
import com.me.cl.myapplication.adapter.ContactListAdapter
import com.me.cl.myapplication.model.Contact
import com.me.cl.myapplication.utils.CustomItemTouchHelper
import com.trello.rxlifecycle2.components.support.RxAppCompatActivity
import io.reactivex.Single
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : RxAppCompatActivity() {
    var data: MutableList<Contact> = arrayListOf()
    val CONTACTS_KEY = "contacts"
    lateinit var contactListAdapter: ContactListAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setSupportActionBar(tb_app)
        rv_contact_list.apply {
            layoutManager = LinearLayoutManager(this@MainActivity, LinearLayoutManager.VERTICAL, false)
            contactListAdapter = ContactListAdapter(mutableListOf())
            adapter = contactListAdapter
            CustomItemTouchHelper(contactListAdapter).let {
                it.attachToRecyclerView(this)
            }
        }
        fb_add.setOnClickListener {
            contactListAdapter.apply {
                contacts.add(Contact("Fake"))
                notifyItemInserted(contacts.size - 1)
                rv_contact_list.scrollToPosition(contacts.size - 1)
//              contacts = sortList(contacts)
//              notifyDataSetChanged()
            }
        }
        if (savedInstanceState == null) {
            for (i in 1 until 20) {
                data.apply {
                    add(Contact("Bname"))
                    add(Contact("Cname"))
                    add(Contact("Aname"))
                }
            }
            Single.create<MutableList<Contact>> {
                it.onSuccess(sortList(data))
            }.subscribeOn(Schedulers.computation()).observeOn(AndroidSchedulers.mainThread())
                    .compose(bindToLifecycle()).subscribe({ t ->
                        contactListAdapter.apply {
                            contacts.clear()
                            contacts.addAll(t)
                            notifyDataSetChanged()
                        }
                    }, ::error)
        } else {
            data = savedInstanceState.getParcelableArrayList<Contact>(CONTACTS_KEY) ?: mutableListOf()
            contactListAdapter.apply{
                contacts = data
                notifyDataSetChanged()
            }
        }
    }

    private fun sortList(contacts: MutableList<Contact>): MutableList<Contact> {
        return contacts.apply {
            sortWith(Comparator { t1, t2 ->
                t1.name.toUpperCase()[0] - t2.name.toUpperCase()[0]
            })
        }
    }

    override fun onSaveInstanceState(outState: Bundle?) {
        super.onSaveInstanceState(outState)
        outState?.putParcelableArrayList(CONTACTS_KEY, contactListAdapter.contacts as ArrayList<Contact>)
    }
}
