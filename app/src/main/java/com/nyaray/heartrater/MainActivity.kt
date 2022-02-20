package com.nyaray.heartrater

import android.app.Activity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.TextView
import com.nyaray.heartrater.databinding.ActivityMainBinding
import java.time.Instant.now
import kotlin.math.roundToInt

class MainActivity : Activity() {

    private var beatCount: Int = 0
    private var elapsedTime: Long = 0
    private var startTime: Long = 0
    private var heartRate: Int = 0

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val beatButton = findViewById<Button>(R.id.beat)
        val resetButton = findViewById<Button>(R.id.reset)

        val beatCountView = findViewById<TextView>(R.id.beatCount)
        val beats = findViewById<TextView>(R.id.beats)
        val secondCountView = findViewById<TextView>(R.id.secondCount)
        val seconds = findViewById<TextView>(R.id.seconds)
        val heartRateView = findViewById<TextView>(R.id.hr)

        val refreshViews = {
            beatCountView.text = "$beatCount"
            secondCountView.text = "${elapsedTime/1000}"
            heartRateView.text = "$heartRate"

            beats.text = resources.getQuantityString(R.plurals.beatCount, beatCount, beatCount)
        }

        beatButton.setOnClickListener {
            val nowMillis = now().toEpochMilli()

            if (startTime == 0L) {
                startTime = nowMillis
            }

            elapsedTime = nowMillis - startTime

            if (elapsedTime > 0) {
                val timeFactor = 60.0 / (elapsedTime.toDouble() / 1000)
                beatCount++

                heartRate = (timeFactor * beatCount).roundToInt()
            }

            refreshViews()
            Log.d("beatButton", "clickListener $beatCount")
        }

        resetButton.setOnClickListener {
            beatCount = 0

            startTime = 0
            elapsedTime = 0

            heartRate = 0

            refreshViews()
        }
    }
}