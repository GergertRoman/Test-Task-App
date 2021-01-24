package ru.dellin.common.view.base

import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Path
import android.graphics.RectF
import android.graphics.drawable.GradientDrawable
import android.util.AttributeSet
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.content.withStyledAttributes
import ru.dellin.common.extension.primitive.getColorProperty
import ru.dellin.common.extension.primitive.getDimenPxProperty
import ru.dellin.common.extension.widget.getDimensionPixelSize
import ru.dellin.tech.common.view.R

open class RoundedConstraintLayout @JvmOverloads constructor(
  context: Context,
  attrs: AttributeSet? = null,
  defStyleAttr: Int = 0
) : ConstraintLayout(context, attrs, defStyleAttr) {

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

  fun setCornerRadius(
    tlRadius: Float = 0f,
    trRadius: Float = 0f,
    brRadius: Float = 0f,
    blRadius: Float = 0f
  ) {
    corners = floatArrayOf(
      tlRadius, tlRadius, trRadius, trRadius,
      brRadius, brRadius, blRadius, blRadius
    )
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

  private fun initCustomProperties(attrs: AttributeSet?) {
    context.withStyledAttributes(attrs, R.styleable.RoundedConstraintLayout) {
      val commonRadius = getDimenPxProperty(R.styleable.RoundedConstraintLayout_rcl_cornerRadius)
      val tlRadius = getDimenPxProperty(R.styleable.RoundedConstraintLayout_rcl_topLeftRadius)
      val trRadius = getDimenPxProperty(R.styleable.RoundedConstraintLayout_rcl_topRightRadius)
      val blRadius = getDimenPxProperty(R.styleable.RoundedConstraintLayout_rcl_bottomLeftRadius)
      val brRadius = getDimenPxProperty(R.styleable.RoundedConstraintLayout_rcl_bottomRightRadius)

      corners = if (commonRadius != 0f) {
        FloatArray(8) { commonRadius }
      } else {
        floatArrayOf(
          tlRadius, tlRadius, trRadius, trRadius,
          brRadius, brRadius, blRadius, blRadius
        )
      }

      color = getColorProperty(R.styleable.RoundedConstraintLayout_rcl_backgroundColor)
    }
  }

  // endregion

}
