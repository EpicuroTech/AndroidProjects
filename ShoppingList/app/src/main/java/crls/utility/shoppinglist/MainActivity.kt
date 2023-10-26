package crls.utility.shoppinglist

import android.app.Activity
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.Button
import android.widget.CheckBox
import android.widget.ListView
import android.widget.TextView
import androidx.activity.result.contract.ActivityResultContracts

class MainActivity : AppCompatActivity() {

    val products = arrayListOf<Product>(
        Product("Maças", 3, false),
        Product("Arroz", 4, true),
        Product("Pipocas", 2, false)
    )
    val productAdapter = ProductAdapter()

    val resultLauncher =
        registerForActivityResult(ActivityResultContracts.StartActivityForResult()){
        if (it.resultCode == Activity.RESULT_OK){
            it.data?.let{intent->
                val position = intent.extras?.getInt(ProductDetailActivity.DATA_POSITION)?:-1
                val qtt = intent.extras?.getInt(ProductDetailActivity.DATA_QTT)?:0
                val nameProduct = intent.extras?.getString(ProductDetailActivity.DATA_PRODUCT_NAME)?:""
                if (position >= 0){
                        products[position].name = nameProduct!!
                        products[position].qtt=qtt!!
                }
                else{
                    val product = Product(nameProduct!!, qtt)
                    products.add(product)
                }
                productAdapter.notifyDataSetChanged()

            }
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val listViewProducts = findViewById<ListView>(R.id.listViewProducts)
        listViewProducts.adapter = productAdapter

        val buttonAdd = findViewById<Button>(R.id.buttonAddProduct)

        buttonAdd.setOnClickListener {
            //criar intent para tentar abrir ProductDetailActivity
            val intent = Intent(this, ProductDetailActivity::class.java)
            resultLauncher.launch(intent) // agora declarar no android manifest
        }
    }

    //inner uma class interior e tem acesso ao que está acima
    inner class ProductAdapter : BaseAdapter(){
        override fun getCount(): Int {
            return products.size
        }

        override fun getItem(position: Int): Any {
            return products[position]
        }

        override fun getItemId(position: Int): Long {
            return 0
        }

        override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View {
            //cria view, layoutinflater devolve views apartir de ficheiro xml e precisa de
            // uma view parente para dizer em que view ela etá a ser criada
            val rootView = layoutInflater.inflate(R.layout.row_product, parent, false)
            val textViewProductName = rootView.findViewById<TextView>(R.id.textViewProductName)
            val textViewProductQuantity = rootView.findViewById<TextView>(R.id.textViewProductQuantity)
            val checkBox = rootView.findViewById<CheckBox>(R.id.checkBox)

            textViewProductName.text = products[position].name
            textViewProductQuantity.text = products[position].qtt.toString()
            checkBox.isChecked = products[position].isChecked

            rootView.setOnClickListener{
                val intent = Intent(this@MainActivity, ProductDetailActivity::class.java)
                intent.putExtra(ProductDetailActivity.DATA_PRODUCT_NAME, products[position].name)
                intent.putExtra(ProductDetailActivity.DATA_QTT, products[position].qtt)
                intent.putExtra(ProductDetailActivity.DATA_POSITION, position)
                resultLauncher.launch(intent) // agora declarar no android manifest
            }
            return rootView
        }


    }
}