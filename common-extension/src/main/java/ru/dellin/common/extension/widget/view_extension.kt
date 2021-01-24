package ru.dellin.common.extension.widget

import android.animation.Animator
import android.animation.AnimatorListenerAdapter
import android.animation.AnimatorSet
import android.animation.ObjectAnimator
import android.app.Activity
import android.text.Editable
import android.text.TextWatcher
import android.view.KeyEvent
import android.view.View
import android.view.ViewGroup
import android.view.animation.AnimationUtils
import android.view.animation.DecelerateInterpolator
import android.view.inputmethod.InputMethodManager
import android.widget.TextView
import androidx.annotation.AnimRes
import androidx.annotation.ColorRes
import androidx.annotation.DimenRes
import androidx.annotation.FontRes
import androidx.annotation.StringRes
import androidx.core.content.ContextCompat
import androidx.core.content.res.ResourcesCompat
import ru.dellin.common.extension.core.hideKeyboard
import ru.dellin.common.utils.DEFAULT_INT

private const val PROPERTY_ALPHA = "alpha"

fun View.hideKeyboard() {
  context?.hideKeyboard(this)
}

fun View.showKeyboard() {
  try {
    if (windowToken != null) {
      requestFocus()
      val imm = context.getSystemService(Activity.INPUT_METHOD_SERVICE) as InputMethodManager
      imm.toggleSoftInput(InputMethodManager.HIDE_IMPLICIT_ONLY, 0)
    }
  } catch (t: Throwable) {
    t.printStackTrace()
  }
}

fun View?.setMargin(
  start: Int = marginStart,
  top: Int = topMargin,
  end: Int = marginEnd,
  bottom: Int = bottomMargin
) {
  reapplyLayoutParams {
    with(it as? ViewGroup.MarginLayoutParams) {
      this?.marginStart = start
      this?.topMargin = top
      this?.marginEnd = end
      this?.bottomMargin = bottom
    }
  }
}

val View?.marginStart: Int
  get() = (this?.layoutParams as? ViewGroup.MarginLayoutParams)?.marginStart ?: DEFAULT_INT

val View?.topMargin: Int
  get() = (this?.layoutParams as? ViewGroup.MarginLayoutParams)?.topMargin ?: DEFAULT_INT

val View?.marginEnd: Int
  get() = (this?.layoutParams as? ViewGroup.MarginLayoutParams)?.marginEnd ?: DEFAULT_INT

val View?.bottomMargin: Int
  get() = (this?.layoutParams as? ViewGroup.MarginLayoutParams)?.bottomMargin ?: DEFAULT_INT

fun View?.reapplyLayoutParams(layoutParamsFun: (ViewGroup.LayoutParams) -> Unit) {
  this?.layoutParams
    ?.also { layoutParamsFun.invoke(it) }

  this?.requestLayout()
}

fun View.findViewTraversal(predicate: (View) -> Boolean): View? {
  if (predicate(this)) {
    return this
  }

  var result: View? = null
  if (this is ViewGroup) {
    traversalView {
      if (predicate(it)) {
        result = it
      }
    }
  }

  return result
}

fun ViewGroup.traversalView(action: (View) -> Unit) {
  for (i in 0 until childCount) {
    val child = getChildAt(i)
    action.invoke(child)

    if (child is ViewGroup) {
      child.traversalView(action)
    }
  }
}

fun TextView.addTextWatcher(
  beforeTextChangedFun: ((text: CharSequence?, start: Int, count: Int, after: Int) -> Unit)? = null,
  onTextChangedFun: ((text: CharSequence?, start: Int, before: Int, count: Int) -> Unit)? = null,
  afterTextChangedFun: ((text: Editable?) -> Unit)? = null
) = object : TextWatcher {
  override fun afterTextChanged(s: Editable?) {
    afterTextChangedFun?.invoke(s)
  }

  override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
    beforeTextChangedFun?.invoke(s, start, count, after)
  }

  override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
    onTextChangedFun?.invoke(s, start, before, count)
  }
}.apply { addTextChangedListener(this) }

fun View.focusChangeListener(focusFun: (Boolean) -> Unit) {
  setOnFocusChangeListener { _, hasFocus -> focusFun.invoke(hasFocus) }
}

