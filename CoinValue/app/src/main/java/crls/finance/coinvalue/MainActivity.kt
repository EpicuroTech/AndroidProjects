package crls.finance.coinvalue

import android.os.Bundle
import android.util.Log
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
    private lateinit var binding : ActivityMainBinding

    private var coinComboResult : Coin? = null
    private val TAG = "CHECK_RESPONSE"

    private var coinComboAsked = "EUR-USD"
    private var coinComboNoBar = coinComboAsked.replace("-","")

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val client = RetrofitClient
            .createService(MyApi::class.java).fetchCoins(coinComboAsked)//1 ou EURUSD

        coinComboResult = getAllCoins(client)

//        binding.buttonConvert.setOnClickListener {
//            val moeda : String? = listCoins!![0].name
//
//            if (!moeda.isNullOrEmpty()) {
//                binding.textView3.text = moeda!!
//            }
//            val euro = binding.editTextValue.text.toString().trim()
//
//            if (!euro.isEmpty()){
//                val result = euro.toDouble()
//                Toast.makeText(applicationContext,"$result", Toast.LENGTH_SHORT).show()
//            }
//        }
    }
    private fun getAllCoins (call : Call<Map<String,Coin>>) : Coin?{
        var result : Coin? = null
        call.enqueue(object : Callback<Map<String,Coin>> {
            override fun onResponse(call: Call<Map<String,Coin>>, response: Response<Map<String,Coin>>)
            {

                if(response.isSuccessful) {
                    Log.d(TAG, "onResponse: ${response.body()}")
                    Toast.makeText(applicationContext, "OK", Toast.LENGTH_SHORT).show()
                    result = response.body()!![coinComboNoBar]
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