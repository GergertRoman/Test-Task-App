package ru.dellin.core.api.system.impl

import ru.dellin.common.utils.DEFAULT_STRING
import ru.dellin.core.api.cache.PreferenceManager
import ru.dellin.core.api.system.DeviceManager
import java.util.UUID

private const val UNIQUE_DEVICE_ID_KEY = "unique_device_id_key"

class AndroidDeviceManager(
  private val preferenceManager: PreferenceManager,
  private val uniqueDeviceIdKey: String = UNIQUE_DEVICE_ID_KEY
) : DeviceManager {
  private var deviceId = DEFAULT_STRING

  override fun getUniqueDeviceId(): String {
    if (deviceId.isEmpty()) {
      var uuid = preferenceManager.getString(uniqueDeviceIdKey, DEFAULT_STRING)
      if (uuid.isEmpty()) {
        uuid = UUID.randomUUID()
          .toString()

        preferenceManager.putString(uniqueDeviceIdKey, uuid)
      }

      deviceId = uuid
    }

    return deviceId
  }

  override fun clearUniqueDeviceId() {
    preferenceManager.remove(uniqueDeviceIdKey)
    deviceId = DEFAULT_STRING
  }

  override fun clearAndGetUniqueDeviceId() = let {
    clearUniqueDeviceId()
    getUniqueDeviceId()
  }
}
