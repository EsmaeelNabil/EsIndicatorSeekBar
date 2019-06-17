package com.esmaeel.indicatorseekbar


import android.content.Context
import android.graphics.*
import android.graphics.drawable.BitmapDrawable
import android.graphics.drawable.Drawable
import android.util.AttributeSet
import android.view.LayoutInflater
import android.view.View
import android.widget.LinearLayout
import android.widget.SeekBar
import android.widget.TextView
import kotlinx.android.synthetic.main.es_seekbar_layout.view.*

class EsSeekBar : LinearLayout {

    var indicatorPrefix : String? = null
    var indicatorView : View? = null
    private var IS_PREFIX_POSITION_START :Boolean = false

    companion object {
        const val WHITE :String = "WHITE"
        const val BLACK :String = "BLACK"
        const val CUSTOM_LAYOUT :String  ="CUSTOM_COLOR"
    }

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
        LayoutInflater.from(context).inflate(R.layout.es_seekbar_layout, this)
    }

    fun doTheMagicIn(context: Context
                     ,listener: (progress :Int) -> Unit
                     , indicatorBackground : String = BLACK
                     , indicatorPrefix: String = ""
                     , prefixPositionStart :Boolean = false
                     , indicatorLayoutResource : Int = R.layout.indicator_black
    ) {
        indicatorView = when(indicatorBackground){
            BLACK -> LayoutInflater.from(context).inflate(R.layout.indicator_black, null, false)
            WHITE -> LayoutInflater.from(context).inflate(R.layout.indicator_white, null, false)
            CUSTOM_LAYOUT -> LayoutInflater.from(context).inflate(indicatorLayoutResource, null, false)
            else -> LayoutInflater.from(context).inflate(R.layout.indicator_black, null, false)
        }

        this.indicatorPrefix = indicatorPrefix
        this.IS_PREFIX_POSITION_START = prefixPositionStart

        distanceSeekBar.setOnSeekBarChangeListener(object : SeekBar.OnSeekBarChangeListener {
            override fun onProgressChanged(seekBar: SeekBar, progressValue: Int, b: Boolean) {
                if (b) {
                    seekBarIndicator.progress = progressValue
                    listener(progressValue)
                }
            }

            override fun onStartTrackingTouch(seekBar: SeekBar) {}

            override fun onStopTrackingTouch(seekBar: SeekBar) {}
        })
        seekBarIndicator.setOnSeekBarChangeListener(object :SeekBar.OnSeekBarChangeListener{
            override fun onProgressChanged(seekBar: SeekBar?, progressValue: Int, p2: Boolean) {
                seekBar!!.thumb = getThumb(progressValue, indicatorView!!)
                distanceSeekBar.progress = progressValue
                listener(progressValue)
            }
            override fun onStartTrackingTouch(p0: SeekBar?) {}
            override fun onStopTrackingTouch(p0: SeekBar?) {}
        })
        // invalidate the change of the Indicator
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
        if (indicatorPrefix.equals("")){
            when(IS_PREFIX_POSITION_START){
                true -> (indicator.findViewById<View>(R.id.progress_text) as TextView).text = "$indicatorPrefix $progress"
                false -> (indicator.findViewById<View>(R.id.progress_text) as TextView).text = "$progress $indicatorPrefix"
            }
        }else{
            when(IS_PREFIX_POSITION_START){
                true -> (indicator.findViewById<View>(R.id.progress_text) as TextView).text = "$indicatorPrefix $progress"
                false -> (indicator.findViewById<View>(R.id.progress_text) as TextView).text = "$progress $indicatorPrefix"
            }
        }
        indicator.measure(MeasureSpec.UNSPECIFIED, MeasureSpec.UNSPECIFIED)
        val bitmap = Bitmap.createBitmap(indicator.measuredWidth, indicator.measuredHeight, Bitmap.Config.ARGB_8888)
        val canvas = Canvas(bitmap)
        indicator.layout(0, 0, indicator.measuredWidth, indicator.measuredHeight)
        indicator.draw(canvas)
        return BitmapDrawable(resources, bitmap)
    }


}

