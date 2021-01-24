package ru.dellin.common.plugins

import android.content.Context
import androidx.annotation.ColorRes
import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import androidx.fragment.app.Fragment
import ru.dellin.common.extension.core.findColor
import ru.dellin.common.extension.core.findDrawable
import ru.dellin.core.android.view.delegate.UIDelegatePlugin

val UIDelegatePlugin<Fragment>.context: Context?
  get() = target?.activity

fun UIDelegatePlugin<Fragment>.getString(@StringRes res: Int) = context?.getString(res)
fun UIDelegatePlugin<Fragment>.getDrawable(@DrawableRes res: Int) = context?.findDrawable(res)
fun UIDelegatePlugin<Fragment>.getColor(@ColorRes res: Int) = context?.findColor(res)
