package com.example.ryan.kanakards

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import com.example.ryan.kanakards.CardMethods;

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val cardMethods = CardMethods()


    }
}
