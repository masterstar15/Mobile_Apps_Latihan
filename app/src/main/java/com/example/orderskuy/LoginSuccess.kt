package com.example.orderskuy

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.view.View
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity

class LoginSuccess : AppCompatActivity() {
    var name: TextView? = null
    var email: TextView? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login_success)
        name = findViewById<View>(R.id.nama) as TextView
        email = findViewById<View>(R.id.email) as TextView
        val bundle = intent.extras
        name!!.text = "Selamat datang " + bundle!!.getString("name")
        email!!.text = "Email : " + bundle.getString("email")

        //Splash Screening
        Handler().postDelayed({
            val intent = Intent(this@LoginSuccess, DashboardActivity::class.java)
            startActivity(intent)
            finish()
        }, SPLASH_TIME_OUT.toLong())
    }

    companion object {
        private const val SPLASH_TIME_OUT = 5000
     }
}