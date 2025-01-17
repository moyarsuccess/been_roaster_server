package dev.moyar.beenroaster.dev.moyar.beenroaster.artisan

import dev.moyar.beenroaster.dev.moyar.beenroaster.roaster.RoasterManager
import org.eclipse.jetty.websocket.api.Session
import org.eclipse.jetty.websocket.api.annotations.OnWebSocketClose
import org.eclipse.jetty.websocket.api.annotations.OnWebSocketConnect
import org.eclipse.jetty.websocket.api.annotations.OnWebSocketMessage
import org.eclipse.jetty.websocket.api.annotations.WebSocket
import org.koin.java.KoinJavaComponent.inject

@WebSocket
class ArtisanWebSocketHandler {

    private val roasterManager: RoasterManager by inject(RoasterManager::class.java)

    @OnWebSocketConnect
    fun onConnect(session: Session) {
        println("$TAG Connected: ${session.remoteAddress}")
        roasterManager.onArtisanConnected(session)
    }

    @OnWebSocketMessage
    fun onMessage(session: Session, message: String) {
        println("$TAG onMessage: ${session.remoteAddress}, Message: $message")
        roasterManager.onNewArtisanMessageReceived(message)
    }

    @OnWebSocketClose
    fun onClose(session: Session, statusCode: Int, reason: String?) {
        println("$TAG Disconnected: ${session.remoteAddress}, Reason: $reason StatusCode: $statusCode")
        roasterManager.onArtisanDisconnected()
    }

    companion object {

        private const val TAG = "ARTISAN CONTROLLER: "
    }
}