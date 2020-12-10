package com.llama.rick_and_morty_mvvm

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.llama.rick_and_morty_mvvm.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(ActivityMainBinding.inflate(layoutInflater).root)
    }
}