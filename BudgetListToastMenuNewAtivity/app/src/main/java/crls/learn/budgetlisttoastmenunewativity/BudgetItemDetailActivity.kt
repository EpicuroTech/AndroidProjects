package crls.learn.budgetlisttoastmenunewativity

import android.app.Activity
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import crls.learn.budgetlisttoastmenunewativity.databinding.ActivityBudgetItemDetailBinding

class BudgetItemDetailActivity : AppCompatActivity() {
    // necessário para poder carregar numa linha da lista!!!
    var position = -1
    // permite que a variavél vá buscar valor a um campo e tbm atribuir valor a um campo
    var value : Double
        get(){
            return findViewById<EditText>(R.id.editTextValue).text.toString().toDouble()
        }
        set(number){
            if (number >= 0)
                findViewById<EditText>(R.id.editTextValue).setText(number.toString())
        }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_budget_item_detail)

        // (2.3) - declarar as variaveis para os editText's na view
        val editTextName = findViewById<EditText>(R.id.editTextNome)
        val editTextValue = findViewById<EditText>(R.id.editTextValue)

        intent.extras?.let {
            position = it.getInt(DATA_POSITION,position)
            value = it.getDouble(DATA_VALUE)
            val description = it.getString(DATA_NAME)
            editTextName.setText(description)
        }

        // (2.3) - Declarar o que faz ao primir o botão, coloca dados no intent
        //       e fecha a ativity trazendo os dados para a main.
        findViewById<Button>(R.id.buttonDone).setOnClickListener {
            val intent = Intent()
            intent.putExtra(DATA_POSITION, position)
            intent.putExtra(DATA_NAME,editTextName.text.toString())
            intent.putExtra(DATA_VALUE, value)

            setResult(Activity.RESULT_OK, intent)
            // finish fecha esta ativity
            finish()
        }
    }


    // Declarar variavel companion object para poder ser usado na main ativity
    // e e nao nos enganarmos na key
    companion object {
        const val DATA_NAME = "data_name"
        const val DATA_VALUE = "data_value"
        // posicao necessaria para poder carregar num item da lista
        const val DATA_POSITION = "data_position"
    }
}