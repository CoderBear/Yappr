package com.udemy.sbsapps.yappr

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import com.google.firebase.auth.FirebaseAuth

class LoginActivity : AppCompatActivity() {

    lateinit var auth : FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        auth = FirebaseAuth.getInstance();
    }

    fun loginLoginClicked(view: View) {}
    fun loginCreateClicked(view: View) {
        var intent = Intent(this, CreateUserActivity::class.java)
        startActivity(intent)
    }
}
