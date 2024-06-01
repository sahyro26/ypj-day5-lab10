package com.sahyro.lab10

import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.google.android.material.snackbar.Snackbar
import com.google.firebase.auth.FirebaseAuth
import com.sahyro.lab10.databinding.ActivityLoginBinding
import com.sahyro.lab10.databinding.ActivityMainBinding

class LoginActivity : AppCompatActivity() {
    private lateinit var binding: ActivityLoginBinding

    private lateinit var auth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLoginBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

        auth = FirebaseAuth.getInstance()

        binding.loginLoginBtn.setOnClickListener {
            if(binding.loginEmailEditText.text.trim().toString().isNotEmpty() && binding.loginPasswordEditText.text.trim().toString().isNotEmpty()){
                loginUser(binding.loginEmailEditText.text.trim().toString(), binding.loginPasswordEditText.text.trim().toString())
            }
            else{
                Snackbar.make(binding.root, "Please fill in your username and password", Snackbar.LENGTH_LONG)
                    .setBackgroundTint(Color.DKGRAY)
                    .show()
            }



        }
    }

    fun loginUser(email:String, password:String){
        auth.signInWithEmailAndPassword(email,password).addOnCompleteListener(this){
                task->
            if(task.isSuccessful){
                val intent = Intent(this, ServiceActivity::class.java)
                startActivity(intent)
            }
            else{
                Snackbar.make(binding.root, "Please check your username and password", Snackbar.LENGTH_LONG)
                    .setBackgroundTint(Color.DKGRAY)
                    .show()

            }
        }
    }
}