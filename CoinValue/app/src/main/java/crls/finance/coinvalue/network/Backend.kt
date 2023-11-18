package crls.finance.coinvalue.network

import androidx.lifecycle.LifecycleCoroutineScope
import crls.finance.coinvalue.Coin
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import okhttp3.OkHttpClient
import okhttp3.Request
import org.json.JSONObject
import java.io.IOException

object Backend {
    private const val BASE_API = "https://economia.awesomeapi.com.br/last/"

    private val client = OkHttpClient()

    fun fetchCoins(lifecycleScope: LifecycleCoroutineScope, coinCombo:String, callback:(Coin)->Unit )
    {
        lifecycleScope.launch(Dispatchers.IO) {
            try {
                val request = Request.Builder()
                    .url("${BASE_API}${coinCombo}")
                    .build()

                client.newCall(request).execute().use { response ->

                    if (!response.isSuccessful) {
                        // Handle unsuccessful response
                        // You can throw an exception or handle the error accordingly
                        throw IOException("Unexpected code ${response.code}")
                    }

                    val result = response.body!!.string()
                    val jsonCoinCombo = JSONObject(result)
                    val coinComboReplaced = coinCombo.replace("-", "")
                    val jsonCoin = jsonCoinCombo.getJSONObject(coinComboReplaced)
                    val coin = Coin.fromJson(jsonCoin)

                    lifecycleScope.launch(Dispatchers.Main) {
                        callback(coin)
                    }
                }
            } catch (e: IOException) {
                // Handle network errors
                e.printStackTrace()
                // You might want to throw an exception or handle the error here
            }
        }
    }
}