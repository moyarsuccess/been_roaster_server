package dev.moyar.beenroaster.dev.moyar.beenroaster.artisan

import com.google.gson.Gson
import dev.moyar.beenroaster.dev.moyar.beenroaster.temp.TempHelper
import org.eclipse.jetty.websocket.api.Session
import org.eclipse.jetty.websocket.api.annotations.OnWebSocketClose
import org.eclipse.jetty.websocket.api.annotations.OnWebSocketConnect
import org.eclipse.jetty.websocket.api.annotations.OnWebSocketMessage
import org.eclipse.jetty.websocket.api.annotations.WebSocket
import org.koin.java.KoinJavaComponent.inject
import java.util.concurrent.ConcurrentHashMap

@WebSocket
class ArtisanWebSocketHandler {

    private val tempHelper: TempHelper by inject(TempHelper::class.java)

    @OnWebSocketConnect
    fun onConnect(session: Session) {
        println("$TAG Connected: ${session.remoteAddress}")
        sessions[session.remoteAddress.address.hostAddress] = session
    }

    @OnWebSocketMessage
    fun onMessage(session: Session, message: String) {
        val artisanCommand = gson.fromJson(message, ArtisanCommand::class.java)
        artisanCommand.handle(session)
    }

    private fun ArtisanCommand.handle(session: Session) {
        if (command != "getData") return
        session.remote.sendString(
            gson.toJson(
                ArtisanResponse(
                    id = id,
                    data = ArtisanResponseData(tempHelper.beenTemp, tempHelper.envTemp)
                )
            )
        )
    }

    @OnWebSocketClose
    fun onClose(session: Session, statusCode: Int, reason: String?) {
        println("$TAG Disconnected: ${session.remoteAddress}, Reason: $reason")
        sessions.remove(session.remoteAddress.address.hostAddress)
    }

    companion object {

        private const val TAG = "ARTISAN CONTROLLER: "
        private val gson = Gson()
        private val sessions = ConcurrentHashMap<String, Session>()

        fun sendPush(obj: Any) {
            val value = gson.toJson(obj)
            println("$TAG $value")
            sessions.values.forEach { it.remote.sendString(value) }
        }
    }
}