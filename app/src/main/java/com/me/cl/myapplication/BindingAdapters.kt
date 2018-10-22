package com.me.cl.myapplication

import android.databinding.BindingAdapter

@BindingAdapter("content")
fun bindContent(view: CustomHeadView, content: String) {
    view.setCharacter(content[0])
}
