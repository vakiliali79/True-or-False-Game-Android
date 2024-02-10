package com.alivakili.ava.trueorfalse.main

import com.alivakili.ava.trueorfalse.R
import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.cardview.widget.CardView
import com.alivakili.ava.trueorfalse.form.FormActivity
import com.alivakili.ava.trueorfalse.results.ResultsActivity


class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        configureOnClickListener()
    }

    private fun configureOnClickListener() {
        val startGameBtn=findViewById<CardView>(R.id.startGame).setOnClickListener {
            startActivity(FormActivity.intent(this))
        }
        val pastResultBtn=findViewById<CardView>(R.id.pastResults).setOnClickListener {
            startActivity(ResultsActivity.intent(this))
        }
    }

    companion object{
        fun intent(context: Context): Intent {
            return Intent(context,MainActivity::class.java)
        }
    }
}