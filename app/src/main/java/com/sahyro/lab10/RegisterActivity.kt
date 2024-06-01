package com.sahyro.lab10

import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.snackbar.Snackbar
import com.google.firebase.Firebase
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.firestore
import com.sahyro.lab10.databinding.ActivityRegisterBinding

class RegisterActivity : AppCompatActivity() {
    private lateinit var binding: ActivityRegisterBinding

    private lateinit var auth: FirebaseAuth
    private lateinit var db: FirebaseFirestore

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityRegisterBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

        auth = FirebaseAuth.getInstance()

        binding.regsRegisterBtn.setOnClickListener {
            if(binding.regsEmailEditText.text.trim().toString().isNotEmpty() && binding.regsPasswordEditText.text.trim().toString().isNotEmpty()){
                createUser(binding.regsEmailEditText.text.trim().toString(), binding.regsPasswordEditText.text.trim().toString())
            }
            else{
                Snackbar.make(binding.root, "Please fill in your username and password", Snackbar.LENGTH_LONG)
                    .setBackgroundTint(Color.DKGRAY)
                    .show()
            }

//            val intent = Intent(this, ThankyouActivity::class.java)
//            startActivity(intent)
        }
        db = Firebase.firestore
    }

    fun createUser(email: String, password: String){
        auth.createUserWithEmailAndPassword(email,password)
            .addOnCompleteListener(this){ task ->
            if(task.isSuccessful){
                newCustomer()
                startActivity(Intent(this, ThankyouActivity::class.java))
            }
            else{
                Snackbar.make(
                 binding.root,
                    "Register unseccessful",
                    Snackbar.LENGTH_LONG
                ).show()
            }
        }
    }

    private fun newCustomer(){
        val customer = hashMapOf(
            "name" to binding.regsNameEditText.text.toString().trim(),
            "city" to binding.regsCityEditText.text.toString().trim(),
            "country" to binding.regsCountryEditText.text.toString().trim(),
            "phone" to binding.regsPhoneEditText.text.toString().trim(),
            "email" to binding.regsEmailEditText.text.toString().trim(),
        )
        db.collection( "customers")
            .add(customer)
            .addOnSuccessListener {
                documentReference ->
                Log.d("debug", "Document successfully added with id ${documentReference.id}")
            }.addOnFailureListener { e->
                Log.d("debug", "An error happen ${e.message}")
            }
    }
}