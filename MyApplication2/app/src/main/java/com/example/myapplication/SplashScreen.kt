package com.example.myapplication

import android.app.ActivityOptions
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.content.Intent
import android.os.Handler
import android.os.Looper
import android.transition.Explode
import android.view.Window
import android.view.animation.AnimationUtils

class SplashScreen : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash_screen)



        Handler(Looper.getMainLooper())
            .postDelayed({
            val intent = Intent(this, MainActivity::class.java)
                startActivity(intent)
            finish()
        }, 3000)


    }
}