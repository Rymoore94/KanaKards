package com.example.ryan.kanakards

import android.content.Intent
import android.content.pm.ActivityInfo
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_main.*


var quickSettings = SettingsPacket()

class MainActivity : AppCompatActivity() {
    private val SETTING_REQUEST = 1
    private var saver = Save(this)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_PORTRAIT
        setContentView(R.layout.activity_main)
        saver = Save(applicationContext)
        quickSettings = saver.load()

        toSettings.setOnClickListener {
            //Goes to settings screen
            val intent = Intent(this, SettingsScreen::class.java)
            intent.putExtra("hiraCheck", quickSettings.hira)
            intent.putExtra("kataCheck", quickSettings.kata)
            intent.putExtra("customCheck", quickSettings.custom)
            intent.putExtra("traceCheck", quickSettings.trace)
            intent.putExtra("voiceCheck", quickSettings.voice)
            intent.putExtra("fileName", quickSettings.filename)
            intent.putExtra("customNames", quickSettings.customNames)
            startActivityForResult(intent, SETTING_REQUEST)
        }

        toHira.setOnClickListener {
            //Goes to card viewer with only Hira
            val intent = Intent(this, CardViewer::class.java)
            intent.putExtra("toLoad", "hira")
            intent.putExtra("isCustom", false)
            startActivity(intent)
        }

        toKata.setOnClickListener {
            //Goes to card viewer with only Kata
            val intent = Intent(this, CardViewer::class.java)
            intent.putExtra("toLoad", "kata")
            intent.putExtra("isCustom", false)
            startActivity(intent)
        }

        toQuick.setOnClickListener {
            val intent: Intent
            if (quickSettings.trace)
                intent = Intent(this, TraceViewer::class.java)
            else if (quickSettings.voice)
                intent = Intent(this, VoiceViewer::class.java)
            else
                intent = Intent(this, CardViewer::class.java)
            if ((quickSettings.hira) || (quickSettings.kata)) {
                if ((quickSettings.hira) && (quickSettings.kata))
                    intent.putExtra("toLoad", "both")
                else if (quickSettings.hira)
                    intent.putExtra("toLoad", "hira")
                else if (quickSettings.kata)
                    intent.putExtra("toLoad", "kata")
                intent.putExtra("isCustom", quickSettings.custom)
                startActivity(intent)
            } else if (quickSettings.custom) {
                intent.putExtra("isCustom", quickSettings.custom)
                intent.putExtra("toLoad", quickSettings.filename)
                startActivity(intent)
            } else
                Toast.makeText(this, "No Quick Start has been set", Toast.LENGTH_SHORT).show()
        }

        toBrowser.setOnClickListener {
            val intent = Intent(this, CharacterBrowser::class.java)
            if ((quickSettings.hira) || (quickSettings.kata)) {
                if ((quickSettings.hira) && (quickSettings.kata))
                    intent.putExtra("toLoad", "both")
                else if (quickSettings.hira)
                    intent.putExtra("toLoad", "hira")
                else if (quickSettings.kata)
                    intent.putExtra("toLoad", "kata")
                intent.putExtra("isCustom", quickSettings.custom)
                startActivity(intent)
            } else if (quickSettings.custom) {
                intent.putExtra("isCustom", quickSettings.custom)
                intent.putExtra("toLoad", quickSettings.filename)
                startActivity(intent)
            } else
                Toast.makeText(
                    this,
                    "No Character Set has been set to browse",
                    Toast.LENGTH_SHORT
                ).show()
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        if (requestCode == SETTING_REQUEST) {
            if (resultCode == SettingsScreen.RESULT_OK) {
                Toast.makeText(this, "Saved", Toast.LENGTH_SHORT).show()
                saver.save(
                    SettingsPacket(
                        (data!!.getBooleanExtra("hiraCheck", true)),
                        data.getBooleanExtra("kataCheck", true),
                        data.getBooleanExtra("customCheck", false),
                        data.getBooleanExtra("traceCheck", false),
                        data.getBooleanExtra("voiceCheck", false),
                        data.getStringExtra("fileName"),
                        data.getStringExtra("customNames")
                    )
                )
                quickSettings = saver.load()
            }
        }
    }
}
