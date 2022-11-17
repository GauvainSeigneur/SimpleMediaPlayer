package com.rcudev.simplemediaplayer.presenter

import android.os.Bundle
import android.widget.Button
import androidx.activity.viewModels
import androidx.fragment.app.FragmentActivity
import com.rcudev.simplemediaplayer.R

class SimpleMediaActivity : FragmentActivity() {

    private val viewModel: SimpleMediaViewModel by viewModels()

    private lateinit var buttonOne : Button
    private lateinit var buttonTwo: Button
    private lateinit var buttonPlay: Button


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_simple_media)
        buttonOne = findViewById(R.id.button_track_one)
        buttonTwo = findViewById(R.id.button_track_two)
        buttonPlay = findViewById(R.id.button_play)

        buttonOne.setOnClickListener {
            viewModel.loadTrackOne()
        }
        buttonTwo.setOnClickListener {

            viewModel.loadTrackTwo()
        }
        buttonPlay.setOnClickListener {
            viewModel.playPause()
        }


        //viewModel.start()
    }

}