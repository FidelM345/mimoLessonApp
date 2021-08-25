package com.example.mimoapp.api

import com.example.mimoapp.util.Constants
import com.jakewharton.espresso.OkHttp3IdlingResource
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.FragmentComponent
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory


//used for testing the responses from the api
val client = OkHttpClient()
val resource = OkHttp3IdlingResource.create("OkHttp", client);

//this module is used to provide create and later provide the instance of the MimoLessonsAPI
//to the repository class.
@Module
@InstallIn(FragmentComponent::class)
class MimoLessonsAPIHiltModule {

    @Provides
    fun retrofit(): Retrofit = Retrofit.Builder()
        .baseUrl(Constants.BASE_URL)
        .client(client)
        .addConverterFactory(GsonConverterFactory.create())
        .build()

    @Provides
    fun mimoLessonsAPI(retrofit: Retrofit): MimoLessonsAPI {
        return retrofit.create(MimoLessonsAPI::class.java)
    }


}

