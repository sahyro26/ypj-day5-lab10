package com.sahyro.lab10

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.Firebase
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.firestore
import com.sahyro.lab10.databinding.ActivityServiceBinding

class ServiceActivity : AppCompatActivity() {
    private lateinit var binding: ActivityServiceBinding
    private lateinit var db: FirebaseFirestore
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityServiceBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

        db = Firebase.firestore
        readFirestoreData()
    }

    fun readFirestoreData(){
        db.collection("customers")
            .get()
            .addOnCompleteListener {
                val result:StringBuffer = StringBuffer()
                    if(it.isSuccessful){
                        for (document in it.result!!){
                            val city = document.getString("city") ?: "N/A"
                            val country = document.getString("country") ?: "N/A"
                            val name = document.getString("name") ?: "N/A"
                            val phone = document.getString("phone") ?: "N/A"

                            result.append("Name: ").append(name).append("\n")
                                .append("Phone: ").append(phone).append("\n")
                                .append("City: ").append(city).append("\n")
                                .append("COuntry: ").append(country).append("\n")

                        }
                    }

                binding.resultTxtView.text = result.toString().trim()
            }
//            .addOnSuccessListener{
//                result -> 
//                if(result!= null){
//                    Log.d("DEBUG", "User ID is ${result.user.uid}")
//                }
//            }
    }
}