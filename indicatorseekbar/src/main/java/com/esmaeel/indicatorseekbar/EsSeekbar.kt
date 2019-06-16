package com.esmaeel.indicatorseekbar

import android.content.Context
import android.graphics.Bitmap
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.graphics.drawable.BitmapDrawable
import android.graphics.drawable.Drawable
import android.text.TextPaint
import android.util.AttributeSet
import android.view.LayoutInflater
import android.view.View
import android.widget.LinearLayout
import android.widget.SeekBar
import android.widget.TextView
import kotlinx.android.synthetic.main.es_seekbar_layout.view.*

/**
 * TODO: document your custom view class.
 */
class EsSeekBar : LinearLayout {



    //This is Master
    var indicatorPrefix : String? = null
    var indicatorView : View? = null


    constructor(context: Context) : super(context) {
        init(null, 0)
    }

    constructor(context: Context, attrs: AttributeSet) : super(context, attrs) {
        init(attrs, 0)
    }

    constructor(context: Context, attrs: AttributeSet, defStyle: Int) : super(context, attrs, defStyle) {
        init(attrs, defStyle)
    }

    private fun init(attrs: AttributeSet?, defStyle: Int) {
        // Load attributes
        LayoutInflater.from(context).inflate(R.layout.es_seekbar_layout, this)
    }

    fun doTheMagicIn(context: Context , indicatorLayout : Int = R.layout.indicator , indicatorPrefix: String = "KM"){
        //making the background invisible
        indicatorView = LayoutInflater.from(context).inflate(indicatorLayout, null, false)
        this.indicatorPrefix = indicatorPrefix
        distanceSeekBar.setOnSeekBarChangeListener(object : SeekBar.OnSeekBarChangeListener {
            override fun onProgressChanged(seekBar: SeekBar, progressValue: Int, b: Boolean) {
                if (b) {
                    seekBarIndicator.progress = progressValue
                }
            }

            override fun onStartTrackingTouch(seekBar: SeekBar) {

            }

            override fun onStopTrackingTouch(seekBar: SeekBar) {

            }
        })
        seekBarIndicator.setOnSeekBarChangeListener(object :SeekBar.OnSeekBarChangeListener{
            override fun onProgressChanged(seekBar: SeekBar?, progressValue: Int, p2: Boolean) {
                seekBar!!.thumb = getThumb(progressValue, indicatorView!!)
                distanceSeekBar.progress = progressValue
            }
            override fun onStartTrackingTouch(p0: SeekBar?) {
            }
            override fun onStopTrackingTouch(p0: SeekBar?) {
            }
        })
        // to invalidate the change of the thumb
        seekBarIndicator.progress = seekBarIndicator.progress + 1
    }



    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)
        seekBarIndicator.progress = 50
        distanceSeekBar.progress = seekBarIndicator.progress
    }

    override fun onWindowFocusChanged(hasWindowFocus: Boolean) {
        super.onWindowFocusChanged(hasWindowFocus)
        val padding = indicatorView!!.width / 2
        distanceSeekBar.max = 100
        seekBarIndicator.max = 100
        seekBarIndicator.setPadding(padding / 2, 0, padding + ( padding /2), 0)
        distanceSeekBar.setPadding(padding / 2, 0, padding + ( padding ), 0)

    }

    fun getThumb(progress: Int, indicator: View): Drawable {
        if (indicatorPrefix.isNullOrEmpty()){
            (indicator.findViewById<View>(R.id.progress_text) as TextView).text = "$progress KM"
        }else{
            (indicator.findViewById<View>(R.id.progress_text) as TextView).text = "$progress $indicatorPrefix"
        }
        indicator.measure(View.MeasureSpec.UNSPECIFIED, View.MeasureSpec.UNSPECIFIED)
        val bitmap = Bitmap.createBitmap(indicator.measuredWidth, indicator.measuredHeight, Bitmap.Config.ARGB_8888)
        val canvas = Canvas(bitmap)
        indicator.layout(0, 0, indicator.measuredWidth, indicator.measuredHeight)
        indicator.draw(canvas)

        return BitmapDrawable(resources, bitmap)
    }

}

