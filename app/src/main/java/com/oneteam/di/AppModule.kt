package com.oneteam.di

import android.content.Context
import com.oneteam.common.Constant
import com.oneteam.core.dispatchers.AppDispatcher
import com.oneteam.core.dispatchers.Dispatcher
import com.oneteam.core.util.NetworkHelper
import com.oneteam.core.util.NetworkHelperImpl
import com.oneteam.data.remote.service.ImageService
import com.oneteam.data.repository.ImageRepositoryImpl
import com.oneteam.domain.repository.ImageRepository
import com.oneteam.domain.use_case.GetImagesUseCase
import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Provides
    @Singleton
    fun provideOkHttpClient(): OkHttpClient {
        return OkHttpClient.Builder()
            .addInterceptor { chain ->
                val request = chain.request().newBuilder()
                    .addHeader("Authorization", Constant.API_KEY)
                    .build()
                chain.proceed(request)
            }
            .build()
    }

    @Provides
    @Singleton
    fun provideMoshi(): Moshi {
        return Moshi.Builder()
            .add(KotlinJsonAdapterFactory())
            .build()
    }

    @Provides
    @Singleton
    fun providesNetworkService(okHttpClient: OkHttpClient, moshi: Moshi): ImageService {
        return Retrofit.Builder()
            .baseUrl(Constant.BASE_URL)
            .client(okHttpClient)
            .addConverterFactory(MoshiConverterFactory.create(moshi))
            .build()
            .create(ImageService::class.java)
    }

    @Provides
    @Singleton
    fun provideAppDispatcher(): Dispatcher {
        return AppDispatcher()
    }

    @Provides
    fun provideNetworkHelper(@ApplicationContext context: Context): NetworkHelper {
        return NetworkHelperImpl(context)
    }


    @Provides
    fun provideImageRepository(
        imageService: ImageService
    ): ImageRepository {
        return ImageRepositoryImpl(imageService)
    }

    @Provides
    fun provideGetImagesUseCase(repository: ImageRepository): GetImagesUseCase {
        return GetImagesUseCase(repository)
    }

}