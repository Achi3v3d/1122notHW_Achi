package com.example.first_k_app

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.first_k_app.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    //binding
    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
    }

    override fun onResume() {
        super.onResume()

        binding.button.setOnClickListener{
                Intent(baseContext, MainActivity2::class.java).apply {
                    //DATA IS THE KEY
                putExtra("DATA", binding.EditText.text.toString())
                startActivity(this)
            }

        }

    }

    override fun onStart() {
        super.onStart()

    }
}