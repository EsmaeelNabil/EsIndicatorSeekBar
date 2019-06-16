package com.esmaeel.esviewsfactory

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
class EsSeekbar : LinearLayout {

    private var _exampleString: String? = null // TODO: use a default from R.string...
    private var _exampleColor: Int = Color.RED // TODO: use a default from R.color...
    private var _exampleDimension: Float = 0f // TODO: use a default from R.dimen...

    private var textPaint: TextPaint? = null
    private var textWidth: Float = 0f
    private var textHeight: Float = 0f

    /**
     * The text to draw
     */


    /**
     * The font color
     */
    var exampleColor: Int
        get() = _exampleColor
        set(value) {
            _exampleColor = value
        }

    /**
     * In the example view, this dimension is the font size.
     */

    var exampleDimension: Float
        get() = _exampleDimension
        set(value) {
            _exampleDimension = value
        }

    /**
     * In the example view, this drawable is drawn above the text.
     */
    var exampleDrawable: Drawable? = null

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


    fun doTheMagicIn(context: Context , indicatorLayout : Int = R.layout.indicator ){
        indicatorView = LayoutInflater.from(context).inflate(indicatorLayout, null, false)
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
        seekBarIndicator.setProgress(50)
        distanceSeekBar.setProgress(seekBarIndicator.progress)
        // TODO: consider storing these as member variables to reduce
        // allocations per draw cycle.
        val paddingLeft = paddingLeft
        val paddingTop = paddingTop
        val paddingRight = paddingRight
        val paddingBottom = paddingBottom

        val contentWidth = width - paddingLeft - paddingRight
        val contentHeight = height - paddingTop - paddingBottom



        // Draw the example drawable on top of the text.
        exampleDrawable?.let {
            it.setBounds(
                paddingLeft, paddingTop,
                paddingLeft + contentWidth, paddingTop + contentHeight
            )
            it.draw(canvas)
        }

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
        (indicator.findViewById<View>(R.id.progress_text) as TextView).text = "$progress KM"

        indicator.measure(View.MeasureSpec.UNSPECIFIED, View.MeasureSpec.UNSPECIFIED)
        val bitmap = Bitmap.createBitmap(indicator.measuredWidth, indicator.measuredHeight, Bitmap.Config.ARGB_8888)
        val canvas = Canvas(bitmap)
        indicator.layout(0, 0, indicator.measuredWidth, indicator.measuredHeight)
        indicator.draw(canvas)

        return BitmapDrawable(resources, bitmap)
    }

}

