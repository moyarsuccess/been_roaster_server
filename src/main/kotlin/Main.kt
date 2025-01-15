package dev.moyar.beenroaster

import dev.moyar.beenroaster.dev.moyar.beenroaster.artisan.ArtisanWebSocketHandler
import dev.moyar.beenroaster.dev.moyar.beenroaster.device_controller.DeviceControllerWebSocket
import dev.moyar.beenroaster.dev.moyar.beenroaster.di.applicationModule
import dev.moyar.beenroaster.dev.moyar.beenroaster.prop.LocalProperties
import org.koin.core.context.startKoin
import spark.Spark

fun main() {

    startKoin {
        modules(applicationModule)
    }

    Spark.port(LocalProperties.serverPort)
    Spark.webSocket("/artisan", ArtisanWebSocketHandler::class.java)
    Spark.webSocket("/controller", DeviceControllerWebSocket::class.java)

    Spark.init()
    println("Been roaster server is running on ws://${LocalProperties.serverAddress}:${LocalProperties.serverPort}")
}