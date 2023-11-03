package crls.learn.dailynews

import org.json.JSONObject
import java.text.SimpleDateFormat
import java.util.Date

data class Article (
    val title        : String?,
    val description  : String?,
    val url          : String,
    val urlToImage   : String?,
    val publishedAt  : Date?
    ){
    companion object{

        //criar o objeto article através de um json object
        fun fromJson(jsonObject: JSONObject): Article{
            return Article(
                jsonObject["title"          ] as? String?,
                jsonObject["description"    ] as? String?,
                jsonObject["url"            ] as String,
                jsonObject["urlToImage"     ] as? String?,
                (jsonObject["publishedAt"   ] as? String?)?.toDate()
                )
        }
    }
}