package com.me.cl.myapplication

import android.databinding.DataBindingUtil
import android.databinding.ViewDataBinding
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.ViewGroup
import com.me.cl.myapplication.databinding.ItemContactBinding

/**
 * Created by CL on 10/19/18.
 */
class ContactListAdapter(var contacts: MutableList<Contact>) : RecyclerView.Adapter<DataBindingViewHolder<ItemContactBinding>>(), ItemTouchDelegate {

    override fun onCreateViewHolder(p0: ViewGroup, p1: Int): DataBindingViewHolder<ItemContactBinding> {
        return DataBindingViewHolder(DataBindingUtil.inflate(LayoutInflater.from(p0.context), R.layout.item_contact, p0, false))
    }

    override fun getItemCount(): Int {
        return contacts.size
    }

    override fun onBindViewHolder(p0: DataBindingViewHolder<ItemContactBinding>, p1: Int) {
        p0.viewBinding.apply {
            contact = contacts[p1]
            executePendingBindings()
        }
    }

    override fun onItemRemove(position: Int) {
        contacts.removeAt(position)
        notifyItemRemoved(position)
    }

}

class DataBindingViewHolder<T : ViewDataBinding>(val viewBinding: T) : RecyclerView.ViewHolder(viewBinding.root)