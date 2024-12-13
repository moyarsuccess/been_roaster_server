package dev.moyar.beenroaster.dev.moyar.beenroaster.prop

import java.io.File
import java.io.FileInputStream
import java.io.InputStreamReader
import java.util.*

object LocalProperties {

    private val prop by lazy {
        val file = File("local.properties")
        val prop = Properties()
        InputStreamReader(FileInputStream(file), Charsets.UTF_8).use { reader ->
            prop.load(reader)
        }
        prop
    }

    val serverAddress by lazy {
        prop.getProperty("server_address")
    }

    val serverPort by lazy {
        prop.getProperty("server_port").toInt()
    }
}