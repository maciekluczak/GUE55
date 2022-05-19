package com.example.myapplication

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.core.view.WindowInsetsControllerCompat

class RegisterActivity : AppCompatActivity() {

    private fun hideSystemBars() {
        val windowInsetsController = ViewCompat.getWindowInsetsController(window.decorView) ?: return
        windowInsetsController.systemBarsBehavior = WindowInsetsControllerCompat.BEHAVIOR_SHOW_TRANSIENT_BARS_BY_SWIPE
        windowInsetsController.hide(WindowInsetsCompat.Type.systemBars())
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_register)
        hideSystemBars()

        val registerText = findViewById<EditText>(R.id.registerEdit)
        val passwordText = findViewById<EditText>(R.id.passwordRegisterEdit)
        val signButton = findViewById<Button>(R.id.trySignButton)

        signButton.setOnClickListener {
            val myDB = DBHelper(this)
            var tryUsername = registerText.text.toString().trim()
            var tryPassword = passwordText.text.toString().trim()

            if(tryPassword.isNotEmpty() && tryUsername.isNotEmpty()){
                if(!myDB.isUserExists(tryUsername)){
                    myDB.addUser(tryUsername, tryPassword)
                    Toast.makeText(this, "Success, $tryUsername signed!", Toast.LENGTH_SHORT).show()
                    onPause()
                }
                else{
                Toast.makeText(this, "Username already exists!", Toast.LENGTH_SHORT).show()}
            }
            else{
                Toast.makeText(this, "Invalid input!", Toast.LENGTH_SHORT).show()
            }


        }
    }
}