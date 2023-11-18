package crls.finance.coinvalue

import org.json.JSONObject


data class Coin (
    var code        : String,
    var codein      : String,
    var name        : String,
    var high        : String,
    var low         : String,
    var varBid      : String,
    var pctChange   : String,
    var bid         : String,
    var ask         : String,
    var timestamp   : String,
    var create_date : String
) {
    fun toJson(): JSONObject {
        val jsonObject = JSONObject()
        jsonObject.put("code"       , code)
        jsonObject.put("codein"     , codein)
        jsonObject.put("name"       , name)
        jsonObject.put("high"       , high)
        jsonObject.put("low"        , low)
        jsonObject.put("varBid"     , varBid)
        jsonObject.put("pctChange"  , pctChange)
        jsonObject.put("bid"        , bid)
        jsonObject.put("ask"        , ask)
        jsonObject.put("timestamp"  , timestamp)
        jsonObject.put("create_date", create_date)
        return jsonObject
    }

    companion object {
        fun fromJson(jsonObject: JSONObject): Coin {
            return Coin(
                jsonObject["code"       ] as String,
                jsonObject["codein"     ] as String,
                jsonObject["name"       ] as String,
                jsonObject["high"       ] as String,
                jsonObject["low"        ] as String,
                jsonObject["varBid"     ] as String,
                jsonObject["pctChange"  ] as String,
                jsonObject["bid"        ] as String,
                jsonObject["ask"        ] as String,
                jsonObject["timestamp"  ] as String,
                jsonObject["create_date"] as String
            )
        }
    }
}
