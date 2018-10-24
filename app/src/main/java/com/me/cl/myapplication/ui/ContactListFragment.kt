package com.me.cl.myapplication.ui

import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProviders
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.LinearLayoutManager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.me.cl.myapplication.R
import com.me.cl.myapplication.adapter.ContactListAdapter
import com.me.cl.myapplication.model.Contact
import com.me.cl.myapplication.utils.CustomItemTouchHelper
import com.me.cl.myapplication.utils.ItemTouchDelegate
import com.me.cl.myapplication.utils.ModifyType
import com.me.cl.myapplication.viewmodel.ContactViewModel
import com.me.cl.myapplication.viewmodel.ContactViewModelFactory
import kotlinx.android.synthetic.main.activity_main.*

class ContactListFragment : Fragment() {
    lateinit var contactListAdapter: ContactListAdapter

    companion object {
        fun newInstance() = ContactListFragment()
    }

    private lateinit var viewModel: ContactViewModel

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View {
        return inflater.inflate(R.layout.activity_main, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        rv_contact_list.apply {
            layoutManager = LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
            contactListAdapter = ContactListAdapter(mutableListOf())
            adapter = contactListAdapter
            CustomItemTouchHelper(object: ItemTouchDelegate {
                override fun onItemRemove(position: Int) {
                    if(::viewModel.isInitialized){
                        viewModel.removeItem(position)
                    }
                }
            }).let {
                it.attachToRecyclerView(this)
            }
        }
        fb_add.setOnClickListener {
            if(::viewModel.isInitialized){
                viewModel.insertNewItem(Contact("Fake"))
            }
        }
        viewModel = ViewModelProviders.of(this, ContactViewModelFactory()).get(ContactViewModel::class.java)

        viewModel.subscribeListEvent().observe(this, Observer {
            when(it?.modifyType){
                ModifyType.Init->{
                    contactListAdapter.apply {
                        contacts.clear()
                        contacts.addAll(it.dataList?: mutableListOf())
                        notifyDataSetChanged()
                    }
                }
                ModifyType.Insert->{
                    contactListAdapter.apply{
                        contacts.addAll(it.dataList?: mutableListOf())
                        notifyItemInserted(contacts.size - 1)
                        rv_contact_list.scrollToPosition(contacts.size - 1)
                    }
                }
                ModifyType.Delete->{
                    it.position?.run{
                        contactListAdapter.contacts.removeAt(this)
                        contactListAdapter.notifyItemRemoved(this)
                    }
                }
                else->{}
            }
        })

        if(savedInstanceState==null){
            viewModel.getSortedContactList()
        }else{
            //Data will lose if activity is destroyed in background, use Room to persist state and restore if necessary.
            viewModel.restoreContactList()
        }
    }

}
