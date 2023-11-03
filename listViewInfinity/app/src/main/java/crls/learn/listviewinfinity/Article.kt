package crls.learn.listviewinfinity

import java.util.Date

data class Article (
    val title        : String?,
    val description  : String?,
    val url          : String,
    val urlToImage   : String?,
    val publishedAt  : Date
)