package dev.moyar.beenroaster.dev.moyar.beenroaster.device_controller

import com.google.gson.Gson
import dev.moyar.beenroaster.dev.moyar.beenroaster.artisan.ArtisanPushMessage
import dev.moyar.beenroaster.dev.moyar.beenroaster.artisan.ArtisanWebSocketHandler
import org.eclipse.jetty.websocket.api.Session
import org.eclipse.jetty.websocket.api.annotations.OnWebSocketClose
import org.eclipse.jetty.websocket.api.annotations.OnWebSocketConnect
import org.eclipse.jetty.websocket.api.annotations.OnWebSocketMessage
import org.eclipse.jetty.websocket.api.annotations.WebSocket
import kotlin.random.Random

@WebSocket
class DeviceControllerWebSocket {

    private val gson = Gson()

    @OnWebSocketConnect
    fun onConnect(session: Session) {
        println("Connected: ${session.remoteAddress}")
    }

    @OnWebSocketMessage
    fun onMessage(session: Session, message: String) {
        val deviceControllerCommand = gson.fromJson(message, DeviceControllerCommand::class.java)
        deviceControllerCommand.handle()
    }

    private fun DeviceControllerCommand.handle() {
        when (command) {
            DeviceCommand.START_ROASTER -> ArtisanWebSocketHandler.sendPush(ArtisanPushMessage.startRoasting)
            DeviceCommand.END_ROASTING -> ArtisanWebSocketHandler.sendPush(ArtisanPushMessage.endRoasting)
        }
    }

    @OnWebSocketClose
    fun onClose(session: Session, statusCode: Int, reason: String?) {
        println("Disconnected: ${session.remoteAddress}, Reason: $reason")
    }

    private fun fetchTemperature(): Float {
        return Random.nextDouble(18.0, 48.0).toFloat()
    }
}