package si.f5.e_yama.connpassclient.json

import com.google.gson.annotations.SerializedName

data class ConnpassEvent(
    @SerializedName("event_id") val id: Int,
    val title: String,
    val catch: String?,
    val description: String?,
    @SerializedName("event_url") val url: String,
    @SerializedName("hash_tag") val hashtag: String?,
    @SerializedName("started_at") val start: String?,
    @SerializedName("ended_at") val end: String?,
    val limit: Int,
    @SerializedName("event_type") val type: String,
    val series: SeriesObject?,
    val address: String?,
    val place: String?,
    val lat: Double?,
    val lon: Double?,
    @SerializedName("owner_id") val ownerId: Int,
    @SerializedName("owner_nickname") val ownerNickname: String?,
    @SerializedName("owner_display_name") val ownerName: String?,
    val accepted: Int,
    val waiting: Int,
    @SerializedName("updated_at") val updated: String?
)
