package com.me.cl.myapplication.utils

import android.databinding.BindingAdapter
import com.me.cl.myapplication.widget.CustomHeadView

@BindingAdapter("content")
fun bindContent(view: CustomHeadView, content: String) {
    view.setCharacter(content[0])
}
