package ru.dellin.common.extension.core

import android.content.ClipData
import android.content.ClipboardManager
import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import android.os.Build
import android.util.TypedValue
import android.view.View
import android.view.inputmethod.InputMethodManager
import androidx.annotation.ColorRes
import androidx.annotation.DimenRes
import androidx.annotation.DrawableRes
import androidx.annotation.IntegerRes
import androidx.annotation.PluralsRes
import androidx.annotation.RequiresApi
import androidx.appcompat.content.res.AppCompatResources
import androidx.core.content.ContextCompat
import ru.dellin.common.utils.DEFAULT_BOOLEAN

val Context?.locale
  get() = this?.resources
    ?.configuration
    ?.let {
      if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
        it.locales[0]
      } else {
        it.locale
      }
    }

fun Context.hideKeyboard(view: View?) {
  try {
    if (view != null && view.windowToken != null) {
      val inputMethodService = getSystemService(Context.INPUT_METHOD_SERVICE)
      val inputMethod = inputMethodService as? InputMethodManager
      inputMethod?.hideSoftInputFromWindow(view.windowToken, 0)
      view.clearFocus()
    }
  } catch (t: Throwable) {
    t.printStackTrace()
  }
}

fun Context?.findDrawable(
  @DrawableRes res: Int
) = this?.let { AppCompatResources.getDrawable(it, res) }

fun Context?.findColor(@ColorRes res: Int) = this?.let { ContextCompat.getColor(it, res) }

// region Resources

fun Context.getStringArray(resId: Int): Array<String> = resources.getStringArray(resId)

fun Context.getQuantityString(
  @PluralsRes pluralId: Int,
  quantity: Int,
  vararg formatArgs: Any
) = resources.getQuantityString(pluralId, quantity, *formatArgs)

fun Context.getDimensionPixelSize(@DimenRes resId: Int) = resources.getDimensionPixelSize(resId)
fun Context.getInteger(@IntegerRes resId: Int) = resources.getInteger(resId)

@RequiresApi(Build.VERSION_CODES.M)
fun Context.isConnected(): Boolean {
  val connectivityManager = this.getSystemService(
    Context.CONNECTIVITY_SERVICE
  ) as ConnectivityManager?
  val capability = connectivityManager?.getNetworkCapabilities(connectivityManager.activeNetwork)
  return capability?.hasCapability(NetworkCapabilities.NET_CAPABILITY_INTERNET) ?: DEFAULT_BOOLEAN
}

fun Context.copyToClipboard(text: String) {
  val manager = getSystemService(Context.CLIPBOARD_SERVICE) as ClipboardManager
  val myClip = ClipData.newPlainText(text, text)
  manager.setPrimaryClip(myClip)
}

fun Context.getPxByDp(dp: Float): Int {
  val metrics = resources?.displayMetrics
  return TypedValue.applyDimension(
    TypedValue.COMPLEX_UNIT_DIP,
    dp,
    metrics
  ).toInt()
}

// endregion
