package com.esmaeel.esviewsfactory

import android.content.Context
import android.graphics.Bitmap
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.drawable.BitmapDrawable
import android.graphics.drawable.Drawable
import android.text.TextPaint
import android.util.AttributeSet
import android.view.LayoutInflater
import android.view.View
import android.widget.LinearLayout
import android.widget.SeekBar
import android.widget.TextView
import com.jaygoo.widget.OnRangeChangedListener
import com.jaygoo.widget.RangeSeekBar
import kotlinx.android.synthetic.main.es_rangebar_layout.view.*
import kotlinx.android.synthetic.main.es_seekbar_layout.view.*

/**
 * TODO: document your custom view class.
 */
class EsRangebar : LinearLayout {

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
        // Load attributes
        LayoutInflater.from(context).inflate(R.layout.es_rangebar_layout, this)
    }


    fun doTheMagicIn(context: Context , indicatorLayout : Int = R.layout.indicator ){
        LayoutInflater.from(context).inflate(R.layout.es_rangebar_layout, this)
        indicatorView = LayoutInflater.from(context).inflate(indicatorLayout, null, false)
        startBar.setOnSeekBarChangeListener(object : SeekBar.OnSeekBarChangeListener {
            override fun onProgressChanged(seekBar: SeekBar, i: Int, b: Boolean) {
                startRange = i
                seekBar.thumb = getThumb(i, indicatorView!!)
                if (startRange > 0 && startRange <= endBar.max) {
                    rangeBar.setValue(startRange.toFloat(), endRange.toFloat())
                }

            }

            override fun onStartTrackingTouch(seekBar: SeekBar) {

            }

            override fun onStopTrackingTouch(seekBar: SeekBar) {

            }
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
                startBar.progress = leftValue.toInt()
                endBar.progress = rightValue.toInt()
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
        // to invalidate the change of the thumb after the first initialization
//        startBar.progress = startBar.progress + 1
//        endBar.progress = endBar.progress + 1
    }



    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)
//        val padding = indicatorView!!.width / 2
//        startBar.max = 1000
//        endBar.max = 1000
//        rangeBar.setRange(0f,1000f)
//        startBar.setPadding(padding / 2, 0, padding, 0)
//        endBar.setPadding(padding / 2, 0, padding, 0)
//        rangeBar.setPadding(padding / 2, 0, padding, 0)
    }

    override fun onWindowFocusChanged(hasWindowFocus: Boolean) {
        super.onWindowFocusChanged(hasWindowFocus)
        val padding = indicatorView!!.width / 2
        startBar.max = 1000
        endBar.max = 1000
        rangeBar.setRange(0f,1000f)
        startBar.setPadding(padding / 2, 0, padding, 0)
        endBar.setPadding(padding / 2, 0, padding, 0)
        rangeBar.setPadding(padding , 0, padding, 0)

    }

    fun getThumb(progress: Int, indicator: View): Drawable {
        (indicator.findViewById<View>(R.id.progress_text) as TextView).text = "$$progress"

        indicator.measure(View.MeasureSpec.UNSPECIFIED, View.MeasureSpec.UNSPECIFIED)
        val bitmap = Bitmap.createBitmap(indicator.measuredWidth, indicator.measuredHeight, Bitmap.Config.ARGB_8888)
        val canvas = Canvas(bitmap)
        indicator.layout(0, 0, indicator.measuredWidth, indicator.measuredHeight)
        indicator.draw(canvas)

        return BitmapDrawable(resources, bitmap)
    }

}

