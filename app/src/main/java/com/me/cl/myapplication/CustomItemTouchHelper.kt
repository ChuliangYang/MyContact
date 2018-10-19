package com.me.cl.myapplication

import android.support.v7.widget.RecyclerView
import android.support.v7.widget.helper.ItemTouchHelper

/**
 * Created by CL on 10/19/18.
 */
class CustomItemTouchHelper(val itemTouchDelegate:ItemTouchDelegate):ItemTouchHelper(object: Callback() {
    override fun getMovementFlags(p0: RecyclerView, p1: RecyclerView.ViewHolder): Int {
        return makeMovementFlags(0, ItemTouchHelper.LEFT or ItemTouchHelper.RIGHT)
    }

    override fun onMove(p0: RecyclerView, p1: RecyclerView.ViewHolder, p2: RecyclerView.ViewHolder): Boolean {
        return false
    }

    override fun onSwiped(p0: RecyclerView.ViewHolder, p1: Int) {
        itemTouchDelegate.onItemRemove(p0.adapterPosition)
    }
})