package com.example.database

import android.os.Bundle
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.database.databinding.ActivityMainBinding
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    lateinit var database: DatabaseReference
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding=ActivityMainBinding.inflate(layoutInflater)
        enableEdgeToEdge()
        setContentView(binding.root)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        binding.btnsignup.setOnClickListener {
            val email = binding.etmail.text.toString()
            val name = binding.etname.text.toString()
            val username = binding.etuname.text.toString()
            val password = binding.etpass.text.toString()

            val user=User(name,email,username,password)

            database= FirebaseDatabase.getInstance().getReference("Users")
            database.child(username).setValue(user).addOnSuccessListener {
                binding.etmail.text?.clear()
                binding.etname.text?.clear()
                binding.etuname.text?.clear()
                binding.etpass.text?.clear()

                Toast.makeText(this@MainActivity,"User Successfully Registered",Toast.LENGTH_LONG).show()
            }

        }
    }
}