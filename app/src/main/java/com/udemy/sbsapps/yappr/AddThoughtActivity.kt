package com.udemy.sbsapps.yappr

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.view.View

class AddThoughtActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_thought)
    }

    fun addPostClicked (view: View) {}
    fun addFunnyClicked (view: View) {}
    fun addSeriousClicked (view: View) {}
    fun addCrazyClicked (view: View) {}
}
