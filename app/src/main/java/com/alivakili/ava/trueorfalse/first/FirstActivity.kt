package com.alivakili.ava.trueorfalse.first

import com.alivakili.ava.trueorfalse.R
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.cardview.widget.CardView
import com.alivakili.ava.trueorfalse.main.MainActivity

class FirstActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_first)

        val testMeBtn:CardView=findViewById(R.id.testMe)
        testMeBtn.setOnClickListener {
            startActivity(MainActivity.intent(this))
            finish()
        }
    }
}