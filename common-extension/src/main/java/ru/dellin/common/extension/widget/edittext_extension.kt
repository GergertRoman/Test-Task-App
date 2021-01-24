package ru.dellin.common.extension.widget

import android.view.inputmethod.EditorInfo
import android.widget.EditText
import android.widget.TextView.OnEditorActionListener

fun EditText.setOnNextListener(callback: () -> Unit = {}) {
  setOnEditorActionListener(
    OnEditorActionListener { v, actionId, event ->
      if (actionId == EditorInfo.IME_ACTION_NEXT) {
        callback()
        return@OnEditorActionListener true
      }
      false
    }
  )
}
