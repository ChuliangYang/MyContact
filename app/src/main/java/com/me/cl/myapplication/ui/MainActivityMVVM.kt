package com.me.cl.myapplication.ui

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import com.me.cl.myapplication.R

class MainActivityMVVM : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.main_activity2_activity)
        if (savedInstanceState == null) {
            supportFragmentManager.beginTransaction()
                    .replace(R.id.container, ContactListFragment.newInstance())
                    .commitNow()
        }
    }

}
