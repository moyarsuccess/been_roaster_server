package dev.moyar.beenroaster.dev.moyar.beenroaster.device_controller

import com.google.gson.Gson
import dev.moyar.beenroaster.dev.moyar.beenroaster.artisan.ArtisanPushEvent
import dev.moyar.beenroaster.dev.moyar.beenroaster.artisan.ArtisanPushMessage
import dev.moyar.beenroaster.dev.moyar.beenroaster.artisan.ArtisanWebSocketHandler
import org.eclipse.jetty.websocket.api.Session
import org.eclipse.jetty.websocket.api.annotations.OnWebSocketClose
import org.eclipse.jetty.websocket.api.annotations.OnWebSocketConnect
import org.eclipse.jetty.websocket.api.annotations.OnWebSocketMessage
import org.eclipse.jetty.websocket.api.annotations.WebSocket

@WebSocket
class DeviceControllerWebSocket {

    private val gson = Gson()

    @OnWebSocketConnect
    fun onConnect(session: Session) {
        println("$TAG Connected: ${session.remoteAddress}")
    }

    @OnWebSocketMessage
    fun onMessage(session: Session, message: String) {
        println("$TAG Message: $message")
        val deviceControllerCommand = gson.fromJson(message, DeviceControllerCommand::class.java)
        deviceControllerCommand.handle()
    }

    @OnWebSocketClose
    fun onClose(session: Session, statusCode: Int, reason: String?) {
        println("$TAG Disconnected: ${session.remoteAddress}, Reason: $reason")
    }

    private fun DeviceControllerCommand.handle() {
        when (command) {
            DeviceCommand.START_ROASTER -> ArtisanWebSocketHandler.sendPush(ArtisanPushMessage.startRoasting)
            DeviceCommand.END_ROASTING -> ArtisanWebSocketHandler.sendPush(ArtisanPushMessage.endRoasting)
            DeviceCommand.COLOR_CHANGE -> ArtisanWebSocketHandler.sendPush(ArtisanPushEvent.colorChange)
            DeviceCommand.FIRST_CRACK_STARTED -> ArtisanWebSocketHandler.sendPush(ArtisanPushEvent.firstCrackStarted)
            DeviceCommand.FIRST_CRACK_ENDED -> ArtisanWebSocketHandler.sendPush(ArtisanPushEvent.firstCrackEnded)
            DeviceCommand.SECOND_CRACK_STARTED -> ArtisanWebSocketHandler.sendPush(ArtisanPushEvent.secondCrackStarted)
            DeviceCommand.SECOND_CRACK_ENDED -> ArtisanWebSocketHandler.sendPush(ArtisanPushEvent.secondCrackEnded)
        }
    }

    companion object {
        private const val TAG = "DEVICE CONTROLLER: "
    }
}