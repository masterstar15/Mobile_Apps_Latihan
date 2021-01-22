package com.example.orderskuy

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.view.View
import android.view.animation.AnimationUtils
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity

class MainActivity : AppCompatActivity() {

    var SPLASH_TIME_OUT = 5000

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        var topAnimation = AnimationUtils.loadAnimation(this, R.anim.top_animation)
        var bottomAnimation = AnimationUtils.loadAnimation(this, R.anim.bottom_animation)
        var middleAnimation = AnimationUtils.loadAnimation(this, R.anim.middle_animation)

        var first = findViewById<View>(R.id.first_line)
        var second = findViewById<View>(R.id.second_line)
        var third = findViewById<View>(R.id.third_line)
        var fourth = findViewById<View>(R.id.fourth_line)

        var b = findViewById<ImageView>(R.id.b)
        var slogan = findViewById<TextView>(R.id.slogan)


        //Setting Animations
        first.animation = topAnimation
        second.animation = topAnimation
        third.animation = topAnimation
        fourth.animation = topAnimation
        b.animation = middleAnimation
        slogan.animation = bottomAnimation


        //Splash Screening
        Handler().postDelayed({
            val intent = Intent(this@MainActivity, LoginActivity::class.java)
            startActivity(intent)
            finish()
        },SPLASH_TIME_OUT.toLong())
    }
}