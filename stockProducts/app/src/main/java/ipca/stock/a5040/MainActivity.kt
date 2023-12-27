package ipca.stock.a5040

import android.app.Activity
import android.content.Intent
import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.Button
import android.widget.ListView
import android.widget.TextView
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts

class MainActivity : AppCompatActivity() {

    var stockList = arrayListOf<Product>(
        Product("Cabo XV3G2,5(m)", 3000, 305),
        Product("Tomadas schuko", 20, 5),
        Product("Ficha RJ45", 1, 20),
        Product("Conetor F RG6", 100, 20),
        Product("Casquilho", 10, 20),
        Product("Ligador 5", 100, 20)
    )
    //var lowStockList = arrayListOf<Product>()

    lateinit var listViewStock: ListView
    val stockAdapter = StockAdapter(stockList)

    val resultLauncher =
        registerForActivityResult(
            ActivityResultContracts
            .StartActivityForResult()
        ) {
            if (it.resultCode == Activity.RESULT_OK) {
                it.data?.let { intent ->
                    val description = intent.extras?.getString(ProductDetailActivity.DATA_DESCRIPTION)?:""
                    val quantity = intent.extras?.getInt(ProductDetailActivity.DATA_QUANTITY)?:0
                    val minimalQuantity = intent.extras?.getInt(ProductDetailActivity.DATA_MINIMALQUANTITY)?:0

                    val position = intent.extras?.getInt(ProductDetailActivity.DATA_POSITION)?:-1

                    if (position >= 0) {
                        stockList[position].description = description
                        stockList[position].quantity = quantity
                        stockList[position].minimalQuantity = minimalQuantity

                    } else {
                        val budgetItem = Product(description, quantity, minimalQuantity)
                        stockList.add(budgetItem)
                    }
                    stockAdapter.list = stockList
                    stockAdapter.notifyDataSetChanged()
                }
            }else if (it.resultCode == Activity.RESULT_CANCELED){
                Toast.makeText(applicationContext, "Cancelada a adição de produto!", Toast.LENGTH_SHORT).show()
            }
        }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        listViewStock = findViewById<ListView>(R.id.listViewStock)
        listViewStock.adapter = stockAdapter

        findViewById<Button>(R.id.buttonAdd).setOnClickListener {
            val intent = Intent(this,ProductDetailActivity::class.java )
            resultLauncher.launch(intent)
        }

        findViewById<Button>(R.id.buttonMinimalStock).setOnClickListener {
//            for(product in stockList) {
//                if(product.quantity<product.minimalQuantity){
//                    lowStockList.add(product)
//                }
//            }
            //stockAdapter.list = lowStockList
            //ou com a mesma lista sem a alterar
            stockAdapter.list = stockList.filter{
                it.quantity < it.minimalQuantity} as ArrayList<Product>
            stockAdapter.notifyDataSetChanged()
        }
    }

    inner class StockAdapter(var list: ArrayList<Product>): BaseAdapter(){

        override fun getCount(): Int {
            return list.size
        }

        override fun getItem(position: Int): Any {
            return list[position]
        }

        override fun getItemId(position: Int): Long {
            return 0
        }

        override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View {
            val rootView = layoutInflater.inflate(R.layout.row_stock_item,parent, false)
            val textViewDescription = rootView.findViewById<TextView>(R.id.textViewDescription)
            val textViewQuantity = rootView.findViewById<TextView>(R.id.textViewQuantity)
            val textViewMinimalQuantity = rootView.findViewById<TextView>(R.id.textViewMinimalQuantity)

            textViewDescription.text = list[position].description
            textViewQuantity.text = list[position].quantity.toString()
            textViewMinimalQuantity.text = list[position].minimalQuantity.toString()

            if (list[position].quantity < list[position].minimalQuantity) {
                textViewDescription.setTextColor(Color.parseColor("#FF0000"))
                textViewQuantity.setTextColor(Color.parseColor("#FF0000"))
                textViewMinimalQuantity.setTextColor(Color.parseColor("#FF0000"))
            }
            return rootView
        }
    }
    //com outro adapter....
//    inner class MinimalStockAdapter : BaseAdapter() {
//        override fun getCount(): Int {
//            return stockList.size
//        }
//
//        override fun getItem(position: Int): Any {
//            return stockList[position]
//        }
//
//        override fun getItemId(position: Int): Long {
//            return 0
//        }
//
//        override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View {
//            val rootView = layoutInflater.inflate(R.layout.row_stock_item, parent, false)
//            val textViewDescription = rootView.findViewById<TextView>(R.id.textViewDescription)
//            val textViewQuantity = rootView.findViewById<TextView>(R.id.textViewQuantity)
//            val textViewMinimalQuantity =
//                rootView.findViewById<TextView>(R.id.textViewMinimalQuantity)
//
//            if (stockList[position].quantity < stockList[position].minimalQuantity) {
//                textViewDescription.text = stockList[position].description
//                textViewQuantity.text = stockList[position].quantity.toString()
//                textViewMinimalQuantity.text = stockList[position].minimalQuantity.toString()
//                textViewDescription.setTextColor(Color.parseColor("#FF0000"))
//                textViewQuantity.setTextColor(Color.parseColor("#FF0000"))
//                textViewMinimalQuantity.setTextColor(Color.parseColor("#FF0000"))
//                return rootView
//            }
//            return layoutInflater.inflate(R.layout.empty_view, parent, false)
//        }
}