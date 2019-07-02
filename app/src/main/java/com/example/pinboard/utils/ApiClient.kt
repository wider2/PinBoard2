package com.example.pinboard.utils

import com.example.pinboard.BuildConfig
import com.example.pinboard.model.PinModel
import io.reactivex.Observable
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET

interface ApiClient {

    // Get list of pins as Observable
    @GET("raw/wgkJgazE")
    fun getPins(): Observable<List<PinModel>>


    companion object {

        fun create(): ApiClient {
            val loggingInterceptor = HttpLoggingInterceptor()
            if (BuildConfig.DEBUG) {
                loggingInterceptor.level = HttpLoggingInterceptor.Level.BODY
            } else {
                loggingInterceptor.level = HttpLoggingInterceptor.Level.NONE
            }

            val okHttpClient = OkHttpClient().newBuilder()
                    .addInterceptor(loggingInterceptor)
                    .build()

            val retrofit = Retrofit.Builder()
                    .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                    .addConverterFactory(GsonConverterFactory.create())
                    .client(okHttpClient)
                    .baseUrl("http://pastebin.com/")
                //.baseUrl("http://pastebin.com/raw/wgkJgazE")
                    .build()

            return retrofit.create(ApiClient::class.java)
        }
    }

}
