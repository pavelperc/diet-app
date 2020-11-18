package com.example.dietapp.ui.login

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.dietapp.R
import com.example.dietapp.ui.mainScreen.MainActivity
import kotlinx.android.synthetic.main.activity_login.*

class LoginActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        btnGoogleSignIn.setOnClickListener {
            login()
        }
        login()
    }

    private fun login() {
        startActivity(Intent(this, MainActivity::class.java))
        finish()
    }
}