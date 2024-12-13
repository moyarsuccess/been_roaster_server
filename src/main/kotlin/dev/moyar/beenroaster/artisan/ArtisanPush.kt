package dev.moyar.beenroaster.dev.moyar.beenroaster.artisan

data class ArtisanPushMessage(
    val pushMessage: String,
) {
    companion object {
        val startRoasting = ArtisanPushMessage(pushMessage = "startRoasting")
        val endRoasting = ArtisanPushMessage(pushMessage = "endRoasting")
    }
}

data class ArtisanPushEvent(
    val pushMessage: String = "addEvent",
    val data: ArtisanPushEventData
) {
    companion object {
        val colorChange = ArtisanPushEvent(data = ArtisanPushEventData("colorChangeEvent"))
        val firstCrackStarted = ArtisanPushEvent(data = ArtisanPushEventData("firstCrackBeginningEvent"))
        val firstCrackEnded = ArtisanPushEvent(data = ArtisanPushEventData("firstCrackEndEvent"))
        val secondCrackStarted = ArtisanPushEvent(data = ArtisanPushEventData("secondCrackBeginningEvent"))
        val secondCrackEnded = ArtisanPushEvent(data = ArtisanPushEventData("secondCrackEndEvent"))
    }
}

data class ArtisanPushEventData(
    val event: String,
)