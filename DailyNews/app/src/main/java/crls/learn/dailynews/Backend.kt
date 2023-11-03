package crls.learn.dailynews

import android.graphics.Bitmap
import android.graphics.BitmapFactory
import androidx.lifecycle.LifecycleCoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import okhttp3.OkHttpClient
import okhttp3.Request
import org.json.JSONArray
import org.json.JSONObject

//classe estatica
object Backend {

    private val client = OkHttpClient()
    private val BASE_API = "https://newsapi.org/v2/"
    private val PATH_TOP_HEADLINES = "top-headlines"
    private val API_KEY = "&apiKey=1765f87e4ebc40229e80fd0f75b6416c"

    fun fetchArticles(lifecycleScope: LifecycleCoroutineScope, category: String,
                      callback: (ArrayList<Article>) -> Unit ) {
        // mudança de thread, para correr codigo assincrono, para o restante ficar em modo de espera
        //GlobalScope.launch(Dispatchers.IO) {
        //lifecycleScope para uso em activitys

        lifecycleScope.launch(Dispatchers.IO) {
            val request = Request.Builder()
                .url("${BASE_API}${PATH_TOP_HEADLINES}?country=us&category=${category}${API_KEY}")
                .build()


            //o codigo seguinte faz um request, logo é codigo assincrono
            client.newCall(request).execute().use { response ->
                val result = response.body!!.string()

                //criar um objeto json com o result
                val jsonResult = JSONObject(result)
                //comprar com o status que é uma propriedade no json, se o resultado é ok
                val status = jsonResult["status"] as String
                if (status == "ok") {
                    val jsonArray = jsonResult["articles"] as JSONArray
                    val articles = arrayListOf<Article>()
                    for (index in 0..jsonArray.length() - 1) {
                        val jsonArticle = jsonArray.getJSONObject(index)

                        val article = Article.fromJson(jsonArticle)
                        articles.add(article)

                    }
                    //this@MainActivity.articles = articles

                    lifecycleScope.launch(Dispatchers.Main) {
                        //verifica se há novos dados para mostrar
                        // articlesAdapter.notifyDataSetChanged()
                        callback(articles)
                    }
                }
            }
        }
    }
    fun fetchImage(lifecycleScope: LifecycleCoroutineScope, url: String, callback: (Bitmap) -> Unit){
        lifecycleScope.launch(Dispatchers.IO) {
            val request = Request.Builder()
                .url(url)
                .build()
            client.newCall(request).execute().use { response->
                response.body?.let{
                    //colocar a resposta os bits num bytestream
                    val input = it.byteStream()
                    val bitmap = BitmapFactory.decodeStream(input)
                    lifecycleScope.launch(Dispatchers.Main){
                        callback(bitmap)
                    }
                }
            }
        }
    }
}