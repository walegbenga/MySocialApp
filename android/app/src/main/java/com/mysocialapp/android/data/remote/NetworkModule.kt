package com.mysocialapp.android.data.remote

import com.mysocialapp.android.BuildConfig
import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import okhttp3.Interceptor
import okhttp3.OkHttpClient

object NetworkModule {
    private val moshi = Moshi.Builder().addLast(KotlinJsonAdapterFactory()).build()
    fun api(tokenProvider: () -> String?): ApiService = Retrofit.Builder().baseUrl(BuildConfig.API_BASE_URL).client(OkHttpClient.Builder().addInterceptor(Interceptor { chain -> chain.proceed(chain.request().newBuilder().apply { tokenProvider()?.let { header("Authorization", "Bearer $it") } }.build()) }).build()).addConverterFactory(MoshiConverterFactory.create(moshi)).build().create(ApiService::class.java)
}
