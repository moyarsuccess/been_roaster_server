package dev.moyar.beenroaster.dev.moyar.beenroaster.artisan

import com.google.gson.Gson
import org.eclipse.jetty.websocket.api.Session
import org.eclipse.jetty.websocket.api.annotations.OnWebSocketClose
import org.eclipse.jetty.websocket.api.annotations.OnWebSocketConnect
import org.eclipse.jetty.websocket.api.annotations.OnWebSocketMessage
import org.eclipse.jetty.websocket.api.annotations.WebSocket
import java.util.concurrent.ConcurrentHashMap
import kotlin.random.Random

@WebSocket
class ArtisanWebSocketHandler {

    @OnWebSocketConnect
    fun onConnect(session: Session) {
        println("Connected: ${session.remoteAddress}")
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
                    data = ArtisanResponseData(fetchTemperature(), fetchTemperature())
                )
            )
        )
    }

    @OnWebSocketClose
    fun onClose(session: Session, statusCode: Int, reason: String?) {
        println("Disconnected: ${session.remoteAddress}, Reason: $reason")
        sessions.remove(session.remoteAddress.address.hostAddress)
    }

    private fun fetchTemperature(): Float {
        return Random.nextDouble(18.0, 48.0).toFloat()
    }

    companion object {

        private val gson = Gson()
        private val sessions = ConcurrentHashMap<String, Session>()

        fun sendPush(obj: Any) {
            sessions.values.forEach { it.remote.sendString(gson.toJson(obj)) }
        }
    }
}