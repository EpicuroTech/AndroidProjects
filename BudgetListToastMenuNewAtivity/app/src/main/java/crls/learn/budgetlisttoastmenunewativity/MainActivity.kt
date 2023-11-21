package crls.learn.budgetlisttoastmenunewativity

import android.app.Activity
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.Button
import android.widget.CheckBox
import android.widget.ListView
import android.widget.TextView
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.view.isInvisible
import androidx.core.view.isVisible
import crls.learn.budgetlisttoastmenunewativity.databinding.RowBudgetItemBinding
import java.util.Date

class MainActivity : AppCompatActivity() {
    // A 1 vai até Á 3 do teste
    // 1.0 - CRIAR UMA LISTVIEW INFINITA COM DADOS DE UM OBJETO
    // 1.1 - Criar class para o objeto em questao
    // 1.2 - Criar ListView na activity_main
    // 1.3 - Criar layout row_OBJETO_item, não esquecer de colocar o constrain
    //      layout com width match parent e height wrap content
    // 1.4 - Na main ativity criar o array ou lista para armazenar os objetos e
    //      que se pretende mostrar(assinalado)
    // 1.5 - Declarar a listView e o adapter, não esquecer também na fun onCreate
    //      para depois lhe atribuir-mos o adaper (assinalado)
    // 1.6 - Criar a class do BudgetAdapter do tipo BaseAdapter para e definir
    //      as funções getCount, getItem e getView.(assinalado)

    // A 2 corresponde Á 4 e 5(em vez de menu estamos a usar um botao) do teste
    // 2.0 - CRIAR ativity para adicionar novo item na lista
    // 2.1 - Fazer new ativity, empty view ativity, para criar ficheiro de codigo
    //      e  também ficheiro de layout, e assim tbm já fica no manifest
    // 2.2 - Criar no layout a nova view, neste caso campo para descriçao e quantidade
    // 2.3 - criar codigo na nova ativity para associar dados inseridos ao intent e
    //      trazer para a main ativity.(assinalado)
    // 2.4 - Declarar o que vai fazer o botão ADD, e declarar o resutLancher para
    //      abrir outra ativity e adicionar o novo item á lista com os dados retornados
    //      da outra ativity.
    // 3.0 - Criar Menu com adicionar, ordenar e toast com orçamento total
    // 3.1 - Criar pasta menu dentro da pasta RES
    // 3.2 - Criar xml, menu_main
    // 3.3 - adicionar os menu items
    // 3.4 - ir aos ficheiros teams tirar os .Noactionbar
    // 3.5 - Criar as funçoes do menu, onCreateOptionsMenu e onCreateItemSelected
    // 4.0 - Editar um produto
    // 4.1 - Adicionar setonclicklistner na rootview
    // 4.2 - Alterações necessárias para o EDITAR
    //

    // (1.4) - array do tipo lista com objetos do tipo budgetitem
    var budgetItems = arrayListOf<BudgetItem>(
        BudgetItem("Batatas 1kg", 10.0, Date()),
        BudgetItem("Cebolas 1kg", 5.0, Date()),
        BudgetItem("Cenouras 1kg", 2.0, Date()),
        BudgetItem("Couve 1kg", 1.0, Date())
    )

    // (1.5) - declarar listview e adapter, mais abaixo declarar função budgetadapter,
    //      e atribuir a view da activity main á listview
    lateinit var listViewBudgetItem: ListView
    val adapter = BudgetAdapter()

    // (2.4) - Declarar o resultLauncher, que abre a nova ativity e permite o retorno
    //      de dados da outra ativity.
    val resultLauncher =
        registerForActivityResult(ActivityResultContracts
            .StartActivityForResult()
        ) {
            if (it.resultCode == Activity.RESULT_OK) {
                it.data?.let { intent ->
                    val description = intent.extras?.getString(BudgetItemDetailActivity.DATA_NAME)?:""
                    val value = intent.extras?.getDouble(BudgetItemDetailActivity.DATA_VALUE)?:0.0
                    val date = Date()

                    // (4.2) - necessário para carregar numa linha da lista
                    val position = intent.extras?.getInt(BudgetItemDetailActivity.DATA_POSITION)?:-1
                    // (4.2) - necessário para edição da linha, SENÃO só o codigo do else
                    if (position >= 0) {
                        budgetItems[position].description = description
                        budgetItems[position].value = value
                        budgetItems[position].date = Date()

                    } else {
                        val budgetItem = BudgetItem(description, value, date)
                        budgetItems.add(budgetItem)
                    }
                    adapter.notifyDataSetChanged()
                }
            }
        }
    //private lateinit var bindingRoot:RowBudgetItemBinding


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        // (1.5) - atribuir ao listViewBudgetItem a listview do layout e atribuir o adapter.
        listViewBudgetItem = findViewById<ListView>(R.id.listViewBudgetItems)
        listViewBudgetItem.adapter = adapter



