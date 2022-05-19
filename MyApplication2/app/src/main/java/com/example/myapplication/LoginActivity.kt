package com.example.myapplication

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.core.view.WindowInsetsControllerCompat

class LoginActivity : AppCompatActivity() {
    private fun hideSystemBars() {
        val windowInsetsController = ViewCompat.getWindowInsetsController(window.decorView) ?: return
        windowInsetsController.systemBarsBehavior = WindowInsetsControllerCompat.BEHAVIOR_SHOW_TRANSIENT_BARS_BY_SWIPE
        windowInsetsController.hide(WindowInsetsCompat.Type.systemBars())
    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.login)
        hideSystemBars()

        val loginText = findViewById<EditText>(R.id.loginEdit)
        val passwordText = findViewById<EditText>(R.id.passwordLoginEdit)
        val loginButton = findViewById<Button>(R.id. tryLogButton)


        loginButton.setOnClickListener {
            val myDB = DBHelper(this)
            var tryUsername = loginText.text.toString().trim()
            var tryPassword = passwordText.text.toString().trim()

            if(tryPassword.isNotEmpty() && tryUsername.isNotEmpty()){
                if(myDB.tryLogUser(tryUsername, tryPassword)){
                    Toast.makeText(this, "Welcome, $tryUsername", Toast.LENGTH_SHORT).show()

                    // the activity's onResume method has been called at this point

                    val intent = Intent(this, MainActivity::class.java)
                    intent.putExtra("username", tryUsername)
                    startActivity(intent)
                    onPause()
                }
                else{
                    Toast.makeText(this, "Wrong username or password", Toast.LENGTH_SHORT).show()}
            }
            else{
                Toast.makeText(this, "Invalid input!", Toast.LENGTH_SHORT).show()
            }
        }
    }
}