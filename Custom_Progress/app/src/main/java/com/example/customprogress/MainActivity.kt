package com.example.customprogress

import android.animation.ValueAnimator
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.SeekBar

class MainActivity : AppCompatActivity() {
    private lateinit var circularProgressView: CustomView
    private lateinit var seek: SeekBar
    private lateinit var valueAnimator: ValueAnimator
    private var count: Float = 0F

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        circularProgressView = findViewById(R.id.circularProgressView)
        seek = findViewById(R.id.seekBar)
        seek.max = 100
        valueAnimator = ValueAnimator()
        seek.setOnSeekBarChangeListener(object : SeekBar.OnSeekBarChangeListener {
            override fun onProgressChanged(seekBar: SeekBar?, progress: Int, fromUser: Boolean) {
                count = progress.toFloat() / 100f
                startProgressAnimation(count)
            }

            override fun onStartTrackingTouch(seekBar: SeekBar?) {}

            override fun onStopTrackingTouch(seekBar: SeekBar?) {}
        })
    }

    private fun startProgressAnimation(targetProgress: Float) {
        valueAnimator.cancel()
        valueAnimator = ValueAnimator.ofFloat(count, targetProgress).apply {
            duration = 300
            addUpdateListener { animator ->
                val progress = animator.animatedValue as Float
                circularProgressView.setProgress(progress)
            }
        }
        valueAnimator.start()
    }
}