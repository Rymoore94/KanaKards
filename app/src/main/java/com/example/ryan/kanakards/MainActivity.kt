package com.example.ryan.kanakards

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import com.example.ryan.kanakards.CardMethods;
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val cardMethods = CardMethods()
        var currentKana = Kana()
        val frontSize = 180F
        val backSize = 60F
        var flipped = false

        currentKana = cardMethods.serveCard()

        kanaBox.text = currentKana.kana

        fun flipToBack(){
            flipped = true
            kanaBox.text = "" + currentKana.classification + "\n\n" + currentKana.roma
            kanaBox.textSize = backSize
        }

        fun flipToFront(){
            flipped = false
            kanaBox.text = currentKana.kana
            kanaBox.textSize = frontSize
        }

        kanaBox.setOnClickListener{
            if(!flipped) {
                flipToBack()
            }
            else{
                flipToFront()
            }
        }

        rightButt.setOnClickListener{
            cardMethods.removeFromPool() //TODO MAKE SURE THIS WORKS
            currentKana = cardMethods.serveCard()
            flipToFront()
        }

        wrongButt.setOnClickListener{
            cardMethods.popToBack() //TODO MAKE SURE THIS WORKS
            currentKana = cardMethods.serveCard()
            flipToFront()
        }

        testButt.setOnClickListener{ //TODO REMOVE THIS
            currentKana = cardMethods.serveCard()
            flipToFront()
        }


    }
}
