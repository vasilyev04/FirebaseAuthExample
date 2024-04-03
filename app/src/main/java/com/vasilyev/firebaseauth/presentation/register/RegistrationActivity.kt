package com.vasilyev.firebaseauth.presentation.register

import android.content.Intent
import android.os.Bundle
import android.view.MenuItem
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import com.vasilyev.firebaseauth.databinding.ActivityRegisterBinding
import com.vasilyev.firebaseauth.presentation.App
import com.vasilyev.firebaseauth.presentation.ViewModelFactory
import com.vasilyev.firebaseauth.presentation.main.MainActivity
import javax.inject.Inject

class RegistrationActivity : AppCompatActivity() {
    private var _binding: ActivityRegisterBinding? = null
    private val binding: ActivityRegisterBinding
        get() = requireNotNull(_binding)

    @Inject
    lateinit var viewModelFactory: ViewModelFactory

    private val viewModel: RegistrationViewModel by viewModels{
        viewModelFactory
    }

    private val component by lazy {
        (application as App).component
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        component.inject(this)

        super.onCreate(savedInstanceState)
        _binding = ActivityRegisterBinding.inflate(layoutInflater)
        setContentView(binding.root)

        showHomeAsUpButton()
        setListeners()
        observeState()
    }

    private fun setListeners(){
        binding.btnSignUp.setOnClickListener {
            val email = binding.etEmail.text.toString()
            val password = binding.etPassword.text.toString()
            val repeatedPassword = binding.etRepeatPassword.text.toString()

            viewModel.register(email, password, repeatedPassword)
        }
    }

    private fun observeState(){
        viewModel.registerState.observe(this){state ->
            when(state){
                is RegistrationState.Success -> {
                    startMainActivity()
                }

                is RegistrationState.UnmatchedPasswordsError ->{
                   showToast("Please, make sure that you entered matched passwords")
                }

                is RegistrationState.UserAlreadyExistsError -> {
                    showToast("User with this Email is already exist")
                }

                is RegistrationState.WeakPasswordError -> {
                    showToast("Password must contain at least 6 symbols, digits and letters")
                }

                is RegistrationState.EmptyFieldsError -> {
                    showToast("Please fill all fields")
                }

                is RegistrationState.InvalidEmailError -> {
                    showToast("Incorrect Email")
                }

                is RegistrationState.UndefinedError -> {
                    showToast("Please try again")
                }
            }
        }
    }

    private fun showToast(message: String){
        Toast.makeText(
            this,
            message,
            Toast.LENGTH_SHORT
        ).show()
    }


    private fun startMainActivity(){
        Intent(this, MainActivity::class.java).apply {
            flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
            startActivity(this)
        }
    }

    private fun showHomeAsUpButton(){
        supportActionBar?.title = "Back"
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        finish()

        return super.onOptionsItemSelected(item)
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}