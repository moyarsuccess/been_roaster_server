package dev.moyar.beenroaster.dev.moyar.beenroaster.artisan

import com.google.gson.annotations.SerializedName

data class ArtisanResponseData(
    @SerializedName("BT") val bt: Float,
    @SerializedName("ET") val et: Float,
)