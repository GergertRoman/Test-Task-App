package ru.dellin.common.extension.core.collection

import ru.dellin.common.extension.primitive.isNotOutToBounds

fun <T, K> List<T>.convert(predicate: (T) -> K): List<K> {
  val list = mutableListOf<K>()
  forEach { list.add(predicate.invoke(it)) }
  return list
}

fun <T> List<T>.isNotOutToBounds(index: Int) = size.isNotOutToBounds(index)
