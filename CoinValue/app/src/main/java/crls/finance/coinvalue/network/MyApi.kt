package crls.finance.coinvalue.network

import retrofit2.Call
import retrofit2.http.GET




interface MyApi{
    @GET("EUR-USD,EUR-BRL,EUR-GBP,BTC-EUR")//,EUR-BRL,EUR-GBP,BTC-EUR ou /{coinsToConvert}
    //fun fetchCoins(@Path("coinsToConvert")  coinsToConvert : String): Call<CoinsResponse>
    fun fetchCoins(): Call<CoinsResponse>//@Query("page") page : String

}