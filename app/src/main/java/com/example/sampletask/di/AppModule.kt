package com.example.sampletask.di

import android.content.Context
import androidx.room.Room
import com.example.sampletask.database.QuestionDatabase
import com.example.sampletask.database.QuestionsDao
import com.example.sampletask.network.ApiInterface
import com.example.sampletask.utils.Constants.BASE_URL
import com.example.sampletask.utils.Constants.DATABASE_NAME
import com.readystatesoftware.chuck.ChuckInterceptor
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Provides
    @Singleton
    fun provideHttpClient(@ApplicationContext context: Context): OkHttpClient =
        OkHttpClient.Builder()
            .addInterceptor(ChuckInterceptor(context))
            .build()

    @Provides
    @Singleton
    fun provideRetrofit(okHttpClient: OkHttpClient): Retrofit =
        Retrofit.Builder()
            .client(okHttpClient)
            .addConverterFactory(GsonConverterFactory.create())
            .baseUrl(BASE_URL)
            .build()

    @Provides
    @Singleton
    fun provideApiService(retrofit: Retrofit): ApiInterface =
        retrofit.create(ApiInterface::class.java)

    @Provides
    @Singleton
    fun provideDatabase(@ApplicationContext context: Context): QuestionDatabase =
        Room.databaseBuilder(context, QuestionDatabase::class.java, DATABASE_NAME).build()

    @Provides
    @Singleton
    fun provideDao(jobDatabase: QuestionDatabase): QuestionsDao = jobDatabase.getDao()

}