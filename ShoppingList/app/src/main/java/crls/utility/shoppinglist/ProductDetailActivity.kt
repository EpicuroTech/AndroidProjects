package crls.utility.shoppinglist

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import crls.utility.shoppinglist.databinding.ActivityProductDetailBinding

class ProductDetailActivity : AppCompatActivity() {

    //usar o binding! ir ao gradle(app) e ativar
    //lateinit diz ao compiler que fica a minha responsabilidade o facto de ela nunca ser nulla

    //!!usar o nome da view sem underscore, e acrescentar bindind pois esta classe abaixo é
    //gerada automaticamente!!
    private lateinit var binding : ActivityProductDetailBinding

    var position = -1
    var qtd : Int
    get(){
        return binding.textViewQuantity.text.toString().toInt()
    }
        set(value){
            //nao queremos que resultados negativos
            if (value >=0)
                binding.textViewQuantity.text=value.toString()
        }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        //usar binding
        binding = ActivityProductDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)

        intent.extras?.let{
            position = it.getInt(DATA_POSITION, -1)
            qtd = it.getInt(DATA_QTT)
            val name= it.getString(DATA_PRODUCT_NAME)
            binding.editTextTextProductName.setText(name)

        }

        binding.buttonPlus.setOnClickListener{
            qtd += 1
        }
        binding.buttonMinus.setOnClickListener{
            qtd -= 1
        }
        binding.buttonDone.setOnClickListener{
            val intent = Intent()
            intent.putExtra(DATA_POSITION, position)
            intent.putExtra(DATA_QTT,qtd)
            intent.putExtra(DATA_PRODUCT_NAME,binding.editTextTextProductName.text.toString())
            setResult(Activity.RESULT_OK,intent)
            finish()
        }

    }
    //comparavel com static no c# fica disponivel em todo o projeto
    companion object {
        const val DATA_QTT = "data_qtt"
        const val DATA_PRODUCT_NAME = "data_product_name"
        const val DATA_POSITION = "data_position"
    }
}