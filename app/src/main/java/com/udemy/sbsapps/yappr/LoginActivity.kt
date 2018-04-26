package com.udemy.sbsapps.yappr

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.view.View

class LoginActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)
    }

    fun loginLoginClicked(view: View) {}
    fun loginCreateClicked(view: View) {
        var intent = Intent(this, CreateUserActivity::class.java)
        startActivity(intent)
    }
}