fun View.enterKeyListener(keyFun: () -> Unit) {
  onKeyListener(KeyEvent.ACTION_DOWN, KeyEvent.KEYCODE_ENTER, keyFun)
}

fun View.nextKeyListener(keyFun: () -> Unit) {
  if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.M) {
    onKeyListener(KeyEvent.ACTION_DOWN, KeyEvent.KEYCODE_NAVIGATE_NEXT, keyFun)
  }
}

fun View.onKeyListener(keyEvent: Int, keyCode: Int, keyFun: () -> Unit) {
  setOnKeyListener { _, code, event ->
    if (keyEvent == event.action && code == keyCode) {
      keyFun.invoke()
      true
    } else {
      false
    }
  }
}

fun View.fadeOutIn(middleFun: () -> Unit) {
  val fadeOut = ObjectAnimator.ofFloat(this, PROPERTY_ALPHA, 1f, .0f)
  fadeOut.duration = 100L
  fadeOut.addListener(
    object : AnimatorListenerAdapter() {
      override fun onAnimationEnd(animation: Animator) {
        super.onAnimationEnd(animation)
        middleFun.invoke()
      }
    }
  )

  val fadeIn = ObjectAnimator.ofFloat(this, PROPERTY_ALPHA, .0f, 1f)
  fadeIn.duration = 200L

  val mAnimationSet = AnimatorSet()
  mAnimationSet.play(fadeIn).after(fadeOut)
  mAnimationSet.start()
}

fun View.fadeInOut(showTimeMillis: Long = 1000): AnimatorSet {
  val fadeIn = ObjectAnimator.ofFloat(this, PROPERTY_ALPHA, .0f, 1f)
  fadeIn.duration = 200L

  val fadeOut = ObjectAnimator.ofFloat(this, PROPERTY_ALPHA, 1f, .0f)
  fadeOut.startDelay = showTimeMillis
  fadeOut.duration = 100L

  val mAnimationSet = AnimatorSet()
  mAnimationSet.play(fadeOut).after(fadeIn)
  mAnimationSet.start()
  return mAnimationSet
}

fun View.fadeIn(endFun: ((View) -> Unit)? = null) {
  val fadeIn = ObjectAnimator.ofFloat(this, PROPERTY_ALPHA, .0f, 1f)
  fadeIn.duration = 200L
  fadeIn.interpolator = DecelerateInterpolator()
  fadeIn.addListener(
    object : AnimatorListenerAdapter() {
      override fun onAnimationEnd(animation: Animator) {
        super.onAnimationEnd(animation)
        if (isAttachedToWindow) {
          endFun?.invoke(this@fadeIn)
        }
      }
    }
  )

  val mAnimationSet = AnimatorSet()
  mAnimationSet.play(fadeIn)
  mAnimationSet.start()
}

fun View.fadeOut(endFun: ((View) -> Unit)? = null) {
  val fadeOut = ObjectAnimator.ofFloat(this, PROPERTY_ALPHA, 1f, .0f)
  fadeOut.duration = 200L
  fadeOut.interpolator = DecelerateInterpolator()
  fadeOut.addListener(
    object : AnimatorListenerAdapter() {
      override fun onAnimationEnd(animation: Animator) {
        super.onAnimationEnd(animation)
        if (isAttachedToWindow) {
          endFun?.invoke(this@fadeOut)
        }
      }
    }
  )

  val mAnimationSet = AnimatorSet()
  mAnimationSet.play(fadeOut)
  mAnimationSet.start()
}

fun View.getDimensionPixelSize(@DimenRes res: Int) = context?.resources
  ?.getDimensionPixelSize(res) ?: DEFAULT_INT

fun View.getFont(@FontRes res: Int) = ResourcesCompat.getFont(context, res)
fun View.getColor(@ColorRes res: Int) = ContextCompat.getColor(context, res)
fun View.getString(@StringRes res: Int) = context.getString(res)
fun View.getColorStateList(@ColorRes res: Int) = ContextCompat.getColorStateList(context, res)

fun View.startAnimation(@AnimRes anim: Int) {
  val animation = AnimationUtils.loadAnimation(context, anim)
  startAnimation(animation)
}
