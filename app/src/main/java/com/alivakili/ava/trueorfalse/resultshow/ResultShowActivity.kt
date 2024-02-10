package com.alivakili.ava.trueorfalse.resultshow

import com.alivakili.ava.trueorfalse.R
import com.alivakili.ava.trueorfalse.databinding.ActivityResultShowBinding
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity



class ResultShowActivity : AppCompatActivity() {

    private lateinit var binding: ActivityResultShowBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityResultShowBinding.inflate(layoutInflater)
        setContentView(binding.root)
        getData()
        binding.done.setOnClickListener {
            finish()
        }

    }



    private fun properLayout(
        allData: Int,
        correctData: Int
    ) {
        if ((correctData.toDouble() / allData.toDouble()) >= 0.7) {
            showPassLayout(allData, correctData)
        } else {
            showFailedLayout(allData, correctData)
        }
    }

    private fun showPassLayout(
        allData: Int,
        correctData: Int
    ) {
        binding.text.text = "Congratulations"
        binding.imagePass.visibility=View.VISIBLE
        binding.totalQuestions.text = allData.toString()
        binding.correctAnswered.text = correctData.toString()
        binding.totalQuestions.setTextColor(getColor(R.color.vibrant_green))
        binding.correctAnswered.setTextColor(getColor(R.color.vibrant_green))
        binding.slash.setTextColor(getColor(R.color.vibrant_green))
    }

    private fun showFailedLayout(
        allData: Int,
        correctData: Int
    ) {
        binding.text.text = "Sorry"
        binding.imageFailed.visibility=View.VISIBLE
        binding.totalQuestions.text = allData.toString()
        binding.correctAnswered.text = correctData.toString()
        binding.totalQuestions.setTextColor(getColor(R.color.reddish_brown))
        binding.correctAnswered.setTextColor(getColor(R.color.reddish_brown))
        binding.slash.setTextColor(getColor(R.color.reddish_brown))
    }


    private fun getData(){
        val correctData = intent.getIntExtra("CORRECT", 0)
        val allData = intent.getIntExtra("ALL", 0)
        properLayout(allData,correctData)
    }



    companion object {
        fun intent(context: Context): Intent {
            return Intent(context, ResultShowActivity::class.java)
        }
    }


}

