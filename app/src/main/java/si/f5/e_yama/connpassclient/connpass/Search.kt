package si.f5.e_yama.connpassclient.connpass

import si.f5.e_yama.connpassclient.json.ConnpassApiResult
import si.f5.e_yama.connpassclient.json.ConnpassEvent
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

class Search(
    private val id: List<Int>?,
    private val keywordAnd: List<String>?,
    private val keywordOr: List<String>?,
    private val yearMonth: List<Int>?,
    private val yearMonthDay: List<Int>?,
    private val nickname: List<String>?,
    private val ownerNickname: List<String>?,
    private val series: List<Int>?,
    private val order: Order?
) {
    companion object {
        lateinit var SERVICE: ConnpassService
    }
    private var page: Int = 0
    private lateinit var current: ConnpassApiResult

    fun next(): List<ConnpassEvent> {
        current = search()
        page++
        return current.events
    }

    private fun search(): ConnpassApiResult {
        return SERVICE.search(
            id,
            keywordAnd,
            keywordOr,
            yearMonth,
            yearMonthDay,
            nickname,
            ownerNickname,
            series,
            page * 10 + 1,
            order?.ordinal?.plus(1) ?: 1,
            10
        ).execute().body() ?: ConnpassApiResult(0, 0, 0, arrayListOf())
    }

    interface ConnpassService {
        @GET("api/v1/event")
        fun search(
            @Query("event_id") id: List<Int>?,
            @Query("keyword") keywordAnd: List<String>?,
            @Query("keyword_or") keywordOr: List<String>?,
            @Query("ym") dateYearMonth: List<Int>?,
            @Query("ymd") dateYearMonthDay: List<Int>?,
            @Query("nickname") nickname: List<String>?,
            @Query("owner_nickname") ownerNickname: List<String>?,
            @Query("series_id") series: List<Int>?,
            @Query("start") start: Int?,
            @Query("order") order: Int?,
            @Query("count") count: Int?
        ): Call<ConnpassApiResult>
    }

    enum class Order {
        UPDATE, START, LATEST
    }
}