package dev.moyar.beenroaster.dev.moyar.beenroaster.device_controller

import com.google.gson.annotations.SerializedName

enum class DeviceCommand {
    @SerializedName("start_roaster")
    START_ROASTER,
    @SerializedName("end_roaster")
    END_ROASTING,
}