package com.example.shoppinglist.presentation

import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.graphics.Rect
import android.util.AttributeSet
import android.util.Log
import android.view.View

class CustomView(context: Context, attrs: AttributeSet?, defStyleAttr: Int, defStyleRes: Int) :
    View(context, attrs, defStyleAttr, defStyleRes) {
    private val paint: Paint = Paint()
    private val rect: Rect = Rect()
    constructor(context: Context) : this(context, null, 0, 0)

    constructor(context: Context, attrs: AttributeSet?) : this(context, attrs, 0, 0)
    constructor(context: Context, attrs: AttributeSet?, defStyleAttr: Int) : this(
        context,
        attrs,
        defStyleAttr,
        0
    )

    init {
        log("init")
    }

    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)

        log("onDraw")
        paint.apply {
            style = Paint.Style.FILL // стиль Заливка
            color = Color.WHITE // закрашиваем холст белым цветом
        }
        canvas?.drawPaint(paint)

        // Солнце
        paint.apply {
            isAntiAlias = true
            color = Color.YELLOW
        }
        canvas?.drawCircle(width - 30F, 30F, 25F, paint)

        // Лужайка
        paint.color = Color.GREEN
        canvas?.drawRect(0F, height - 30F, width.toFloat(), height.toFloat(), paint)

        // Текст над лужайкой
        paint.apply {
            color = Color.BLUE
            style = Paint.Style.FILL
            isAntiAlias = true
            textSize = 32F
        }
        canvas?.drawText("Лужайка только для котов", 30F, height - 32F, paint)

        // Лучик солнца
        val x = width - 170F
        val y = 190F

        paint.apply {
            color = Color.GRAY
            style = Paint.Style.FILL
            textSize = 27F
        }

        val beam = "Лучик солнца!"

        canvas?.save()
        canvas?.rotate(-45F, x + rect.exactCenterX(), y + rect.exactCenterY())
        canvas?.drawText(beam, x, y, paint)

        canvas?.restore()
    }

    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec)
    }

    override fun onLayout(changed: Boolean, left: Int, top: Int, right: Int, bottom: Int) {
        super.onLayout(changed, left, top, right, bottom)
    }

    companion object{
        fun log(text: String){
            Log.d("CustomView",text)
        }
    }
}