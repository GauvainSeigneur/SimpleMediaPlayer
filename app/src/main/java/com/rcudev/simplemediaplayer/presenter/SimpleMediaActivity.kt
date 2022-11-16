package com.rcudev.simplemediaplayer.presenter

import android.os.Bundle
import androidx.activity.viewModels
import androidx.fragment.app.FragmentActivity
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class SimpleMediaActivity : FragmentActivity() {

    private val viewModel: SimpleMediaViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel.startService(this)
        viewModel.onUIEvent(UIEvent.PlayPause)
    }

    override fun onDestroy() {
        super.onDestroy()
        viewModel.stopService(this)

    }

}