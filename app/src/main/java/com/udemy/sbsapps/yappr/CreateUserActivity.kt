package com.udemy.sbsapps.yappr

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.view.View

class CreateUserActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_create_user)
    }

    fun createCreateClicked(view: View) {}
    fun createCancelClicked(view: View) {
        var intent = Intent(this, LoginActivity::class.java)
        startActivity(intent)
    }
}
