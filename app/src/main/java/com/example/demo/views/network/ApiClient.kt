package com.example.demo.views.network

import android.content.Context
import com.example.demo.BuildConfig
import com.example.demo.views.utils.CommonUtils
import com.squareup.moshi.JsonAdapter
import com.squareup.moshi.JsonReader
import com.squareup.moshi.JsonWriter
import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import okhttp3.Cache
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.Response
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import java.io.File
import java.io.IOException
import java.text.SimpleDateFormat
import java.util.*

class ApiClient {
    companion object {

        @Volatile
        private var retrofit: Retrofit? = null

        @Volatile
        private var apiInterface: ApiInterface? = null

        private fun buildClient(): OkHttpClient {
            return OkHttpClient.Builder()
                .addInterceptor(HttpLoggingInterceptor().apply {
                    this.level =
                        if (BuildConfig.DEBUG) HttpLoggingInterceptor.Level.BODY else HttpLoggingInterceptor.Level.NONE
                })
                .addInterceptor { chain ->
                    val request = chain.request().newBuilder()
                        .addHeader("Accept", "application/json")
                        .build()

                    chain.proceed(request)
                }
                .build()
        }

        private fun buildCacheClient(context: Context): OkHttpClient {
            val httpCacheDirectory = File(context.cacheDir, "network-cache")
            val cacheSize: Long = 10 * 1024 * 1024 // 10mb

            val cache = Cache(httpCacheDirectory, cacheSize)

            return OkHttpClient.Builder()
                .addInterceptor(HttpLoggingInterceptor().apply {
                    this.level =
                        if (BuildConfig.DEBUG) HttpLoggingInterceptor.Level.BODY else HttpLoggingInterceptor.Level.NONE
                })
                .addInterceptor { chain ->
                    val request = chain.request().newBuilder()
                        .addHeader("Accept", "application/json")
                        .build()
                    chain.proceed(request)
                }
                .addInterceptor(CacheInterceptor(context))
                .addNetworkInterceptor(CacheInterceptor(context))
                .cache(cache)
                .build()
        }

        class CacheInterceptor(private val context: Context) : Interceptor {
            @Throws(IOException::class)
            override fun intercept(chain: Interceptor.Chain): Response {
                val response: Response = chain.proceed(chain.request())

                return if (CommonUtils.isConnectedToInternet(context)) {
                    val maxAge = 1
                    response.newBuilder()
                        .removeHeader("Pragma")
                        .header("Cache-Control", "public, max-age=$maxAge")
                        .build()
                } else {
                    val maxStale = 60 * 60 * 24 * 28 // tolerate 28 days stale
                    response.newBuilder()
                        .removeHeader("Pragma")
                        .header("Cache-Control", "public, only-if-cached, max-stale=$maxStale")
                        .build()
                }
            }
        }

        @Synchronized
        private fun getRetrofit(): Retrofit {
            return retrofit ?: synchronized(this) {

                val moshi = Moshi.Builder()
                    // Note: To automatically convert date string to Date object use this:
                    .add(Date::class.java, DateJsonAdapter())
                    .add(KotlinJsonAdapterFactory())
                    .build()

                retrofit ?: Retrofit.Builder()
                    .client(buildClient())
                    .addConverterFactory(MoshiConverterFactory.create(moshi))
                    .baseUrl(BuildConfig.BASE_URL)
                    .build()
            }
        }

        @Synchronized
        private fun getCachedRetrofit(context: Context): Retrofit {
            return retrofit ?: synchronized(this) {

                val moshi = Moshi.Builder()
                    .add(Date::class.java, DateJsonAdapter())
                    .add(KotlinJsonAdapterFactory())
                    .build()

                retrofit ?: Retrofit.Builder()
                    .client(buildCacheClient(context))
                    .addConverterFactory(MoshiConverterFactory.create(moshi))
                    .baseUrl(BuildConfig.BASE_URL)
                    .build()
            }
        }

        @Synchronized
        fun getCacheClient(context: Context): ApiInterface {

            return apiInterface ?: synchronized(this) {
                getCachedRetrofit(context).create(ApiInterface::class.java)
            }
        }

        @Synchronized
        fun getClient(): ApiInterface {

            return apiInterface ?: synchronized(this) {
                getRetrofit().create(ApiInterface::class.java)
            }
        }
    }

    class DateJsonAdapter : JsonAdapter<Date>() {

        private val dateFormat = SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.getDefault())

        override fun fromJson(reader: JsonReader): Date? {
            return try {
                val dateAsString = reader.nextString()
                dateFormat.parse(dateAsString)
            } catch (e: Exception) {
                e.printStackTrace()
                null
            }
        }

        override fun toJson(writer: JsonWriter, value: Date?) {
            if (value != null) {
                writer.value(dateFormat.format(value))
            }
        }
    }
}