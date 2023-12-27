package ipca.stock.a5040

import android.app.Activity
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast

class ProductDetailActivity : AppCompatActivity() {

    var position = -1

    var quantity : Int
        get(){
            return findViewById<EditText>(R.id.editTextTextQuantity).text.toString().toInt()
        }
        set(number){
            if (number >= 0)
                findViewById<EditText>(R.id.editTextTextQuantity).setText(number.toString())
        }
    var minimalQuantity : Int
        get(){
            return findViewById<EditText>(R.id.editTextTextMinimalQuantity).text.toString().toInt()
        }
        set(number){
            if (number >= 0)
                findViewById<EditText>(R.id.editTextTextMinimalQuantity).setText(number.toString())
        }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_product_detail)

        // (2.3) - declarar as variaveis para os editText's na view
        val editTextDescription = findViewById<EditText>(R.id.editTextTextDescription)
        //val editTextQuantity = findViewById<EditText>(R.id.editTextTextQuantity)
        //val editTextMinimalQuantity = findViewById<EditText>(R.id.editTextTextMinimalQuantity)

//        // (4.2) - Necessário para o EDITAR uma linha da listview
//        //          carrega os dados na nova activity se existir intent.extras
//        intent.extras?.let {
//            position = it.getInt(DATA_POSITION,position)
//            quantity = it.getInt(DATA_QUANTITY)
//            minimalQuantity = it.getInt(DATA_MINIMALQUANTITY)
//            val description = it.getString(DATA_DESCRIPTION)
//            editTextDescription.setText(description)
//        }

        // (2.3) - Declarar o que faz ao primir o botão, coloca dados no intent
        //       e fecha a ativity trazendo os dados para a main.
        findViewById<Button>(R.id.buttonDone).setOnClickListener {
            //val intent = Intent()
            // (4.2) - posiçao é necessária para o editar
            if(editTextDescription.text.isNotEmpty()) {
                intent.putExtra(DATA_POSITION, position)
                intent.putExtra(DATA_DESCRIPTION, editTextDescription.text.toString())
                intent.putExtra(DATA_QUANTITY, quantity)
                intent.putExtra(DATA_MINIMALQUANTITY, minimalQuantity)
                setResult(Activity.RESULT_OK, intent)
            }else setResult(Activity.RESULT_CANCELED, intent)
            // finish fecha esta ativity
            finish()
        }
    }


    // Declarar variavel companion object para poder ser usado na main ativity
    // e e nao nos enganarmos na key
    companion object {
        const val DATA_DESCRIPTION = "data_description"
        const val DATA_QUANTITY = "data_quantity"
        const val DATA_MINIMALQUANTITY = "data_minimal_quantity"
        const val DATA_POSITION = "data_position"
    }
}