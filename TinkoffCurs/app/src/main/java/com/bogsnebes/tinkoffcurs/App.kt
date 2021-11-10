package com.bogsnebes.tinkoffcurs

import android.app.Application
import android.content.Context
import com.bogsnebes.tinkoffcurs.data.remote.messages.MessagesApi
import com.bogsnebes.tinkoffcurs.data.remote.streams.StreamsApi
import com.bogsnebes.tinkoffcurs.data.remote.users.UsersApi
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory


class App : Application() {
    private val httpLoggingInterceptor =
        HttpLoggingInterceptor().apply { level = HttpLoggingInterceptor.Level.BODY }

    private val okHttpClient = OkHttpClient.Builder().addInterceptor(httpLoggingInterceptor).build()

    lateinit var streamsApi: StreamsApi
    lateinit var usersApi: UsersApi
    lateinit var messagesApi: MessagesApi

    override fun onCreate() {
        super.onCreate()
        application = this

        retrofit =
            Retrofit.Builder()
                .baseUrl("https://tinkoff-android-fall21.zulipchat.com/api/v1/")
                .client(okHttpClient)
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .build()

        streamsApi = retrofit.create(StreamsApi::class.java)
        usersApi = retrofit.create(UsersApi::class.java)
        messagesApi = retrofit.create(MessagesApi::class.java)
    }

    companion object {
        var application: Application? = null
            private set
        val context: Context
            get() = application!!.applicationContext

        lateinit var retrofit: Retrofit

        const val API_KEY =
            "Basic Ym9nZGFuLmJvZ290b3YxMjMxMjNAZ21haWwuY29tOnh5a0tkUmNGYlI2c3RSZzRuUWV3aFd3QTZYM01UNFhU"
    }
}