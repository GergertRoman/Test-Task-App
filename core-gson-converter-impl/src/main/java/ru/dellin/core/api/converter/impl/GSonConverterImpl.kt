package ru.dellin.core.api.converter.impl

import com.google.gson.Gson
import ru.dellin.core.api.converter.Converter
import java.lang.reflect.Type

class GSonConverterImpl(val gson: Gson) : Converter {

  override fun to(any: Any?): String = gson.toJson(any)
  override fun <T> from(json: String?, type: Type?): T = gson.fromJson(json, type)
}
