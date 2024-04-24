package com.example.l1z2_2

import android.graphics.Color
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import kotlin.random.Random

class MainActivity : AppCompatActivity() {
    var numb = 0
    private lateinit var number: TextView
    private lateinit var comm: TextView
    private lateinit var user: EditText
    private lateinit var game: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        number = findViewById(R.id.num)
        comm = findViewById(R.id.tip)
        user = findViewById(R.id.user)
        game = findViewById(R.id.restart)
        roll()
    }

    fun checkNum(view: View){
        if (user.text.toString().equals("")) {
            Toast.makeText(this, "Podaj liczbę!!!", Toast.LENGTH_SHORT).show()
        } else {
            val temp: Int = user.text.toString().toInt()
            if (temp > numb) {
                comm.text="Podana liczba jest większa od oczekiwanej!"
                comm.setShadowLayer(50f, 1f, 1f, Color.RED)
                user.setBackgroundColor(Color.RED)
            }
            if (temp < numb) {
                comm.text="Podana liczba jest mniejsza od oczekiwanej!"
                comm.setShadowLayer(50f, 1f, 1f, Color.RED)
                user.setBackgroundColor(Color.RED)
            }
            if (temp == numb) {
                comm.text="Gratulacja!!! Wygrałeś grę!!!"
                number.text="" + numb
                game.isEnabled=true
                comm.setShadowLayer(50f, 1f, 1f, Color.GREEN)
                user.setBackgroundColor(Color.GREEN)
                user.isEnabled=false
            }
        }
    }

    fun newGame(view: View){
        roll()
        user.setText("")
        comm.setShadowLayer(50f, 1f, 1f, Color.TRANSPARENT)
        user.setBackgroundColor(Color.TRANSPARENT)
        user.isEnabled=true
        game.isEnabled=false
    }

    private fun roll() {
        numb = Random.nextInt(100)
        number.text="?"
        comm.text="Podaj liczbę"
        game.isEnabled=false
    }
}