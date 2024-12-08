package dev.moyar.beenroaster

import dev.moyar.beenroaster.dev.moyar.beenroaster.artisan.ArtisanWebSocketHandler
import dev.moyar.beenroaster.dev.moyar.beenroaster.device_controller.DeviceControllerWebSocket
import spark.Spark

fun main() {
    Spark.port(8082)
    Spark.webSocket("/", ArtisanWebSocketHandler::class.java)
    Spark.webSocket("/device/controller", DeviceControllerWebSocket::class.java)

    Spark.init()
    println("Been roaster server is running on ws://localhost:8082")
}