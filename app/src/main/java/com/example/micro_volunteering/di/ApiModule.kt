package com.example.micro_volunteering.di

import android.content.Context
import android.content.SharedPreferences
import com.example.micro_volunteering.data.local.TokenManager
import com.example.micro_volunteering.data.constants.AuthConstants.AUTH_PREFERENCES
import com.example.micro_volunteering.data.mapper.TaskMapper
import com.example.micro_volunteering.data.mapper.UserMapper
import com.example.micro_volunteering.data.remote.api.VolunteeringApiService
import com.example.micro_volunteering.data.repository.VolunteeringRepositoryImpl
import com.example.micro_volunteering.domain.repository.VolunteeringRepository
import com.example.micro_volunteering.domain.usecase.AuthorizationUserUseCase
import com.example.micro_volunteering.domain.usecase.RegistrationUserUseCase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit

@Module()
@InstallIn(SingletonComponent::class)
object ApiModule {
    @Provides
    fun provideApi(retrofit: Retrofit) : VolunteeringApiService {
        return retrofit.create(VolunteeringApiService::class.java)
    }

    @Provides
    fun provideRepository(api: VolunteeringApiService, tokenManager: TokenManager) : VolunteeringRepository {
        return VolunteeringRepositoryImpl(api, tokenManager, UserMapper(), TaskMapper())
    }

    @Provides
    fun provideAuthorizationUseCase(repository: VolunteeringRepository) : AuthorizationUserUseCase {
        return AuthorizationUserUseCase(repository)
    }

    @Provides
    fun provideRegistrationUserUseCase(repository: VolunteeringRepository) : RegistrationUserUseCase {
        return RegistrationUserUseCase(repository)
    }

    @Provides
    fun provideTokenManager(sharedPreferences : SharedPreferences) : TokenManager {
        return TokenManager(sharedPreferences)
    }

    @Provides
    fun provideSharedPreferences(@ApplicationContext context: Context): SharedPreferences {
        return context.getSharedPreferences(AUTH_PREFERENCES, Context.MODE_PRIVATE)
    }
}