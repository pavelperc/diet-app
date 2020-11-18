package com.example.dietapp.ui.login

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.dietapp.R
import com.example.dietapp.ui.mainScreen.MainActivity
import kotlinx.android.synthetic.main.activity_login.*
import org.jetbrains.anko.startActivity

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
        startActivity<MainActivity>()
        finish()
    }
}