package com.example.beatwell.ui.login

import android.animation.AnimatorSet
import android.animation.ObjectAnimator
import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import com.example.beatwell.MainActivity
import com.example.beatwell.R
import com.example.beatwell.ui.ViewModelFactory
import com.example.beatwell.data.Result
import com.example.beatwell.databinding.ActivityLoginBinding
import com.example.beatwell.ui.register.RegisterActivity

class LoginActivity : AppCompatActivity() {

    private val viewModel: SignInViewModel by viewModels {
        ViewModelFactory.getInstance(this)
    }
    private lateinit var binding: ActivityLoginBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setAnimation()

        binding.btnSignIn.setOnClickListener {
            val email = binding.edLoginEmail.text.toString()
            val password = binding.edLoginPassword.text.toString()
            login(email, password)
        }
        binding.textSignup.setOnClickListener {
            startActivity(Intent(this, RegisterActivity::class.java))
        }

    }

    private fun setAnimation() {
        ObjectAnimator.ofFloat(binding.ivHeart, View.TRANSLATION_X, -30f, 30f).apply {
            duration = 6000
            repeatCount = ObjectAnimator.INFINITE
            repeatMode = ObjectAnimator.REVERSE
        }.start()
        val tagline = ObjectAnimator.ofFloat(binding.appTagline, View.ALPHA, 1f).setDuration(500)
        val email = ObjectAnimator.ofFloat(binding.textEmail, View.ALPHA, 1f).setDuration(500)
        val emailLayout = ObjectAnimator.ofFloat(binding.emailLayout, View.ALPHA, 1f).setDuration(500)
        val password = ObjectAnimator.ofFloat(binding.textPassword, View.ALPHA, 1f).setDuration(500)
        val passwordLayout = ObjectAnimator.ofFloat(binding.passwordLayout, View.ALPHA, 1f).setDuration(500)
        val login = ObjectAnimator.ofFloat(binding.btnSignIn, View.ALPHA, 1f).setDuration(500)
        val signup = ObjectAnimator.ofFloat(binding.textSignup, View.ALPHA, 1f).setDuration(500)

        val together = AnimatorSet().apply {
            playTogether(email, emailLayout, password, passwordLayout)
            start()
        }

        AnimatorSet().apply {
            playSequentially(tagline, together, login, signup)
            start()
        }
    }

    private fun login(email: String, password: String) {
        viewModel.login(email, password).observe(this) { result ->
            when (result) {
                is Result.Success -> {
                    binding.progressBar.visibility = View.GONE
                    startActivity(Intent(this, MainActivity::class.java))
                    showToast(getString(R.string.text_welcome))
                    finish()
                }
                is Result.Error -> {
                    binding.progressBar.visibility = View.GONE
                    binding.textError.visibility = View.VISIBLE
                    showToast(result.error)
                    binding.textError.text = getString(R.string.text_login_error)
                }
                is Result.Loading -> {
                    binding.progressBar.visibility = View.VISIBLE
                }
            }
        }
    }
    private fun showToast(message: String){
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
    }
}