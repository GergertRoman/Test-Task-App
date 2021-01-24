package ru.dellin.core.api.system

interface DeviceManager {
  fun getUniqueDeviceId(): String
  fun clearUniqueDeviceId()
  fun clearAndGetUniqueDeviceId(): String
}
