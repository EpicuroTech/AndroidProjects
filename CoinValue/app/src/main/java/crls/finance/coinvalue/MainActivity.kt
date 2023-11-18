package crls.finance.coinvalue

import android.os.Bundle
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import crls.finance.coinvalue.databinding.ActivityMainBinding
import crls.finance.coinvalue.network.Backend


class MainActivity : AppCompatActivity() {
    //fazer a binding view
    private lateinit var binding : ActivityMainBinding
    //variavel para taggar a resposta

    val coinSuported = listOf("EUR-BRL", "BRL-EUR", "EUR-USD", "USD-EUR", "BTC-EUR", "BTC-USD", "BTC-BRL", "EUR-GBP", "GBP-EUR", "USD-GBP")
    var coinComboAsked = "EUR-BRL"
    lateinit var coinComboResult : Coin
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        //Carregar o spinner com a lista de moedas possiveis
        val adapter = ArrayAdapter(this, android.R.layout.simple_spinner_item, coinSuported)
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        binding.spinnerCoin.adapter = adapter

        binding.buttonConvert.setOnClickListener {
            coinComboAsked = binding.spinnerCoin.selectedItem.toString()

            if (!binding.editTextValue.text.isNullOrEmpty()) {
                //val value: Double = binding.editTextValue.text.toString().toDouble()?:1.0
                val value: Double = try {
                    binding.editTextValue.text.toString().toDouble()
                } catch (e: NumberFormatException) {
                    1.0 // Set a default value if conversion fails
                }

                if (value > 0.01) {
                    Backend.fetchCoins(lifecycleScope, coinComboAsked) {
                        this.coinComboResult = it
                        val result = value * coinComboResult.bid.toDouble()
                        val coinOut = coinComboResult.codein
                        Toast.makeText(applicationContext, "${result.toString()} $coinOut",
                            Toast.LENGTH_LONG).show()
                    }
                }
            }
            else{
                Toast.makeText(applicationContext, "Please insert a value to convert!",
                Toast.LENGTH_SHORT).show()
            }
        }
    }
}