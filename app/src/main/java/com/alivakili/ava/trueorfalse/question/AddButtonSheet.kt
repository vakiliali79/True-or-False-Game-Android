package com.alivakili.ava.trueorfalse.question

import com.alivakili.ava.trueorfalse.R
import android.content.DialogInterface
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.WindowManager
import android.widget.EditText
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.core.content.ContextCompat.getColor
import com.google.android.material.bottomsheet.BottomSheetDialogFragment

class AddButtonSheet(private val questionModel: QuestionModel): BottomSheetDialogFragment() {

    private var layout: CardView?=null
    private var text:TextView?=null
    private var image:ImageView?=null

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?

    ): View? {
        val view = inflater.inflate(R.layout.custom_question_answer_bottom_sheet,container,false)
        layout=view.findViewById(R.id.linearLayout)
        text=view.findViewById(R.id.text)
        image=view.findViewById(R.id.image)
        Log.e(TAG, "onCreateView: "+questionModel.correctAnswer+questionModel.youAnswered, )
        if(questionModel.correctAnswer.lowercase()==questionModel.youAnswered.lowercase()){
            layout?.setCardBackgroundColor(getColor(requireContext(),R.color.vibrant_green))
            text?.text="Correct"
            image?.setImageResource(R.drawable.correct_answer_avatar)
        }else{
            layout?.setCardBackgroundColor(getColor(requireContext(),R.color.reddish_brown))
            text?.text="Incorrect"
            image?.setImageResource(R.drawable.incorrect_answer_avatar)
        }
        dialog?.window?.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_VISIBLE)
        layout?.setOnClickListener {
            dismiss()
        }
        return view
    }


    companion object{
        const val TAG="AddQuestionBottomSheet"
    }


}