        // (2.4) - Declarar o que faz o botão add
        findViewById<Button>(R.id.buttonAdd).setOnClickListener {
            val intent = Intent(this,BudgetItemDetailActivity::class.java )
            resultLauncher.launch(intent)
        }

        // Outros botões
        findViewById<Button>(R.id.buttonSort).setOnClickListener {
            budgetItems.sortBy {
                it.value
            }
            adapter.notifyDataSetChanged()
            Toast.makeText(this, "Ordered by Quantity", Toast.LENGTH_SHORT).show()
        }
        findViewById<Button>(R.id.buttonTotal).setOnClickListener {
            var result = 0.0
            for(b in budgetItems){
                result += b.value
            }

            // (3.5) - TOAST MESSAGE
            Toast.makeText(this, "Total:${result}", Toast.LENGTH_LONG).show()
        }
    }

    // (1.6) - Declarar BudgetAdapter do tipo BaseAdapter
    inner class BudgetAdapter : BaseAdapter() {
        override fun getCount(): Int {
            return budgetItems.size
        }
    // Não esquecer o position! caso seja criado automáticamente.
        override fun getItem(position: Int): Any {
            return budgetItems[position]
        }

        override fun getItemId(position: Int): Long {
            return 0
        }

        // Dizemos na primeira atribuição que a rootView é a row_budget_item para
        // ter acesso aos campos da row_budget e no fim retornamos a rootView para
        // devolver ao adapter
        // CADA LINHA DA LISTVIEW!!!!
        override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View {
            var bindingRoot = RowBudgetItemBinding.inflate(layoutInflater)
            bindingRoot = RowBudgetItemBinding.inflate(layoutInflater)

            bindingRoot.textViewDescription.text = budgetItems[position].description
            bindingRoot.textViewValue.text = budgetItems[position].value.toString()
            bindingRoot.textViewDate.text = budgetItems[position].date.toString()

            // (4.1) - ao clicar na view abre a nova ativity
            bindingRoot.root.setOnClickListener{
                val intent = Intent(this@MainActivity,BudgetItemDetailActivity::class.java )
                intent.putExtra(BudgetItemDetailActivity.DATA_NAME, budgetItems[position].description)
                intent.putExtra(BudgetItemDetailActivity.DATA_VALUE, budgetItems[position].value)
                intent.putExtra(BudgetItemDetailActivity.DATA_POSITION, position)
                resultLauncher.launch(intent)
            }
//            if (!bindingRoot.checkBox.isChecked){
//                findViewById<Button>(R.id.buttonSort).isClickable=false
//            }

//            val rootView = layoutInflater.inflate(R.layout.row_budget_item,parent, false)
//
//            val textViewDescription = rootView.findViewById<TextView>(R.id.textViewDescription)
//            val textViewValue = rootView.findViewById<TextView>(R.id.textViewValue)
//            val textViewDate = rootView.findViewById<TextView>(R.id.textViewDate)
//
//            textViewDescription.text = budgetItems[position].description
//            textViewValue.text = budgetItems[position].value.toString()
//            textViewDate.text = budgetItems[position].date.toString()
//
//            rootView.setOnClickListener{
//                val intent = Intent(this@MainActivity,BudgetItemDetailActivity::class.java )
//                intent.putExtra(BudgetItemDetailActivity.DATA_NAME, budgetItems[position].description)
//                intent.putExtra(BudgetItemDetailActivity.DATA_VALUE, budgetItems[position].value)
//                intent.putExtra(BudgetItemDetailActivity.DATA_POSITION, position)
//                resultLauncher.launch(intent)
//            }
//            return rootView
            return bindingRoot.root
        }
    }
    // (3.5) - MENU
    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        val inflater: MenuInflater = menuInflater
        inflater.inflate(R.menu.menu_main, menu)
        return true
    }
    // (3.5) - o que faz cada botao do menu
    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        // Handle item selection.
        return when (item.itemId) {
            R.id.action_add -> {
                val intent = Intent(this,BudgetItemDetailActivity::class.java )
                resultLauncher.launch(intent)
                true
            }
            R.id.action_sort -> {
                budgetItems.sortBy {
                    it.value
                }
                adapter.notifyDataSetChanged()
                true
            }
            R.id.action_total -> {
                var result = 0.0
                for(b in budgetItems){
                    result += b.value
                }

                // (3.5) - TOAST MESSAGE
                Toast.makeText(this, "Total:${result}", Toast.LENGTH_LONG).show()
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }
}
