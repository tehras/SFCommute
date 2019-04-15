package com.github.tehras.sfcommute.service.caltrain

import com.github.tehras.dagger.scopes.ApplicationScope
import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import dagger.Module
import dagger.Provides
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.moshi.MoshiConverterFactory
import retrofit2.converter.protobuf.ProtoConverterFactory
import timber.log.Timber
import java.util.concurrent.TimeUnit

@Module
object CaltrainServiceModule {
    @Provides
    @JvmStatic
    @ApplicationScope
    fun providesCaltrainService(retrofit: Retrofit): CaltrainService {
        return retrofit.create(CaltrainService::class.java)
    }

    @Provides
    @JvmStatic
    @ApplicationScope
    fun providesCaltrainServiceRetrofit(moshi: Moshi): Retrofit {
        return Retrofit
            .Builder()
            .baseUrl("https://transit.land")
            .client(OkHttpClient
                .Builder()
                .callTimeout(5, TimeUnit.SECONDS)
                .connectTimeout(5, TimeUnit.SECONDS)
                .readTimeout(5, TimeUnit.SECONDS)
                .writeTimeout(5, TimeUnit.SECONDS)
                .addInterceptor {
                    val request = it.request()
                    Timber.d("request url :: ${request.url()}")

                    val response = it.proceed(request)
                    Timber.d("response body :: ${response.peekBody(1024).string()}")

                    response
                }
                .build()
            )
            .addConverterFactory(MoshiConverterFactory.create(moshi))
            .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
            .build()
    }
}