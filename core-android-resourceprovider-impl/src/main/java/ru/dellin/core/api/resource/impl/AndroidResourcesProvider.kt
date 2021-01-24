package ru.dellin.core.api.resource.impl

import android.content.Context
import android.content.res.ColorStateList
import androidx.appcompat.content.res.AppCompatResources
import androidx.core.content.ContextCompat
import ru.dellin.common.extension.core.getDimensionPixelSize
import ru.dellin.common.extension.core.getInteger
import ru.dellin.common.extension.core.getQuantityString
import ru.dellin.common.extension.core.getStringArray
import ru.dellin.common.extension.paint.drawable.getDrawableRes
import ru.dellin.common.utils.DEFAULT_INT
import ru.dellin.core.api.resource.ResourcesProvider

class AndroidResourcesProvider(private val context: Context) :
  ResourcesProvider {
  override fun getString(resId: Int) = context.getString(resId)
  override fun getString(resId: Int, vararg formatArgs: Any) = context.getString(resId, *formatArgs)
  override fun getStringArray(resId: Int): Array<String> = context.getStringArray(resId)
  override fun getDrawableRes(fileName: String) = context.getDrawableRes(fileName) ?: DEFAULT_INT
  override fun getDimensionPixelSize(id: Int) = context.getDimensionPixelSize(id)
  override fun getInteger(res: Int) = context.getInteger(res)
  override fun getColor(colorRes: Int) = ContextCompat.getColor(context, colorRes)

  override fun getQuantityString(
    pluralId: Int,
    quantity: Int,
    vararg formatArgs: Any
  ) = context.getQuantityString(pluralId, quantity, *formatArgs)

  override fun getColorStateList(
    id: Int
  ): ColorStateList = AppCompatResources.getColorStateList(context, id)
}
