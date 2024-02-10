package com.alivakili.ava.trueorfalse.results

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.alivakili.ava.trueorfalse.R
import com.alivakili.ava.trueorfalse.databinding.ResultItemListBinding
import com.alivakili.ava.trueorfalse.question.QuestionModel

class ResultRecyclerViewAdapter (
    private val context: Context
        ) : RecyclerView.Adapter<ResultRecyclerViewAdapter.ResultViewHolder>(){

    private val diffCallback=object : DiffUtil.ItemCallback<QuestionModel>(){
        override fun areItemsTheSame(oldItem: QuestionModel, newItem: QuestionModel): Boolean {
            return oldItem.id==newItem.id
        }

        override fun areContentsTheSame(oldItem: QuestionModel, newItem: QuestionModel): Boolean {
            return oldItem==newItem
        }


    }

    val differ= AsyncListDiffer(this,diffCallback)

    class ResultViewHolder(
        private val binding: ResultItemListBinding,
        private val context: Context
    ): RecyclerView.ViewHolder(binding.root){

        fun bind(question: QuestionModel){
            if(question.youAnswered.lowercase()==question.correctAnswer.lowercase()){
                binding.view.setBackgroundColor( ContextCompat.getColor(context,R.color.vibrant_green))
                binding.question.text=question.question
                binding.question.setTextColor(ContextCompat.getColor(context,R.color.vibrant_green))
                binding.youAnswered.text=question.youAnswered
                binding.textYouAnswered.setTextColor(ContextCompat.getColor(context,R.color.vibrant_green))
                binding.youAnswered.setTextColor(ContextCompat.getColor(context,R.color.vibrant_green))
            }else{
                binding.question.setTextColor(ContextCompat.getColor(context,R.color.reddish_brown))
                binding.view.setBackgroundColor( ContextCompat.getColor(context,R.color.reddish_brown))
                binding.question.text=question.question
                binding.youAnswered.text=question.youAnswered
                binding.textYouAnswered.setTextColor(ContextCompat.getColor(context,R.color.reddish_brown))
                binding.youAnswered.setTextColor(ContextCompat.getColor(context,R.color.reddish_brown))
            }


        }


        companion object{
            fun create(parent: ViewGroup,context: Context): ResultViewHolder {
                val binding= ResultItemListBinding.inflate(
                    LayoutInflater.from(parent.context),
                    parent,
                    false)
                return ResultRecyclerViewAdapter.ResultViewHolder(
                    context = context,
                    binding = binding,
                )
            }
        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ResultViewHolder {
        return ResultViewHolder.create(parent, context )
    }

    override fun getItemCount(): Int {
        return differ.currentList.size
    }

    override fun onBindViewHolder(holder: ResultViewHolder, position: Int) {
        holder.bind(differ.currentList[position])
    }
}