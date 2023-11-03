package crls.learn.listviewinfinity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.ImageView
import android.widget.ListView
import android.widget.TextView
import crls.learn.listviewinfinity.databinding.ActivityMainBinding
import crls.learn.listviewinfinity.databinding.RowArticleBinding
import java.util.Date

class MainActivity : AppCompatActivity() {

    //usar bindingview, não esquecer adicionar no gradle, "ActivityMainBinding"
    //é gerado automaticamente, deve corresponder ao nome da view em layout
    private lateinit var binding : ActivityMainBinding


    //lista do tipo artigo já com 3 artigos
    var articles = arrayListOf<Article>(
        Article("aaaaaaaa", "sdkjhsdkfksdjhf","","", Date()),
        Article("bbbbbbbb", "sdfsdfsdfsdfsdf","","", Date()),
        Article("cccccccc", "sdfsdfsdfsdfsdf","","", Date())
    )

    // instancia o adapter que criamos abaixo
    val articlesAdapter = ArticlesAdapter()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        //==================================================================================

        //setContentView(R.layout.activity_main)
        //val listViewArticles = findViewById<ListView>(R.id.listViewArticles)
        //listViewArticle.adapter = articlesAdapter

        //==================================================================================
        //  USANDO A BINDING VIEW

        //temos que passar a binding.root mas antes atribuir lhe o layout
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        //atribuir ao adapter da list view o adapter inicializado em cima
        binding.listViewArticles.adapter = articlesAdapter

        //==================================================================================
    }
    //criação da class ArticlesAdapter para vizualizar e retirar informaçao da posiçao dos artigos em forma de lista infinita
    inner class ArticlesAdapter : BaseAdapter() {

        override fun getCount(): Int {
            return articles.size
        }

        override fun getItem(position: Int): Any {
            return articles[position]
        }

        override fun getItemId(position: Int): Long {
            return 0
        }

        override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View {
            val rootView = layoutInflater.inflate(R.layout.row_article,parent, false)
            val textViewTitle = rootView.findViewById<TextView>(R.id.textViewTitle)
            val textViewDescription = rootView.findViewById<TextView>(R.id.textViewDescription)
            val textViewDate = rootView.findViewById<TextView>(R.id.textViewDate)
            val imageViewArticle = rootView.findViewById<ImageView>(R.id.imageViewArticle)

            //colocar em cada textview o respetivo conteudo
            textViewTitle.text = articles[position].title
            textViewDescription.text = articles[position].description
            textViewDate.text = articles[position].publishedAt.toString()

            rootView.setOnClickListener {
            }
            return rootView
        }
    }
}