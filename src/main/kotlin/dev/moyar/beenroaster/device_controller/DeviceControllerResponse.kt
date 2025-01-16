package dev.moyar.beenroaster.dev.moyar.beenroaster.device_controller

import com.google.gson.Gson
import org.json.JSONObject

sealed class DeviceControllerResponse {

    abstract val name: String
    abstract val json: String

    data object RoasterStarted : DeviceControllerResponse() {
        override val name = "roaster_started"
        override val json: String
            get() = JSONObject().apply {
                put("name", name)
            }.toString()
    }

    data object RoasterEnded : DeviceControllerResponse() {
        override val name = "roaster_ended"
        override val json: String
            get() = JSONObject().apply {
                put("name", name)
            }.toString()
    }

    data object FirstCrackStarted : DeviceControllerResponse() {
        override val name = "first_crack_started"
        override val json: String
            get() = JSONObject().apply {
                put("name", name)
            }.toString()
    }

    data object FirstCrackEnded : DeviceControllerResponse() {
        override val name = "first_crack_ended"
        override val json: String
            get() = JSONObject().apply {
                put("name", name)
            }.toString()
    }

    data object SecondCrackStarted : DeviceControllerResponse() {
        override val name = "second_crack_started"
        override val json: String
            get() = JSONObject().apply {
                put("name", name)
            }.toString()
    }

    data object SecondCrackEnded : DeviceControllerResponse() {
        override val name = "second_crack_ended"
        override val json: String
            get() = JSONObject().apply {
                put("name", name)
            }.toString()
    }

    data object ColorChangeSent : DeviceControllerResponse() {
        override val name = "color_change_sent"
        override val json: String
            get() = JSONObject().apply {
                put("name", name)
            }.toString()
    }

    data class GetTemperatureResponseReady(
        val envTemp: String,
        val beenTemp: String,
    ) : DeviceControllerResponse() {
        override val name = "get_temperature"
        override val json: String
            get() = gson.toJson(this)
    }

    companion object {
        private val gson = Gson()
    }
}