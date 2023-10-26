package crls.finance.coinvalue.network

import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory


object RetrofitClient {

    private val BASE_URL = "https://economia.awesomeapi.com.br/last/"
    private lateinit var INSTANCE : Retrofit
    private val moshi = Moshi.Builder().add(KotlinJsonAdapterFactory()).build()


    private fun getRetrofitInstance(): Retrofit {
        val http = OkHttpClient.Builder()
        if(!::INSTANCE.isInitialized){
            INSTANCE = Retrofit.Builder()
                .baseUrl(BASE_URL)
                .client(http.build())
                .addConverterFactory(MoshiConverterFactory.create(moshi))
                .build()
        }
        return INSTANCE
    }
    fun <S> createService(service:Class<S>): S {
        return getRetrofitInstance().create(service)
    }

}