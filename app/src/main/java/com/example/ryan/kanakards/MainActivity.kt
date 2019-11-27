package com.example.ryan.kanakards

import android.content.Intent
import android.content.pm.ActivityInfo
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import kotlinx.android.synthetic.main.activity_main.*
import android.support.v4.app.SupportActivity
import android.support.v4.app.SupportActivity.ExtraData
import android.support.v4.content.ContextCompat.getSystemService
import android.icu.lang.UCharacter.GraphemeClusterBreak.T


var quickSettings = SettingsPacket()
class MainActivity : AppCompatActivity() {
    val SETTING_REQUEST = 1
    var saver = save(this)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_PORTRAIT
        setContentView(R.layout.activity_main)
        saver = save(applicationContext)
        quickSettings = saver.load()

        toExperimental.setOnClickListener{  //Goes to experimental screen
            val intent = Intent(this, ExperimentalFeatures::class.java)
            startActivity(intent)
        }

        toSettings.setOnClickListener { //Goes to settings screen
            val intent = Intent(this, SettingsScreen::class.java)
            intent.putExtra("hiraCheck", quickSettings.hira)
            intent.putExtra("kataCheck", quickSettings.kata)
            intent.putExtra("customCheck", quickSettings.custom)
            intent.putExtra("fileName", quickSettings.filename)
            intent.putExtra("customNames", quickSettings.customNames)
            startActivityForResult(intent, SETTING_REQUEST)
        }

        toHira.setOnClickListener { //Goes to card viewer with only Hira
            val intent = Intent(this, CardViewer::class.java)
            intent.putExtra("toLoad", "hira")
            intent.putExtra("isCustom", false)
            startActivity(intent)
        }

        toKata.setOnClickListener { //Goes to card viewer with only Kata
            val intent = Intent(this, CardViewer::class.java)
            intent.putExtra("toLoad", "kata")
            intent.putExtra("isCustom", false)
            startActivity(intent)
        }

        toQuick.setOnClickListener {    //Goes to card viewer with settings applied
            val intent = Intent(this, CardViewer::class.java)
            if((quickSettings.hira) || (quickSettings.kata)) {
                if ((quickSettings.hira) && (quickSettings.kata))
                    intent.putExtra("toLoad", "both")
                else if (quickSettings.hira)
                    intent.putExtra("toLoad", "hira")
                else if (quickSettings.kata)
                    intent.putExtra("toLoad", "kata")
                intent.putExtra("isCustom", quickSettings.custom)
                startActivity(intent)
            }
            else if(quickSettings.custom){
                intent.putExtra("isCustom", quickSettings.custom)
                intent.putExtra("toLoad", quickSettings.filename)
                startActivity(intent)
            }
            else
                Toast.makeText(this, "No Quick Start has been set", Toast.LENGTH_SHORT).show()
        }

        toBrowser.setOnClickListener {
            val intent = Intent(this, CharacterBrowser::class.java)
            if((quickSettings.hira) || (quickSettings.kata)) {
                if ((quickSettings.hira) && (quickSettings.kata))
                    intent.putExtra("toLoad", "both")
                else if (quickSettings.hira)
                    intent.putExtra("toLoad", "hira")
                else if (quickSettings.kata)
                    intent.putExtra("toLoad", "kata")
                intent.putExtra("isCustom", quickSettings.custom)
                startActivity(intent)
            }
            else if(quickSettings.custom){
                intent.putExtra("isCustom", quickSettings.custom)
                intent.putExtra("toLoad", quickSettings.filename)
                startActivity(intent)
            }
            else
                Toast.makeText(this, "No Character Set has been set to browse", Toast.LENGTH_SHORT).show()
        }
    }
   override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        if (requestCode == SETTING_REQUEST) {
            if (resultCode == SettingsScreen.RESULT_OK) {
                Toast.makeText(this, "Saved", Toast.LENGTH_SHORT).show()
                saver.save(SettingsPacket((data!!.getBooleanExtra("hiraCheck", true)),
                    data.getBooleanExtra("kataCheck", true),
                    data.getBooleanExtra("customCheck", false),
                    data.getStringExtra("fileName"),
                    data.getStringExtra("customNames")))
                quickSettings = saver.load()
            }
        }
    }
}
