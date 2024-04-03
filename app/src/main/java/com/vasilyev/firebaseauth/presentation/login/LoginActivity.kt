package com.vasilyev.firebaseauth.presentation.login

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.vasilyev.firebaseauth.R
import com.vasilyev.firebaseauth.databinding.ActivityLoginBinding
import com.vasilyev.firebaseauth.presentation.App
import com.vasilyev.firebaseauth.presentation.ViewModelFactory
import com.vasilyev.firebaseauth.presentation.main.MainActivity
import com.vasilyev.firebaseauth.presentation.register.RegistrationActivity
import javax.inject.Inject

class LoginActivity : AppCompatActivity() {
    private var _binding: ActivityLoginBinding? = null
    private val binding: ActivityLoginBinding
        get() = requireNotNull(_binding)

    @Inject
    lateinit var viewModelFactory: ViewModelFactory

    private val viewModel: LoginViewModel by viewModels {
        viewModelFactory
    }

    private val component by lazy {
        (application as App).component
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        component.inject(this)

        super.onCreate(savedInstanceState)
        _binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setListeners()
        observeState()
    }

    private fun setListeners(){
        binding.tvHaveAccount.setOnClickListener {
            startRegistrationActivity()
        }

        binding.btnSignIn.setOnClickListener {
            login()
        }
    }

    private fun login(){
        val email = binding.etEmail.text.toString()
        val password = binding.etPassword.text.toString()

        viewModel.login(email, password)
    }

    private fun observeState(){
        viewModel.loginState.observe(this){ state ->
            when(state){
                is LoginState.Success -> {
                    startMainActivity()
                }

                is LoginState.IncorrectEmailOrPasswordError -> {
                    showToast("Incorrect Email or Password")
                }

                is LoginState.EmptyFieldsError -> {
                    showToast("Please fill all fields")
                }

                is LoginState.UndefinedError -> {
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

    private fun startRegistrationActivity(){
        Intent(this, RegistrationActivity::class.java).apply {
            startActivity(this)
        }
    }



    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}