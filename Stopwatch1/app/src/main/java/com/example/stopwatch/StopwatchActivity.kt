package com.example.stopwatch

import android.annotation.SuppressLint
import android.app.Activity
import android.content.res.Configuration
import android.os.Build
import android.os.Bundle
import android.os.Handler
import android.view.View
import android.widget.TextView
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity

class StopwatchActivity : AppCompatActivity() {
    private var seconds = 0
    private var running = false
    private var wasRunning = false
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        if (resources.configuration.orientation == Configuration.ORIENTATION_LANDSCAPE) {
            supportActionBar?.hide();  //to hide action bar in land mode
        }

        if (savedInstanceState != null) {
            seconds = savedInstanceState.getInt("seconds")
            running = savedInstanceState.getBoolean("running")
            wasRunning = savedInstanceState.getBoolean("wasRunning")
        }
        runTimer()
    }

    public override fun onSaveInstanceState(savedInstanceState: Bundle) {
        super.onSaveInstanceState(savedInstanceState)
        savedInstanceState.putInt("seconds", seconds)
        savedInstanceState.putBoolean("running", running)
        savedInstanceState.putBoolean("wasRunning", wasRunning)
    }

    override fun onStop() {
        super.onStop()
        wasRunning = running
        running = false
    }

    override fun onStart() {
        super.onStart()
        if (wasRunning) {
            running = true
        }
    }

    //to activate stopwatch pushing start button
    fun onClickStart(view: View?) {
        running = true
    }

    //to stop stopwatch pushing stop button
    fun onClickStop(view: View?) {
        running = false
    }

    //to reset stopwatch pushing reset button
    fun onClickReset(view: View?) {
        running = false
        seconds = 0
    }

    private fun runTimer() {
        val timeView = findViewById<View>(R.id.time_view) as TextView
        val handler = Handler()
        handler.post(object : Runnable {
            override fun run() {
                val hours = seconds / 3600
                val minutes = seconds % 3600 / 60
                val secs = seconds % 60
                @SuppressLint("DefaultLocale") val time = String.format(
                    "%d:%02d:%02d",
                    hours, minutes, secs
                )
                timeView.text = time
                if (running) {
                    seconds++
                }
                handler.postDelayed(this, 1000)
            }
        })
    }
}