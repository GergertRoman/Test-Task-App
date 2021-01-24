package ru.dellin.core.api.location

interface LocationProvider {
  fun getLastKnownLocation(okFun: (latLng: LatLng) -> Unit)
}
