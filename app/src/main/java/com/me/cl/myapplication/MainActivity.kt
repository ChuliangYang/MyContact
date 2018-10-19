package com.me.cl.myapplication

import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
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
            contactListAdapter.contacts.add(Contact("Fake"))
            contactListAdapter.notifyItemInserted(contactListAdapter.contacts.size - 1)
            rv_contact_list.scrollToPosition(contactListAdapter.contacts.size - 1)
        }
        if (savedInstanceState == null) {
            for (i in 1 until 20) {
                data.add(Contact("aname"))
                data.add(Contact("bname"))
                data.add(Contact("cname"))
            }
            Single.create<List<Contact>> {
                it.onSuccess(sortList(data))
            }.subscribeOn(Schedulers.computation()).observeOn(AndroidSchedulers.mainThread())
                    .compose(bindToLifecycle()).subscribe({ t ->
                        contactListAdapter.contacts.clear()
                        contactListAdapter.contacts.addAll(t)
                        contactListAdapter.notifyDataSetChanged()
                    }, ::error)
        } else {
            data = savedInstanceState.getParcelableArrayList<Contact>(CONTACTS_KEY) ?: mutableListOf()
            contactListAdapter.contacts = data
            contactListAdapter.notifyDataSetChanged()
        }
    }

    fun sortList(contacts: List<Contact>): List<Contact> {
        return contacts.sortedWith(Comparator { t1, t2 ->
            t1.name[0] - t2.name[0]
        })
    }

    override fun onSaveInstanceState(outState: Bundle?) {
        super.onSaveInstanceState(outState)
        outState?.putParcelableArrayList(CONTACTS_KEY, contactListAdapter.contacts as ArrayList<Contact>)
    }
}
