package com.app.rxretrofit.apiservices

import com.app.rxretrofit.BuildConfig
import com.app.rxretrofit.apiservices.RxJavaObserveOnMainThread.ObserveOnMainCallAdapterFactory
import io.reactivex.Scheduler
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.CallAdapter
import retrofit2.Converter
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

object RestClient {
    var currentRetrofit: Retrofit? = null
        private set

    @Volatile
    private var sServiceInstance: RestClientInterface? = null


    operator fun get(retrofit: Retrofit?): RestClientInterface? {
        currentRetrofit = retrofit
        if (sServiceInstance == null) {
            synchronized(RestClient::class.java) {
                if (sServiceInstance == null) {
                    sServiceInstance =
                        provideApiService(retrofit)
                }
            }
        }
        return sServiceInstance
    }

    fun get(): RestClientInterface? {
        if (sServiceInstance == null) {
            synchronized(RestClient::class.java) {
                if (sServiceInstance == null) {
                    currentRetrofit = provideRetrofit(
                        BuildConfig.BASE_URL,
                        provideGsonConverterFactory(),
                        provideRxJavaIOAdapterFactory(Schedulers.io()),
                        provideRxJavaMainAdapterFactory(AndroidSchedulers.mainThread()),
                        provideHttpClient()
                    )
                    sServiceInstance =
                        provideApiService(currentRetrofit)
                }
            }
        }
        return sServiceInstance
    }

    fun provideHttpClient(): OkHttpClient {
        val httpClient = OkHttpClient().newBuilder()
        val logging = HttpLoggingInterceptor()
        logging.level = HttpLoggingInterceptor.Level.BODY
        httpClient.addInterceptor(logging) //Check other request params
        httpClient.addNetworkInterceptor(logging) //Check headers
        httpClient.connectTimeout(30, TimeUnit.SECONDS)
        httpClient.writeTimeout(30, TimeUnit.SECONDS)
        httpClient.readTimeout(30, TimeUnit.SECONDS)
        /*httpClient.addInterceptor(Interceptor { chain ->
            val request = chain.request()
            try {
                val response = chain.proceed(request)
                val body = response.body()
                val bodyString = body!!.string()
                val contentType = body.contentType()
                return@Interceptor response.newBuilder()
                    .body(ResponseBody.create(contentType, bodyString))
                    .build()
            } catch (e: Exception) {
                e.printStackTrace()
            }
            null
        })*/
        return httpClient.build()
    }


    private fun provideGsonConverterFactory(): Converter.Factory {
        return GsonConverterFactory.create()
    }

    private fun provideRxJavaIOAdapterFactory(scheduler: Scheduler?): CallAdapter.Factory {
        return RxJava2CallAdapterFactory.createWithScheduler(scheduler)
    }

    private fun provideRxJavaMainAdapterFactory(scheduler: Scheduler?): CallAdapter.Factory {
        return ObserveOnMainCallAdapterFactory(scheduler!!)
    }

    private fun provideRetrofit(
        baseUrl: String?, converterFactory: Converter.Factory?,
        ioCallAdapterFactory: CallAdapter.Factory?,
        mainCallAdapterFactory: CallAdapter.Factory?,
        client: OkHttpClient?
    ): Retrofit {
        return Retrofit.Builder().baseUrl(baseUrl)
            .client(client)
            .addConverterFactory(converterFactory)
            .addCallAdapterFactory(mainCallAdapterFactory)
            .addCallAdapterFactory(ioCallAdapterFactory)
            .build()
    }

    private fun provideApiService(retrofit: Retrofit?): RestClientInterface {
        return retrofit!!.create(RestClientInterface::class.java)
    }

    operator fun <T> get(clazz: Class<T>?): T {
        return currentRetrofit!!.create(clazz)
    }
}