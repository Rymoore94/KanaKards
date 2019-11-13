package com.example.ryan.kanakards

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        toExperimental.setOnClickListener{  //Goes to experimental screen
            val intent = Intent(this, ExperimentalFeatures::class.java)
            /*USE THIS IF YOU NEED TO PASS INFO INSIDE INTENT
            intent.putExtra("IDENTIFIER", VALUE)
             */
            startActivity(intent)
        }

        toSettings.setOnClickListener { //Goes to settings screen
            val intent = Intent(this, SettingsScreen::class.java)
            /*USE THIS IF YOU NEED TO PASS INFO INSIDE INTENT
            intent.putExtra("IDENTIFIER", VALUE)
             */
            startActivity(intent)
        }

        toHira.setOnClickListener { //Goes to card viewer with only Hira
            val intent = Intent(this, CardViewer::class.java)
            intent.putExtra("toLoad", "hira")
            startActivity(intent)
        }

        toKata.setOnClickListener { //Goes to card viewer with only Kata
            val intent = Intent(this, CardViewer::class.java)
            intent.putExtra("toLoad", "kata")
            startActivity(intent)
        }

        toQuick.setOnClickListener {    //Goes to card viewer with settings applied
            val intent = Intent(this, CardViewer::class.java)
            intent.putExtra("toLoad", "both")
            startActivity(intent)
        }


    }
}
