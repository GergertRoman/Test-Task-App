package ru.dellin.common.extension.primitive

import android.content.res.TypedArray
import android.graphics.Color
import ru.dellin.common.utils.DEFAULT_INT
import java.util.Locale

fun Int.isNotOutToBounds(index: Number, start: Int = 0) = index in start until this
fun Double.round(decimals: Int = 2): Double = roundAsStr(decimals).toDouble()
fun Double.roundAsStr(decimals: Int = 2): String = "%.${decimals}f".format(Locale.US, this)
fun TypedArray.getDimenPxProperty(resId: Int) = getDimensionPixelSize(resId, DEFAULT_INT).toFloat()
fun TypedArray.getColorProperty(resId: Int) = getColor(resId, Color.WHITE)
