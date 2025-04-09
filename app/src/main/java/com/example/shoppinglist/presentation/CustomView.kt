package com.example.shoppinglist.presentation

import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.graphics.Rect
import android.os.Parcelable
import android.util.AttributeSet
import android.util.Log
import android.view.View
import kotlin.math.min

class CustomView @JvmOverloads constructor(context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0, defStyleRes: Int = 0) :
    View(context, attrs, defStyleAttr, defStyleRes) {
    private val paint: Paint = Paint()
    private val rect: Rect = Rect()
//    constructor(context: Context) : this(context, null, 0, 0)
//
//    constructor(context: Context, attrs: AttributeSet?) : this(context, attrs, 0, 0)
//    constructor(context: Context, attrs: AttributeSet?, defStyleAttr: Int) : this(
//        context,
//        attrs,
//        defStyleAttr,
//        0
//    )

    init {
        log("init")
    }

    override fun onAttachedToWindow() {
        super.onAttachedToWindow()
        log("onAttachedToWindow")
    }

    override fun onDetachedFromWindow() {
        super.onDetachedFromWindow()
        log("onDetachedFromWindow")
    }

    override fun onFinishInflate() {
        super.onFinishInflate()
        log("onFinishInflate")
    }

    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)

        log("onDraw")
        paint.apply {
            style = Paint.Style.FILL // стиль Заливка
            color = Color.WHITE // закрашиваем холст белым цветом
        }
        canvas.drawPaint(paint)

        // Солнце
        paint.apply {
            isAntiAlias = true
            color = Color.YELLOW
        }
        canvas.drawCircle(width - 30F, 30F, 25F, paint)

        // Лужайка
        paint.color = Color.GREEN
        canvas.drawRect(0F, height - 30F, width.toFloat(), height.toFloat(), paint)
        // Текст над лужайкой
        paint.apply {
            color = Color.BLUE
            style = Paint.Style.FILL
            isAntiAlias = true
            textSize = 32F
        }
        canvas.drawText("Лужайка только для котов", 30F, height - 32F, paint)

        // Лучик солнца
        val x = width - 170F
        val y = 190F

        paint.apply {
            color = Color.GRAY
            style = Paint.Style.FILL
            textSize = 27F
        }

        val beam = "Лучик солнца!"

        canvas.save()
        canvas.rotate(-45F, x + rect.exactCenterX(), y + rect.exactCenterY())
        canvas.drawText(beam, x, y, paint)

        canvas.restore()
    }



    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        log("___onMeasure widthMeasureSpec = ${MeasureSpec.toString(widthMeasureSpec)} " +
                "heightMeasureSpec = ${MeasureSpec.toString(heightMeasureSpec)}")
        val desiredWidth = 100 // Предполагаемая ширина View
        val desiredHeight = 100 // Предполагаемая высота View

        val widthMode = MeasureSpec.getMode(widthMeasureSpec)
        val widthSize = MeasureSpec.getSize(widthMeasureSpec)


        val heightMode = MeasureSpec.getMode(heightMeasureSpec)
        val heightSize = MeasureSpec.getSize(heightMeasureSpec)


        val width = when (widthMode) {
            MeasureSpec.EXACTLY -> widthSize // Задан конкретный размер для ширины
            MeasureSpec.AT_MOST -> min(desiredWidth, widthSize) // Размер не должен превышать заданный размер
            else -> desiredWidth // Задать предпочтительный размер, если точного или максимального размера не задано
        }

        val height = when (heightMode) {
            MeasureSpec.EXACTLY -> heightSize // Задан конкретный размер для высоты
            MeasureSpec.AT_MOST -> min(desiredHeight, heightSize) // Размер не должен превышать заданный размер
            else -> desiredHeight // Задать предпочтительный размер, если точного или максимального размера не задано
        }

        setMeasuredDimension(width, height) // Устанавливаем фактический размер View
       // super.onMeasure(widthMeasureSpec, heightMeasureSpec)
    }

    override fun onLayout(changed: Boolean, left: Int, top: Int, right: Int, bottom: Int) {
        log("onLayout changed = $changed left = $left top = $top right = $right bottom = $bottom")
        super.onLayout(changed, left, top, right, bottom)
    }

    override fun onSaveInstanceState(): Parcelable? {
        log("onSaveInstanceState")
        return super.onSaveInstanceState()
    }

    override fun onRestoreInstanceState(state: Parcelable?) {
        log("onRestoreInstanceState")
        super.onRestoreInstanceState(state)
    }

    override fun onSizeChanged(w: Int, h: Int, oldw: Int, oldh: Int) {
        log("onSizeChanged new : $w $h, old : $oldw , $oldh")
        super.onSizeChanged(w, h, oldw, oldh)
    }

    companion object{
        fun log(text: String){
            Log.d("CustomView",text)
        }
    }
}