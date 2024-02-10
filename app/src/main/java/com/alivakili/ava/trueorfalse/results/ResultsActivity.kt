package com.alivakili.ava.trueorfalse.results

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.recyclerview.widget.LinearLayoutManager
import com.alivakili.ava.trueorfalse.R
import com.alivakili.ava.trueorfalse.database.DatabaseInstance
import com.alivakili.ava.trueorfalse.databinding.ActivityResultsBinding
import com.alivakili.ava.trueorfalse.form.FormActivity
import com.alivakili.ava.trueorfalse.question.QuestionModel
import kotlinx.coroutines.launch


class ResultsActivity : AppCompatActivity() {
    private lateinit var binding: ActivityResultsBinding
    private lateinit var context : Context
    private lateinit var adapter:ResultRecyclerViewAdapter
    private val viewModel:ResultViewModel by viewModels {
        ResultViewModel.factory(
            dao = DatabaseInstance.instance(this).questionDao()
        )}

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        context=this
        binding=ActivityResultsBinding.inflate(layoutInflater)
        setContentView(binding.root)
        configureToolbar()
        configureRecyclerView()
        observeViewModel()
        configureClickListeners()

    }

    private fun configureRecyclerView() {
        adapter= ResultRecyclerViewAdapter (this)
        binding.recyclerView.apply {
            adapter=this@ResultsActivity.adapter
            layoutManager= LinearLayoutManager(this@ResultsActivity)
            setHasFixedSize(false)
        }
    }

    private fun configureToolbar(){
        setSupportActionBar(binding.toolbar)
        supportActionBar?.apply {
            setTitle("Pass Result")
            setDisplayHomeAsUpEnabled(true)
            setHomeAsUpIndicator(R.drawable.baseline_arrow_back_24)
        }
    }

    override fun onSupportNavigateUp(): Boolean {
        finish()
        return true
    }

    private fun showProgressBar(){
        binding.progressBar.visibility=View.VISIBLE
    }

    private fun hideProgressBar(){
        binding.progressBar.visibility=View.GONE
    }

    private fun configureClickListeners(){
        binding.allButton.setOnClickListener {
            viewModel.onAllClicked()
        }
        binding.correctButton.setOnClickListener { viewModel.onCorrectClicked() }
        binding.incorrectButton.setOnClickListener { viewModel.onIncorrectClicked() }

    }

    private fun toggleButtonCurrentState(): com.alivakili.ava.trueorfalse.ToggleButtonState {
        return if(binding.toggleButton.checkedButtonId==R.id.allButton)
            com.alivakili.ava.trueorfalse.ToggleButtonState.All
        else if(binding.toggleButton.checkedButtonId==R.id.incorrectButton)
            com.alivakili.ava.trueorfalse.ToggleButtonState.Incorrect
        else
            com.alivakili.ava.trueorfalse.ToggleButtonState.Correct

    }

    private fun observeViewModel(){
        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.CREATED){
                viewModel.state.collect{state->
                    when(state){
                        ResultState.Loading -> {
                            showProgressBar()
                            binding.toggleButton.visibility= View.GONE
                            binding.emptyListLayout.root.visibility=View.VISIBLE
                            binding.emptyListLayout.startGame.setOnClickListener {
                                startActivity(FormActivity.intent(context))
                                finish()
                            }
                        }
                        is ResultState.QuestionList ->showResultList(state.questions)
                    }

                }
            }


        }


    }

    private fun showResultList(questions: List<QuestionModel>) {
        hideProgressBar()
        if (questions.isEmpty()){
            binding.recyclerView.visibility= View.GONE
            binding.toggleButton.visibility=View.GONE
            binding.emptyListLayout.root.visibility=View.VISIBLE
        }else{
            binding.toggleButton.visibility=View.VISIBLE
            binding.recyclerView.visibility= View.VISIBLE
            binding.emptyListLayout.root.visibility=View.GONE
        }
        adapter.differ.submitList(questions)
    }


    companion object{
        fun intent(context: Context): Intent {
            return Intent(context, ResultsActivity::class.java)
        }
    }
}