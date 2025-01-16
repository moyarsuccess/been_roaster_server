package dev.moyar.beenroaster.dev.moyar.beenroaster.temp

import kotlin.random.Random

class TempHelper {

    val envTemp: Float
        get() = Random.nextFloat()

    val beenTemp: Float
        get() = Random.nextFloat()
}