package crls.finance.coinvalue.network

import com.squareup.moshi.Json


data class Coin (

    @Json(name = "code")
    var code: String = "",

    @Json(name = "codein")
    var codein: String = "",

    @Json(name = "name")
    var name: String = "",

    @Json(name = "high")
    var high: Double = 0.0,

    @Json(name = "low")
    var low: Double = 0.0,

    @Json(name = "varBid")
    var varBid: Double = 0.0,

    @Json(name = "pctChange")
    var pctChange: Double = 0.0,

    @Json(name = "bid")
    var bid: Double = 0.0,

    @Json(name = "ask")
    var ask: Double = 0.0,

    @Json(name = "timestamp")
    var timestamp: String = "",

    @Json(name = "create_date")
    var create_date: String = ""

)

//data class CoinsResponse(
//    val coinCombo : Map<String, Coin>
//)
//val coin: Coin = Coin()