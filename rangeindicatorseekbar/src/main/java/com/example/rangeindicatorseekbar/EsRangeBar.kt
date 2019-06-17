package com.example.rangeindicatorseekbar

import android.content.Context
import android.graphics.Bitmap
import android.graphics.Canvas
import android.graphics.drawable.BitmapDrawable
import android.graphics.drawable.Drawable
import android.util.AttributeSet
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.widget.LinearLayout
import android.widget.SeekBar
import android.widget.TextView
import com.jaygoo.widget.OnRangeChangedListener
import com.jaygoo.widget.RangeSeekBar
import kotlinx.android.synthetic.main.es_rangebar_layout.view.*
import kotlin.math.absoluteValue


class EsRangeBar : LinearLayout {

    private var indicatorPrefix : String? = ""


    private var IS_PREFIX_POSITION_START :Boolean = false


    companion object {
        const val WHITE :String = "WHITE"
        const val BLACK :String = "BLACK"
        const val CUSTOM_LAYOUT :String  ="CUSTOM_COLOR"
    }


    var startRange = 20
    var endRange = 60
    var leftSeekBar: com.jaygoo.widget.SeekBar? = null
    var rightSeekBar: com.jaygoo.widget.SeekBar? = null
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
        LayoutInflater.from(context).inflate(R.layout.es_rangebar_layout, this)
    }


    fun doTheMagicIn(context: Context
                     ,listener : (startRange :Int , endRange:Int) -> Unit
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

        LayoutInflater.from(context).inflate(R.layout.es_rangebar_layout, this)


        this.indicatorPrefix = indicatorPrefix
        this.IS_PREFIX_POSITION_START = prefixPositionStart

        startBar.setOnSeekBarChangeListener(object : SeekBar.OnSeekBarChangeListener {
            override fun onProgressChanged(seekBar: SeekBar, i: Int, b: Boolean) {
                startRange = i
                seekBar.thumb = getThumb(i, indicatorView!!)
                if (startRange > 0 && startRange <= endBar.max) {
                    rangeBar.setValue(startRange.toFloat(), endRange.toFloat())
                }

            }

            override fun onStartTrackingTouch(seekBar: SeekBar) {}
            override fun onStopTrackingTouch(seekBar: SeekBar) {}
        })
        endBar.setOnSeekBarChangeListener(object : SeekBar.OnSeekBarChangeListener {
            override fun onProgressChanged(seekBar: SeekBar, i: Int, b: Boolean) {
                endRange = i
                seekBar.thumb = getThumb(i, indicatorView!!)
                if (endRange > 0 && endRange <= endBar.max) {
                    rangeBar.setValue(startRange.toFloat(), endRange.toFloat())
                }
            }
            override fun onStartTrackingTouch(seekBar: SeekBar) {}
            override fun onStopTrackingTouch(seekBar: SeekBar) {}
        })
        rangeBar.setOnRangeChangedListener(object : OnRangeChangedListener {
            override fun onRangeChanged(view: RangeSeekBar, leftValue: Float, rightValue: Float, isFromUser: Boolean) {
                var left_Value = leftValue.toInt()
                var right_Value = rightValue.toInt()
                startBar.progress = left_Value
                endBar.progress = right_Value
                try {
                    listener(left_Value,right_Value)
                } catch (e: Exception) {
                    Log.e("EsListenerError",e.message.toString())
                }
            }
            override fun onStartTrackingTouch(view: RangeSeekBar, isLeft: Boolean) {}
            override fun onStopTrackingTouch(view: RangeSeekBar, isLeft: Boolean) {}
        })

        rangeBar.seekBarMode = RangeSeekBar.SEEKBAR_MODE_RANGE

        leftSeekBar = rangeBar.leftSeekBar
        rightSeekBar = rangeBar.rightSeekBar

        leftSeekBar!!.setIndicatorTextStringFormat("$ %s")
        leftSeekBar!!.setIndicatorTextDecimalFormat("0")
        rightSeekBar!!.setIndicatorTextStringFormat("$ %s")
        rightSeekBar!!.setIndicatorTextDecimalFormat("0")



        rangeBar.setRange(0f,1000f)
        rangeBar.setValue(200f,800f)
        rangeBar.postInvalidate()
    }

    override fun onWindowFocusChanged(hasWindowFocus: Boolean) {
        super.onWindowFocusChanged(hasWindowFocus)
        indicatorView.let {
            val padding = it!!.width / 2
            startBar.max = 1000
            endBar.max = 1000
            rangeBar.setRange(0f,1000f)
            startBar.setPadding(padding / 2, 0, padding, 0)
            endBar.setPadding(padding / 2, 0, padding, 0)
            rangeBar.setPadding(padding , 0, padding, 0)
        }
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


