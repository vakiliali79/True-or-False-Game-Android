package com.alivakili.ava.trueorfalse.question

import com.alivakili.ava.trueorfalse.databinding.ActivityQuestionBinding
import android.app.AlertDialog
import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.view.View
import androidx.activity.viewModels
import androidx.core.text.HtmlCompat
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import com.alivakili.ava.trueorfalse.database.DatabaseInstance
import com.alivakili.ava.trueorfalse.form.FormModel
import com.alivakili.ava.trueorfalse.resultshow.ResultShowActivity
import kotlinx.coroutines.launch


class QuestionActivity : AppCompatActivity() {
    private lateinit var binding: ActivityQuestionBinding
    private var form:FormModel?= FormModel()
    private var currentQuestion=QuestionModel()
    private var currentQuestionIndex:Int=0

    private val viewModel:QuestionViewModel by viewModels {
        QuestionViewModel.factory(
            dao = DatabaseInstance.instance(this).questionDao()
        )}

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityQuestionBinding.inflate(layoutInflater)
        setContentView(binding.root)
        getDataFromIntent()
    }

    private fun getDataFromIntent() {
        form =intent.getParcelableExtra<FormModel>("form")
        if(form!=null){
            observeViewModel()
        }
    }


    private fun observeViewModel(){
        viewModel.retrieveNews(form)
        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.CREATED){
                viewModel.state.collect{state->
                    when(state){
                        QuestionState.Loading->showProgressBar()
                        QuestionState.Failure -> showDialog("Network Error")
                        QuestionState.NoResult -> openEmptyListDialog()
                        is QuestionState.Success ->showQuestions(state.questions)
                    }
                }
            }
        }
    }

    private fun openEmptyListDialog() {
        hideProgressBar()
        showEmptyLayout()
    }

    private fun showEmptyLayout() {
        binding.emptyListLayout.root.visibility=View.VISIBLE

        val handler = Handler()
        // Define a Runnable for the second action
        val runnable = Runnable {
            finish()
        }
        handler.postDelayed(runnable, 5000L)
    }

    private fun showDialog(message: String) {
        val dialog = AlertDialog.Builder(this)
            .setTitle("Error")
            .setMessage(message)
            .setCancelable(true)
            .create()

        dialog.show()

        val handler = android.os.Handler()
        handler.postDelayed({ dialog.dismiss()
            finish()
                            }, 3000) // Dismiss the dialog after 3 seconds (3000 milliseconds)

    }

    private fun showQuestions(questions: List<QuestionModel>) {
        viewModel.deleteDataBase()
        hideProgressBar()
        showLayout()
        displayQuestion(questions)
        binding.trueButton.setOnClickListener {
            handleAnswer(true,questions.size,questions)
        }

        binding.falseBtn.setOnClickListener {
            handleAnswer(false, questions.size, questions)
        }
    }

    private fun handleAnswer(userAnswer: Boolean, size: Int, questions: List<QuestionModel>) {
        // Handle the user's answer
        // You can perform any necessary actions based on the selected answer (e.g., check correctness, update score)
        questions[currentQuestionIndex].youAnswered=userAnswer.toString()
        checkAnswer(questions[currentQuestionIndex])
        viewModel.questionAnswered(questions[currentQuestionIndex])
        // Show the next question or finish the activity if there are no more questions
        currentQuestionIndex++
        if (currentQuestionIndex < size) {
            displayQuestion(questions)
        } else {
            intent= ResultShowActivity.intent(this)
            intent.putExtra("ALL",questions.size)
            intent.putExtra("CORRECT",correctAnswersCheck(questions))
            startActivity(intent)
            finish()
        }
    }
    private fun correctAnswersCheck(questions: List<QuestionModel>):Int{
        var size=0
        for (q in questions)
            if(q.correctAnswer.lowercase()==q.youAnswered.lowercase())
                size++
        return size
    }

    private fun checkAnswer(questionModel: QuestionModel) {
        val addQuestionSheet = AddButtonSheet(questionModel)
        addQuestionSheet.show(supportFragmentManager, AddButtonSheet.TAG)
    }


    private fun displayQuestion(questions: List<QuestionModel>) {
        currentQuestion=questions[currentQuestionIndex]
        binding.questionText .text =
            currentQuestion.question?.let { HtmlCompat.fromHtml(it, HtmlCompat.FROM_HTML_MODE_LEGACY).toString() }
        binding.progress.progress = ((currentQuestionIndex.toDouble() / questions.size.toDouble())*100).toInt()
    }

    private fun showLayout() {
        binding.progress.visibility=View.VISIBLE
        binding.questionText.visibility=View.VISIBLE
        binding.buttonsLayout.visibility=View.VISIBLE
    }

    private fun showProgressBar() {
        binding.loadingProgressBar.visibility= View.VISIBLE
    }

    private fun hideProgressBar() {
        binding.loadingProgressBar.visibility= View.GONE
    }

    companion object {
        fun intent(context: Context): Intent {
            return Intent(context, QuestionActivity::class.java)
        }
    }
}