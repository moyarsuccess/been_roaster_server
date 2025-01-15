package dev.moyar.beenroaster.dev.moyar.beenroaster.device_controller

import com.google.gson.annotations.SerializedName

enum class DeviceCommand {
    @SerializedName("start_roaster")
    START_ROASTER,

    @SerializedName("end_roaster")
    END_ROASTING,

    @SerializedName("color_change")
    COLOR_CHANGE,

    @SerializedName("first_crack_started")
    FIRST_CRACK_STARTED,

    @SerializedName("first_crack_ended")
    FIRST_CRACK_ENDED,

    @SerializedName("second_crack_started")
    SECOND_CRACK_STARTED,

    @SerializedName("second_crack_ended")
    SECOND_CRACK_ENDED,

    @SerializedName("get_temperature")
    GET_TEMPERATURE,
}