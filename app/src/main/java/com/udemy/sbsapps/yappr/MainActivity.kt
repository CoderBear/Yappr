package com.udemy.sbsapps.yappr

import android.content.Intent
import android.os.Bundle
import android.support.design.widget.Snackbar
import android.support.v7.app.AppCompatActivity
import android.view.Menu
import android.view.MenuItem
import android.view.View
import kotlinx.android.synthetic.main.activity_add_thought.*

import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.content_main.*

class MainActivity : AppCompatActivity() {

    var selectedCategory = FUNNY

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setSupportActionBar(toolbar)

        fab.setOnClickListener { view ->
            val addThoughtIntent = Intent(this, AddThoughtActivity::class.java)
            startActivity(addThoughtIntent)
        }
    }

    fun mainFunnyClicked (view: View) {
        if(selectedCategory == FUNNY) {
            mainFunnyButton.isChecked = true
        }
        mainSeriousButton.isChecked = false
        mainCrazyButton.isChecked = false
        mainPopularButton.isChecked = false
    }
    fun mainSeriousClicked (view: View) {
        if(selectedCategory == SERIOUS) {
            mainSeriousButton.isChecked = true
        }
        mainFunnyButton.isChecked = false
        mainCrazyButton.isChecked = false
        mainPopularButton.isChecked = false
    }
    fun mainCrazyClicked (view: View) {
        if(selectedCategory == CRAZY) {
            mainCrazyButton.isChecked = true
        }
        mainFunnyButton.isChecked = false
        mainSeriousButton.isChecked = false
        mainPopularButton.isChecked = false
    }
    fun mainPopularClicked (view: View) {
        if(selectedCategory == POPULAR) {
            mainPopularButton.isChecked = true
        }
        mainFunnyButton.isChecked = false
        mainSeriousButton.isChecked = false
        mainCrazyButton.isChecked = false
    }
}
