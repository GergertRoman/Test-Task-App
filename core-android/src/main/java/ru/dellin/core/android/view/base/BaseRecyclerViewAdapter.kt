package ru.dellin.core.android.view.base

import androidx.recyclerview.widget.RecyclerView

abstract class BaseRecyclerViewAdapter<V : RecyclerView.ViewHolder, D> : RecyclerView.Adapter<V>() {

  protected val items: MutableList<D> = mutableListOf()

  override fun getItemCount() = items.size

  fun update(items: List<D>) {
    this.items.clear()
    this.items.addAll(items)
    notifyDataSetChanged()
  }
}
