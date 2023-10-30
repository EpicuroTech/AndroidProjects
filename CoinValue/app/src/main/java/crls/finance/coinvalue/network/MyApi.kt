package crls.finance.coinvalue.network

import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path


interface MyApi{
    @GET("{coinComboAsked}")//,EUR-BRL,EUR-GBP,BTC-EUR
    //fun fetchCoins(@Path("coinComboAsked") coinComboAsked : String) : Call<CoinsResponse>
    fun fetchCoins(@Path("coinComboAsked") coinComboAsked: String): Call<Map<String, Coin>>
}