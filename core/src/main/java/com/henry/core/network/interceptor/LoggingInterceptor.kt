package com.henry.core.network.interceptor

import android.util.Log
import com.henry.core.BuildConfig
import okhttp3.Interceptor
import okhttp3.Response
import okhttp3.logging.HttpLoggingInterceptor
import javax.inject.Inject

class LoggingInterceptor @Inject constructor() : Interceptor{

    private val loggingInterceptor = HttpLoggingInterceptor { message ->
        Log.d("NewsApi", message )
    }

    private fun httpLoggingInterceptor(): HttpLoggingInterceptor {
        return HttpLoggingInterceptor { message ->
            Log.d("NewsApi", message)
        }.apply {
            level = if(BuildConfig.ENABLE_LOGS)
                HttpLoggingInterceptor.Level.BODY
            else
                HttpLoggingInterceptor.Level.NONE
        }
    }


    override fun intercept(chain: Interceptor.Chain): Response {
        return  httpLoggingInterceptor().intercept(chain)
    }
}