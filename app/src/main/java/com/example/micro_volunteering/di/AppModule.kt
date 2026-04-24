package com.example.micro_volunteering.di

import android.content.Context
import android.content.SharedPreferences
import com.example.micro_volunteering.data.local.TokenPreferences
import com.example.micro_volunteering.data.constants.AppConstants.AUTH_PREFERENCES
import com.example.micro_volunteering.data.local.UserPreferences
import com.example.micro_volunteering.data.remote.api.VolunteeringApiService
import com.example.micro_volunteering.data.utils.UriConverter
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit

@Module()
@InstallIn(SingletonComponent::class)
object AppModule {
    @Provides
    fun provideApi(retrofit: Retrofit) : VolunteeringApiService {
        return retrofit.create(VolunteeringApiService::class.java)
    }

    @Provides
    fun provideUriConverter(@ApplicationContext context: Context) : UriConverter {
        return UriConverter(context)
    }

    @Provides
    fun provideTokenManager(sharedPreferences : SharedPreferences) : TokenPreferences {
        return TokenPreferences(sharedPreferences)
    }

    @Provides
    fun provideUserPreferences(sharedPreferences : SharedPreferences) : UserPreferences {
        return UserPreferences(sharedPreferences)
    }

    @Provides
    fun provideSharedPreferences(@ApplicationContext context: Context): SharedPreferences {
        return context.getSharedPreferences(AUTH_PREFERENCES, Context.MODE_PRIVATE)
    }
}