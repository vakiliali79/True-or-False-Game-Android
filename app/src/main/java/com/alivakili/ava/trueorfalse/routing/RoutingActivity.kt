package com.alivakili.ava.trueorfalse.routing

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.alivakili.ava.trueorfalse.first.FirstActivity
import com.alivakili.ava.trueorfalse.main.MainActivity


class RoutingActivity : AppCompatActivity() {
    private lateinit var sharedPreferences: SharedPreferences

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        sharedPreferences = getSharedPreferences("MyPrefs", Context.MODE_PRIVATE)
        // Check if it's the first launch
        val isFirstLaunch = sharedPreferences.getBoolean("isFirstLaunch", true)

        if (isFirstLaunch) {
            // First launch, open Activity1
            val intent = Intent(this, FirstActivity::class.java)
            startActivity(intent)

            // Update the flag to indicate that it's not the first launch anymore
            val editor = sharedPreferences.edit()
            editor.putBoolean("isFirstLaunch", false)
            editor.apply()
        } else {
            // Not the first launch, open Activity2
            startActivity(MainActivity.intent(this))
        }
        finish()
    }





}