package crls.learn.retrofitexample

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MainActivity : AppCompatActivity() {

    private val TAG : String = "CHECK_RESPONSE"
    private var listPost : List<PostEntity>? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val textView = findViewById<TextView>(R.id.textView)
        val button = findViewById<Button>(R.id.button1)

        val service = RetrofitClient.createService(PostService::class.java)
        val call: Call<List<PostEntity>> = service.list()

        call.enqueue(object : Callback<List<PostEntity>>{

            override fun onResponse(
                call: Call<List<PostEntity>>,
                response: Response<List<PostEntity>>
            ) {
                Toast.makeText(applicationContext, "OK", Toast.LENGTH_SHORT).show()
                listPost = response.body()
                if (listPost != null) {
                    for (post in listPost!!){
                        Log.i(TAG, "onResponse: ${post.body}")
                    }
                }
            }

            override fun onFailure(call: Call<List<PostEntity>>, t: Throwable) {
                Toast.makeText(applicationContext, "Error", Toast.LENGTH_SHORT).show()
            }
        })
//        if (!listPost.isNullOrEmpty()){
//            val a : PostEntity?  = listPost!![1]
//            Toast.makeText(applicationContext, "Titulo: ${a?.title}", Toast.LENGTH_LONG).show()
//
//        }
        var index = 0
        button.setOnClickListener {
            textView.text = listPost?.get(index)?.title.toString()
            index ++
        }
    }
}