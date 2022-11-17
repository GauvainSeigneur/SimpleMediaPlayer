package com.rcudev.simplemediaplayer.presenter

import android.os.Bundle
import androidx.activity.viewModels
import androidx.fragment.app.FragmentActivity

class SimpleMediaActivity : FragmentActivity() {

    private val viewModel: SimpleMediaViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel.onUIEvent(UIEvent.PlayPause)
    }

}