package com.example.navigation

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.navigation.databinding.ActivityBoxBinding

class BoxActivity : BaseActivity() {
    private lateinit var binding: ActivityBoxBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding= ActivityBoxBinding.inflate(layoutInflater).also { setContentView(it.root) }

        binding.toMainMenuButton.setOnClickListener { onToMainMenuPressed() }
    }
    private fun onToMainMenuPressed(){
        val intent=Intent(this,MenuActivity::class.java)
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP or Intent.FLAG_ACTIVITY_SINGLE_TOP)
        startActivity(intent)
    }
}