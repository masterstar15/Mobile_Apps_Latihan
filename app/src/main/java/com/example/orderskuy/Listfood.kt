package com.example.orderskuy

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.ImageView

class Listfood : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_listfood)
        var back = findViewById<Button>(R.id.balik) as Button

        back!!.setOnClickListener {
            startActivity(
                Intent(
                    this@Listfood,
                    DashboardActivity::class.java
                )
            )
        }
    }
}