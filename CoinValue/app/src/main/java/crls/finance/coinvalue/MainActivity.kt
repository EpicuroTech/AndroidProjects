package crls.finance.coinvalue

import android.os.Bundle
import android.util.Log
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import crls.finance.coinvalue.databinding.ActivityMainBinding
import crls.finance.coinvalue.network.Coin
import crls.finance.coinvalue.network.MyApi
import crls.finance.coinvalue.network.RetrofitClient
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MainActivity : AppCompatActivity() {
    //fazer a binding view
    private lateinit var binding : ActivityMainBinding
    //variavel para taggar a resposta
    private val TAG = "CHECK_RESPONSE"


    val coinSuported = listOf("EUR-BRL", "BRL-EUR", "EUR-USD", "USD-EUR", "BTC-EUR", "BTC-USD", "BTC-BRL", "EUR-GBP", "GBP-EUR", "USD-GBP")
    var coinComboAsked = "EUR-BRL"
    var coinComboResult : Coin? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        //Carregar o spinner com a lista de moedas possiveis
        val adapter = ArrayAdapter(this, android.R.layout.simple_spinner_item, coinSuported)
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        binding.spinnerCoin.adapter = adapter



        //CHECK_RESPONSE
        //coinComboResult = getAllCoins(client)

        binding.buttonConvert.setOnClickListener {
            coinComboAsked = binding.spinnerCoin.selectedItem.toString().trim()

            if (binding.editTextValue.text.isNullOrEmpty()) {
                Toast.makeText(applicationContext,"Please insert a value to convert!", Toast.LENGTH_SHORT).show()
            }
            val value = binding.editTextValue.text.toString().toDouble()


            if (value > 0.01){
                val client = RetrofitClient
                    .createService(MyApi::class.java).fetchCoins(coinComboAsked)
                try {
                    coinComboResult = getAllCoins(client)
                    val result = value * coinComboResult!!.bid
                    val coinOut = coinComboResult!!.codein
                    Toast.makeText(applicationContext, "$result $coinOut", Toast.LENGTH_LONG).show()
                } catch (e: Exception) {
                    Toast.makeText(applicationContext, "Error: ${e.message}", Toast.LENGTH_LONG).show()
                }
            }
        }
    }



    private fun getAllCoins (call : Call<Map<String,Coin>>) : Coin?{
        var result : Coin? = null
        call.enqueue(object : Callback<Map<String,Coin>> {
            override fun onResponse(call: Call<Map<String,Coin>>, response: Response<Map<String,Coin>>)
            {

                if(response.isSuccessful) {
                    Log.d(TAG, "onResponse: ${response.body()}")
                    Toast.makeText(applicationContext, "OK", Toast.LENGTH_SHORT).show()
                    result = response.body()!![coinComboAsked.replace("-","")]
                }
            }
            override fun onFailure(call: Call<Map<String,Coin>>, t: Throwable)
            {
                Log.e(TAG, "onFailure: ${t.message}")
                Toast.makeText(applicationContext, "Error", Toast.LENGTH_SHORT).show()
            }
        })
        return result
    }
}