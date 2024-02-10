package com.alivakili.ava.trueorfalse.form

import com.alivakili.ava.trueorfalse.R
import com.alivakili.ava.trueorfalse.databinding.ActivityFormBinding
import android.app.AlertDialog
import android.content.ContentValues.TAG
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.NumberPicker
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import com.alivakili.ava.trueorfalse.question.QuestionActivity
import kotlinx.coroutines.launch


class FormActivity : AppCompatActivity() {
    private val viewModel:FormViewModel by viewModels{
        FormViewModel.factory()
    }
    private lateinit var form:FormModel
    private lateinit var binding: ActivityFormBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityFormBinding.inflate(layoutInflater)
        setContentView(binding.root)

        form= FormModel(0,"",0)
        configureSelectCategory()
        configureNumberOfQuestions()
        configureDifficulty()
        observeViewModel()
        startGame()
    }

    private fun startGame() {
        binding.startGame.setOnClickListener {
            Log.e(TAG, "startGame: ", )
            viewModel.startGame(form)
        }
    }

    private fun configureDifficulty() {
        var difficulty=""
        // Create an ArrayAdapter with category options
        val adapter = ArrayAdapter<String>(this, android.R.layout.simple_spinner_item)
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)

        // Add a hint as the first item
        adapter.add("Select difficulty")
        adapter.add("Easy")
        adapter.add("Medium")
        adapter.add("Hard")

        // Set the ArrayAdapter on the Spinner
        binding.selectDifficulty.adapter = adapter

        // Set an OnItemSelectedListener
        binding.selectDifficulty.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
                val selectedCategory = parent?.getItemAtPosition(position).toString()
                // Perform actions based on the selected category

                if (position == 0) {
                    // Do nothing if the hint is selected
                } else {
                    // Handle the click event for other selected items
                    when (selectedCategory) {

                        "Easy" -> {
                            difficulty="easy"
                            // Handle the click on "Sports" category
                        }
                        "Medium" -> {
                            difficulty="medium"
                            // Handle the click on "Geography" category
                        }
                        "Hard" -> {
                            difficulty="hard"
                            // Handle the click on "History" category
                        }
                    }
                    form.difficulty=difficulty
                }
            }

            override fun onNothingSelected(parent: AdapterView<*>?) {
                // Do nothing if no category is selected
            }
        }

        // Set the initial selection to the hint item
        binding.selectDifficulty.setSelection(0, false)
    }

    private fun configureNumberOfQuestions() {
        var amount: Int = 10

        binding.numberOfQuestionsCardView.setOnClickListener {
            val dialogBuilder = AlertDialog.Builder(this, R.style.AlertDialogTheme)
            val inflater = layoutInflater
            val dialogView = inflater.inflate(R.layout.custom_dialog_layout, null)
            dialogBuilder.setView(dialogView)

            val numberPicker = dialogView.findViewById<NumberPicker>(R.id.numberPicker)
            numberPicker.minValue = 10
            numberPicker.maxValue = 50
            numberPicker.value = amount

            val dialog = dialogBuilder.create()

            // Access the buttons in the custom dialog layout
            val buttonOk = dialogView.findViewById<Button>(R.id.buttonOk)
            val buttonCancel = dialogView.findViewById<Button>(R.id.buttonCancel)

            // Set click listener for the "OK" button
            buttonOk.setOnClickListener {
                amount = numberPicker.value
                // Perform actions with the selected number
                binding.numberOfQuestions.text=amount.toString()
                form.amount=amount
                dialog.dismiss() // Close the dialog
            }

            // Set click listener for the "Cancel" button
            buttonCancel.setOnClickListener {
                // Handle cancellation if needed
                dialog.dismiss() // Close the dialog
            }

            dialog.show()
        }
    }

    private fun configureSelectCategory() {
        var category=0
        // Create an ArrayAdapter with category options
        val adapter = ArrayAdapter<String>(this, android.R.layout.simple_spinner_item)
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)

        // Add a hint as the first item
        adapter.add("Select a category")
        adapter.add("General Knowledge")
        adapter.add("Sports")
        adapter.add("Geography")
        adapter.add("History")
        adapter.add("Politics")
        adapter.add("Art")

        // Set the ArrayAdapter on the Spinner
        binding.categorySpinner.adapter = adapter

        // Set an OnItemSelectedListener
        binding.categorySpinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
                val selectedCategory = parent?.getItemAtPosition(position).toString()
                // Perform actions based on the selected category

                if (position == 0) {

                    // Do nothing if the hint is selected
                } else {
                    // Handle the click event for other selected items
                    when (selectedCategory) {
                        "General Knowledge" -> {
                            category=9
                            form.category=category
                            // Handle the click on "General Knowledge" category
                        }
                        "Sports" -> {
                            category=21
                            form.category=category
                            // Handle the click on "Sports" category
                        }
                        "Geography" -> {
                            category=22
                            form.category=category
                            // Handle the click on "Geography" category
                        }
                        "History" -> {
                            category=23
                            form.category=category
                            // Handle the click on "History" category
                        }
                        "Politics" -> {
                            category=24
                            form.category=category
                            // Handle the click on "Politics" category
                        }
                        "Art" -> {
                            category=25
                            form.category=category
                            // Handle the click on "Art" category
                        }
                    }

                }
            }

            override fun onNothingSelected(parent: AdapterView<*>?) {
                // Do nothing if no category is selected
            }
        }

        // Set the initial selection to the hint item
        binding.categorySpinner.setSelection(0, false)
    }

    private fun observeViewModel(){
        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.CREATED){
                viewModel.state.collect{state->
                    when(state){
                        FormState.Error -> Toast.makeText(this@FormActivity, "Error", Toast.LENGTH_SHORT).show()
                        FormState.Idle -> {}
                        is FormState.Success -> openQuestionActivity(state)
                        is FormState.InvalidForm ->notifyInvalidForm(state)
                    }
                }
            }
        }
    }

    private fun notifyInvalidForm(invalidUserDetails: FormState.InvalidForm){
        if (!invalidUserDetails.isAmountValid) {
            showErrorDialog("Amount is not selected")
        }
        if (!invalidUserDetails.isCategoryValid) {
            showErrorDialog("Category is not selected")
        }
        if (!invalidUserDetails.isDifficultyValid) {
            showErrorDialog("Difficulty is not selected")
        }
    }

    private fun showErrorDialog(message: String) {
        val dialog = AlertDialog.Builder(this)
            .setTitle("Error")
            .setMessage(message)
            .setCancelable(true)
            .create()

        dialog.show()

        val handler = android.os.Handler()
        handler.postDelayed({ dialog.dismiss() }, 3000)  // Dismiss the dialog after 3 seconds (3000 milliseconds)
    }


    private fun openQuestionActivity(state: FormState.Success) {
        val intent = QuestionActivity.intent(this)
        val form=FormModel(
            state.amount,
            state.difficulty,
            state.category
        )
        Log.e(TAG, "openQuestionActivity:$form " )
        intent.putExtra("form",form )
        startActivity(intent)
        finish()
    }

    companion object {
        fun intent(context: Context): Intent {
            return Intent(context, FormActivity::class.java)
        }
    }
}