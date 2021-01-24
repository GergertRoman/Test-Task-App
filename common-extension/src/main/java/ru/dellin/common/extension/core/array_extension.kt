package ru.dellin.common.extension.core

import ru.dellin.common.extension.primitive.isNotOutToBounds

fun <T> Array<T>.isNotOutToBounds(index: Int) = size.isNotOutToBounds(index)
fun CharArray.isNotOutToBounds(index: Int) = size.isNotOutToBounds(index)
