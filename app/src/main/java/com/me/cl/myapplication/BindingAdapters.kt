package com.me.cl.myapplication

import android.databinding.BindingAdapter

@BindingAdapter("content")
fun bindContent(view: CustomHeadView, first: String) {
    view.setCharacter(first[0])
}
