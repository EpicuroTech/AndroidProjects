package crls.finance.coinvalue

import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import crls.finance.coinvalue.databinding.ActivityMainBinding
import crls.finance.coinvalue.network.CoinsResponse
import crls.finance.coinvalue.network.MyApi
import crls.finance.coinvalue.network.RetrofitClient
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MainActivity : AppCompatActivity() {
    private lateinit var binding : ActivityMainBinding

    private var listCoins : CoinsResponse? = null
    private val TAG = "CHECK_RESPONSE"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val client = RetrofitClient
            .createService(MyApi::class.java).fetchCoins()//1 ou EURUSD

        listCoins = getAllCoins(client)

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

    private fun getAllCoins (call : Call<CoinsResponse>) : CoinsResponse?{
        var result : CoinsResponse? = null
        call.enqueue(object : Callback<CoinsResponse> {
            override fun onResponse(call: Call<CoinsResponse>, response: Response<CoinsResponse>)
            {
                if(response.isSuccessful) {
                    Log.d(TAG, "onResponse: ${response.body()}")
                    Toast.makeText(applicationContext, "OK", Toast.LENGTH_SHORT).show()
                    result = response.body()
//                    if (result != null) {
//                        for (coin in result) {
//                            Log.i(TAG, "onResponse: ${coin}")
//                        }
//                    }
                }
            }
            override fun onFailure(call: Call<CoinsResponse>, t: Throwable)
            {
                Log.e(TAG, "onFailure: ${t.message}")
                Toast.makeText(applicationContext, "Error", Toast.LENGTH_SHORT).show()
            }
        })
        return result
    }
}