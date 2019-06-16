package com.esmaeel.esviewsfactory

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.indicator.view.*

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        myCustomDistanceSeekBar.doTheMagicIn(this)
        myCustomRangeSeekBar.doTheMagicIn(this)
    }
}
