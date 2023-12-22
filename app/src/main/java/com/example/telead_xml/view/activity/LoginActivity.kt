package com.example.telead_xml.view.activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.telead_xml.R
import com.example.telead_xml.databinding.ActivityLoginBinding
import com.example.telead_xml.view.fragment.LetsYouInFragment

class LoginActivity : AppCompatActivity() {

    private lateinit var binding: ActivityLoginBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLoginBinding.inflate(layoutInflater)
        selectFragment()
        setContentView(binding.root)
    }

    private fun selectFragment() {
        supportFragmentManager.beginTransaction()
            .add(R.id.login_fragment_container, LetsYouInFragment())
            .commit()
    }
}