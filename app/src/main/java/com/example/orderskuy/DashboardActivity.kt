package com.example.orderskuy

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.ImageView
import androidx.appcompat.app.AppCompatActivity

class DashboardActivity : AppCompatActivity() {

    var piclist: ImageView? = null
    var picorder:android.widget.ImageView? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_dashboard)
        piclist = findViewById<View>(R.id.listfood) as ImageView

        piclist!!.setOnClickListener {
            startActivity(
                Intent(
                    this@DashboardActivity,
                    Listfood::class.java
                )
            )
        }

        picorder = findViewById<View>(R.id.order) as ImageView

        picorder!!.setOnClickListener {
            startActivity(
                Intent(
                    this@DashboardActivity,
                    orderFood::class.java
                )
            )
        }
    }
}