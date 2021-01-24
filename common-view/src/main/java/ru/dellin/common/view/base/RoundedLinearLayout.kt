package ru.dellin.common.view.base

import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Path
import android.graphics.RectF
import android.graphics.drawable.GradientDrawable
import android.util.AttributeSet
import android.widget.LinearLayout
import androidx.core.content.withStyledAttributes
import ru.dellin.common.extension.primitive.getColorProperty
import ru.dellin.common.extension.primitive.getDimenPxProperty
import ru.dellin.common.extension.widget.getDimensionPixelSize
import ru.dellin.tech.common.view.R

open class RoundedLinearLayout @JvmOverloads constructor(
  context: Context,
  attrs: AttributeSet,
  defStyleAttr: Int = 0
) : LinearLayout(context, attrs, defStyleAttr) {

  private var path = Path()
  private lateinit var corners: FloatArray
  private var color = Color.WHITE

  init {
    initCustomProperties(attrs)

    prepareForElevation()

    setWillNotDraw(false)
  }

  override fun onSizeChanged(w: Int, h: Int, oldw: Int, oldh: Int) {
    super.onSizeChanged(w, h, oldw, oldh)
    val rect = RectF(0f, 0f, width.toFloat(), height.toFloat())
    path.addRoundRect(rect, corners, Path.Direction.CW)
    path.close()
  }

  override fun draw(canvas: Canvas) {
    canvas.save()
    canvas.clipPath(path)
    super.draw(canvas)
    canvas.restore()
  }

  fun setCornerRadius(resId: Int) {
    setCornerRadius(getDimensionPixelSize(resId).toFloat())
  }

  fun setCornerRadius(radius: Float) {
    corners = FloatArray(8) { radius }
  }

  // region private

  private fun prepareForElevation() {
    if (background == null) {
      val shape = GradientDrawable()
      shape.cornerRadius = corners.maxOrNull() ?: 0f
      shape.setColor(color)
      background = shape
    }
  }

  private fun initCustomProperties(attrs: AttributeSet) {
    context.withStyledAttributes(attrs, R.styleable.RoundedLinearLayout) {
      val commonRadius = getDimenPxProperty(R.styleable.RoundedLinearLayout_rll_cornerRadius)
      val tlRadius = getDimenPxProperty(R.styleable.RoundedLinearLayout_rll_topLeftRadius)
      val trRadius = getDimenPxProperty(R.styleable.RoundedLinearLayout_rll_topRightRadius)
      val blRadius = getDimenPxProperty(R.styleable.RoundedLinearLayout_rll_bottomLeftRadius)
      val brRadius = getDimenPxProperty(R.styleable.RoundedLinearLayout_rll_bottomRightRadius)

      corners = if (commonRadius != 0f) {
        FloatArray(8) { commonRadius }
      } else {
        floatArrayOf(
          tlRadius, tlRadius, trRadius, trRadius,
          brRadius, brRadius, blRadius, blRadius
        )
      }

      color = getColorProperty(R.styleable.RoundedLinearLayout_rll_backgroundColor)
    }
  }

  // endregion

}
