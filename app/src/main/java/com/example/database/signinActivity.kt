package com.example.database

import android.os.Bundle
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.database.databinding.ActivityMainBinding
import com.example.database.databinding.ActivitySigninBinding
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase

class signinActivity : AppCompatActivity() {
    private lateinit var binding: ActivitySigninBinding
    lateinit var databaseReference: DatabaseReference
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding=ActivitySigninBinding.inflate(layoutInflater)
        enableEdgeToEdge()
        setContentView(binding.root)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        binding.signin.setOnClickListener {
//            Take ref till node "Users"
            val uname = binding.uname.text.toString()
            val pass = binding.etpass.text.toString()
            if(uname.isNotEmpty() && pass.isNotEmpty()){
                readData(uname,pass)
            }else{
                Toast.makeText(this@signinActivity,"Please fill all fields",Toast.LENGTH_LONG).show()
            }

        }
}

    private fun readData(uname: String, pass: String) {
            databaseReference = FirebaseDatabase.getInstance().getReference("Users")

            databaseReference.child(uname).child(pass).get().addOnSuccessListener {
//                if user exist or not
                if(it.exists()){
//                    welcome your User in your app and add intent
                    val name=it.child("name").value
                    val email=it.child("email").value
                    val username=it.child("username").value
                    val password=it.child("password").value
                    Toast.makeText(this@signinActivity,"Welcome $name",Toast.LENGTH_LONG).show()
                    binding.tv1.text=name.toString()

                    binding.uname.text?.clear()
                    binding.etpass.text?.clear()
                }else{
                    Toast.makeText(this@signinActivity,"User does not exist",Toast.LENGTH_LONG).show()
                }

            }.addOnFailureListener {
                Toast.makeText(this@signinActivity,"FAILED",Toast.LENGTH_LONG).show()
            }
    }
}