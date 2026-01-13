package si.f5.e_yama.connpassclient.json

import com.google.gson.annotations.SerializedName

data class ConnpassApiResult(
    @SerializedName("results_returned") val size : Int,
    @SerializedName("results_available") val all : Int,
    @SerializedName("results_start") val start : Int,
    val events: List<ConnpassEvent>
)