package dev.moyar.beenroaster.dev.moyar.beenroaster.roaster

import com.google.gson.Gson
import dev.moyar.beenroaster.dev.moyar.beenroaster.artisan.*
import dev.moyar.beenroaster.dev.moyar.beenroaster.device_controller.DeviceCommand
import dev.moyar.beenroaster.dev.moyar.beenroaster.device_controller.DeviceControllerCommand
import dev.moyar.beenroaster.dev.moyar.beenroaster.device_controller.DeviceControllerResponse
import dev.moyar.beenroaster.dev.moyar.beenroaster.temp.TempHelper
import org.eclipse.jetty.websocket.api.Session
import java.util.concurrent.atomic.AtomicBoolean

class RoasterManager(
    private val tempHelper: TempHelper,
    private val gson: Gson,
) {

    private var artisanInstance: Session? = null
    private val isConnected = AtomicBoolean(false)

    fun onArtisanConnected(session: Session) {
        this.artisanInstance = session
        isConnected.set(true)
    }

    fun onControllerNewMessageReceived(session: Session, message: String) {
        val deviceControllerCommand = gson.fromJson(message, DeviceControllerCommand::class.java)
        deviceControllerCommand.handle(session)
    }

    fun onNewArtisanMessageReceived(message: String) {
        val artisanCommand = gson.fromJson(message, ArtisanCommand::class.java)
        when (artisanCommand.command) {
            "getData" -> handleGetDataArtisanCommand(message)
            else -> {
                // Do nothing
            }
        }
    }

    fun onArtisanDisconnected() {
        isConnected.set(false)
    }

    private fun DeviceControllerCommand.handle(session: Session) {
        when (command) {
            DeviceCommand.START_ROASTER -> {
                sendMessageToArtisan(ArtisanPushMessage.startRoasting)
                session.send(DeviceControllerResponse.RoasterStarted)
            }

            DeviceCommand.END_ROASTING -> {
                sendMessageToArtisan(ArtisanPushMessage.endRoasting)
                session.send(DeviceControllerResponse.RoasterEnded)
            }

            DeviceCommand.COLOR_CHANGE -> {
                sendMessageToArtisan(ArtisanPushEvent.colorChange)
                session.send(DeviceControllerResponse.ColorChangeSent)
            }

            DeviceCommand.FIRST_CRACK_STARTED -> {
                sendMessageToArtisan(ArtisanPushEvent.firstCrackStarted)
                session.send(DeviceControllerResponse.FirstCrackStarted)
            }

            DeviceCommand.FIRST_CRACK_ENDED -> {
                sendMessageToArtisan(ArtisanPushEvent.firstCrackEnded)
                session.send(DeviceControllerResponse.FirstCrackEnded)
            }

            DeviceCommand.SECOND_CRACK_STARTED -> {
                sendMessageToArtisan(ArtisanPushEvent.secondCrackStarted)
                session.send(DeviceControllerResponse.SecondCrackStarted)
            }

            DeviceCommand.SECOND_CRACK_ENDED -> {
                sendMessageToArtisan(ArtisanPushEvent.secondCrackEnded)
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

    private fun handleGetDataArtisanCommand(message: String) {
        val artisanCommand = gson.fromJson(message, ArtisanCommand::class.java)
        val beenTemp = tempHelper.beenTemp
        val envTemp = tempHelper.envTemp
        artisanInstance?.remote?.sendString(
            gson.toJson(
                ArtisanResponse(
                    id = artisanCommand.id,
                    data = ArtisanResponseData(beenTemp, envTemp)
                )
            )
        )
    }

    private fun sendMessageToArtisan(obj: Any) {
        if (!isConnected.get()) return
        artisanInstance?.remote?.sendString(gson.toJson(obj))
    }
}