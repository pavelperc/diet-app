package com.example.dietapp.ui.loading

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.dietapp.R
import com.google.android.gms.common.SignInButton
import kotlinx.android.synthetic.main.loading_activity.*
import org.jetbrains.anko.toast

class LoadingActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.loading_activity)

        btnGoogleSignIn.setOnClickListener {
            toast("Hello")
        }
    }
}