package dev.moyar.beenroaster.dev.moyar.beenroaster.artisan

data class ArtisanPushMessage(
    val pushMessage: String,
) {
    companion object {
        val startRoasting = ArtisanPushMessage(pushMessage = "startRoasting")
        val endRoasting = ArtisanPushMessage(pushMessage = "endRoasting")
    }
}