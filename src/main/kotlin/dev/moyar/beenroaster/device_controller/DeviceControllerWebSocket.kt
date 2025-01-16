package dev.moyar.beenroaster.dev.moyar.beenroaster.device_controller

import com.google.gson.Gson
import dev.moyar.beenroaster.dev.moyar.beenroaster.artisan.ArtisanPushEvent
import dev.moyar.beenroaster.dev.moyar.beenroaster.artisan.ArtisanPushMessage
import dev.moyar.beenroaster.dev.moyar.beenroaster.artisan.ArtisanWebSocketHandler
import dev.moyar.beenroaster.dev.moyar.beenroaster.temp.TempHelper
import org.eclipse.jetty.websocket.api.Session
import org.eclipse.jetty.websocket.api.annotations.OnWebSocketClose
import org.eclipse.jetty.websocket.api.annotations.OnWebSocketConnect
import org.eclipse.jetty.websocket.api.annotations.OnWebSocketMessage
import org.eclipse.jetty.websocket.api.annotations.WebSocket
import org.koin.java.KoinJavaComponent.inject

@WebSocket
class DeviceControllerWebSocket {

    private val gson = Gson()
    private val tempHelper: TempHelper by inject(TempHelper::class.java)

    @OnWebSocketConnect
    fun onConnect(session: Session) {
        println("$TAG Connected: ${session.remoteAddress}")
    }

    @OnWebSocketMessage
    fun onMessage(session: Session, message: String) {
        println("$TAG Message: $message")
        val deviceControllerCommand = gson.fromJson(message, DeviceControllerCommand::class.java)
        deviceControllerCommand.handle(session)
    }

    @OnWebSocketClose
    fun onClose(session: Session, statusCode: Int, reason: String?) {
        println("$TAG Disconnected: ${session.remoteAddress}, Reason: $reason")
    }

    private fun DeviceControllerCommand.handle(session: Session) {
        when (command) {
            DeviceCommand.START_ROASTER -> {
                ArtisanWebSocketHandler.sendPush(ArtisanPushMessage.startRoasting)
                session.send(DeviceControllerResponse.RoasterStarted)
            }

            DeviceCommand.END_ROASTING -> {
                ArtisanWebSocketHandler.sendPush(ArtisanPushMessage.endRoasting)
                session.send(DeviceControllerResponse.RoasterEnded)
            }

            DeviceCommand.COLOR_CHANGE -> {
                ArtisanWebSocketHandler.sendPush(ArtisanPushEvent.colorChange)
                session.send(DeviceControllerResponse.ColorChangeSent)
            }

            DeviceCommand.FIRST_CRACK_STARTED -> {
                ArtisanWebSocketHandler.sendPush(ArtisanPushEvent.firstCrackStarted)
                session.send(DeviceControllerResponse.FirstCrackStarted)
            }

            DeviceCommand.FIRST_CRACK_ENDED -> {
                ArtisanWebSocketHandler.sendPush(ArtisanPushEvent.firstCrackEnded)
                session.send(DeviceControllerResponse.FirstCrackEnded)
            }

            DeviceCommand.SECOND_CRACK_STARTED -> {
                ArtisanWebSocketHandler.sendPush(ArtisanPushEvent.secondCrackStarted)
                session.send(DeviceControllerResponse.SecondCrackStarted)
            }

            DeviceCommand.SECOND_CRACK_ENDED -> {
                ArtisanWebSocketHandler.sendPush(ArtisanPushEvent.secondCrackEnded)
                session.send(DeviceControllerResponse.SecondCrackEnded)
            }

            DeviceCommand.GET_TEMPERATURE -> {
                session.send(
                    DeviceControllerResponse.GetTemperatureResponseReady(
                        tempHelper.envTemp.toString(),
                        tempHelper.beenTemp.toString(),
                    )
                )
            }
        }
    }

    private fun Session.send(response: DeviceControllerResponse) {
        remote.sendString(response.json)
        println("Sent to client: ${response.json}")
    }

    companion object {
        private const val TAG = "DEVICE CONTROLLER: "
    }
}