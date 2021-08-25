package com.example.mimoapp.roomDB

import android.content.Context
import androidx.room.Room
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

/*
This annotation tells Hilt that the dependencies provided via this
module shall stay alive as long as application is running.*/
@InstallIn(SingletonComponent::class)

/*This annotation tells Hilt
that this class contributes to dependency injection object graph.*/
@Module
class DataBaseHiltModule {

    @Provides
    @Singleton
    fun provideAppDatabase(@ApplicationContext appContext: Context): LessonsDatabase {
        return Room.databaseBuilder(
            appContext,
            LessonsDatabase::class.java,
            "lessonsDB"
        ) .fallbackToDestructiveMigration()//ensures when u upgrade db it will not crash
            .build()

        /*  @Singleton annotation tells Hilt that AppDatabase should be initialized only once. And the same
                  instance should be provided every time it’s needed to be injected.
          @ApplicationContext allows hilt to provide application context without having to explicitly specify how to obtain it.*/
    }


}