package com.example.database

import android.content.Intent
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

    companion object{
        const val KEY1="com.example.database.signinActivity.mail"
        const val KEY2="com.example.database.signinActivity.name"
        const val KEY3="com.example.database.signinActivity.username"

    }

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
            val uname = binding.etname.text.toString()
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

            databaseReference.child(uname).get().addOnSuccessListener {
//                if user exist or not
                if(it.exists()){
//                    welcome your User in your app and add intent
                    val name=it.child("name").value
                    val email=it.child("email").value
                    val username=it.child("username").value
                    val password=it.child("password").value
                    Toast.makeText(this@signinActivity,"Welcome $name",Toast.LENGTH_LONG).show()
                    val intent=Intent(this@signinActivity,HomeActivity::class.java)
                    startActivity(intent)
                    intent.putExtra(KEY2,name.toString())
                    intent.putExtra(KEY1,email.toString())
                    intent.putExtra(KEY3,username.toString())



                }else{
                    Toast.makeText(this@signinActivity,"User does not exist",Toast.LENGTH_LONG).show()
                }

            }.addOnFailureListener {
                Toast.makeText(this@signinActivity,"FAILED,Error in Database",Toast.LENGTH_LONG).show()
            }
    }
}