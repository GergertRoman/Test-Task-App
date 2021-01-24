package ru.dellin.common.network

import retrofit2.Converter
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import kotlin.reflect.KClass

class RetrofitWrapper(
  baseUrl: String,
  okHttpWrapper: OkHttpWrapper,
  converter: Converter.Factory
) {
  private val retrofit: Retrofit = Retrofit.Builder()
    .client(okHttpWrapper.httpClient)
    .baseUrl(baseUrl)
    .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
    .addConverterFactory(converter)
    .build()

  fun <T : Any> create(service: KClass<T>): T = retrofit.create(service.java)
}
