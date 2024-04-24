package com.example.wisieliec

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.View
import android.widget.EditText
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import kotlin.random.Random


class MainActivity : AppCompatActivity() {
    private lateinit var image: ImageView
    private lateinit var word: TextView
    private lateinit var edit: EditText
    private lateinit var arr1: CharArray
    private lateinit var arr2: CharArray
    private var a = 0
    private var b = ""
    private var c = ""
    private var i = 1
    private var number = 0
    private var size = 0
    private val imageIds = arrayOf(
        R.drawable.a1,
        R.drawable.a2,
        R.drawable.a3,
        R.drawable.a4,
        R.drawable.a5,
        R.drawable.a6,
        R.drawable.a7,
        R.drawable.a8
    )

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        image = findViewById(R.id.imageView)
        word = findViewById(R.id.textView)
        edit = findViewById(R.id.edit1)
        game()
    }

    private fun game(){
        i=1
        image.setBackgroundResource(imageIds[i-1])
        size = resources.getStringArray(R.array.words).size
        number = Random.nextInt(size)
        word.text = resources.getStringArray(R.array.words)[number]
        a = resources.getStringArray(R.array.words)[number].length
        b = "_ ".repeat(a)
        c = resources.getStringArray(R.array.words)[number]
        word.text = b
        arr1 = CharArray(a)
        for (i in c.indices) {
            arr1[i] = c[i]
        }
        b = b.replace(" ","")
        arr2 = CharArray(b.length)
        for (i in b.indices) {
            arr2[i] = b[i]
        }
    }

    @SuppressLint("SuspiciousIndentation")
    fun checkChar(view: View) {
        if(edit.text.isEmpty()){
            Toast.makeText(this, "Podaj literę", Toast.LENGTH_SHORT).show()
        }
        else{
            if(c.contains(edit.text.toString())){
                val text = edit.text.toString()[0]
                var index = c.indexOf(text)
                if (index != -1) {
                    arr2[index] = text
                    while (index != -1) {
                        index = c.indexOf(text, index + 1)
                        if(index != -1)
                        arr2[index] = text
                    }
                }
                word.text = arr2.joinToString(separator = " ")
                edit.text.clear()
                if (!word.text.toString().contains('_')){
                        Toast.makeText(this, "You WIN!", Toast.LENGTH_SHORT).show()
                        edit.text.clear()
                        game()
                }
            }
            else{
                if(i<8) {
                    i++
                    image.setBackgroundResource(imageIds[i-1])
                    edit.text.clear()
                    if(i==8)
                    {Toast.makeText(this, "GAME OVER", Toast.LENGTH_SHORT).show()
                    game()}
                }
                else{
                    Toast.makeText(this, "GAME OVER", Toast.LENGTH_SHORT).show()
                    edit.text.clear()
                    game()
                }
            }
        }
    }


}