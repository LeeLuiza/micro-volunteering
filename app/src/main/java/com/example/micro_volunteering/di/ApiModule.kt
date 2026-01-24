package com.example.micro_volunteering.di

import android.content.Context
import android.content.SharedPreferences
import com.example.micro_volunteering.data.local.TokenManager
import com.example.micro_volunteering.data.constants.AuthConstants.AUTH_PREFERENCES
import com.example.micro_volunteering.data.local.UserPreferences
import com.example.micro_volunteering.data.mapper.FeedbackMapper
import com.example.micro_volunteering.data.mapper.NotificationMapper
import com.example.micro_volunteering.data.mapper.TaskMapper
import com.example.micro_volunteering.data.mapper.UserMapper
import com.example.micro_volunteering.data.remote.api.VolunteeringApiService
import com.example.micro_volunteering.data.repository.VolunteeringRepositoryImpl
import com.example.micro_volunteering.data.utils.UriConverter
import com.example.micro_volunteering.domain.repository.VolunteeringRepository
import com.example.micro_volunteering.domain.usecase.AuthorizationUserUseCase
import com.example.micro_volunteering.domain.usecase.CompleteTaskUseCase
import com.example.micro_volunteering.domain.usecase.CreateTaskUseCase
import com.example.micro_volunteering.domain.usecase.DeleteUserUseCase
import com.example.micro_volunteering.domain.usecase.GetFeedbacksUseCase
import com.example.micro_volunteering.domain.usecase.GetNotificationUseCase
import com.example.micro_volunteering.domain.usecase.GetUserRoleUseCase
import com.example.micro_volunteering.domain.usecase.GetVolunteerRespondUseCase
import com.example.micro_volunteering.domain.usecase.LeaveFeedbackUseCase
import com.example.micro_volunteering.domain.usecase.LogoutUseCase
import com.example.micro_volunteering.domain.usecase.RegistrationUserUseCase
import com.example.micro_volunteering.domain.usecase.UpdateTaskUseCase
import com.example.micro_volunteering.domain.usecase.UpdateUserInfoUseCase
import com.example.micro_volunteering.domain.usecase.UserInfoUseCase
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
    fun provideRepository(
        api: VolunteeringApiService, tokenManager: TokenManager, userPreferences: UserPreferences, uriConverter: UriConverter
    ) : VolunteeringRepository {
        return VolunteeringRepositoryImpl(
            api, tokenManager, userPreferences, UserMapper(), TaskMapper(), FeedbackMapper(),
            NotificationMapper(), uriConverter
        )
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
    fun provideCreateTaskUseCase(repository: VolunteeringRepository) : CreateTaskUseCase {
        return CreateTaskUseCase(repository)
    }

    @Provides
    fun provideGetUserRoleUseCase(repository: VolunteeringRepository) : GetUserRoleUseCase {
        return GetUserRoleUseCase(repository)
    }

    @Provides
    fun provideUpdateTaskUseCase(repository: VolunteeringRepository) : UpdateTaskUseCase {
        return UpdateTaskUseCase(repository)
    }

    @Provides
    fun provideUserInfoUseCase(repository: VolunteeringRepository) : UserInfoUseCase {
        return UserInfoUseCase(repository)
    }

    @Provides
    fun provideUpdateUserInfoUseCase(repository: VolunteeringRepository) : UpdateUserInfoUseCase {
        return UpdateUserInfoUseCase(repository)
    }

    @Provides
    fun provideDeleteUserUseCase(repository: VolunteeringRepository) : DeleteUserUseCase {
        return DeleteUserUseCase(repository)
    }

    @Provides
    fun provideLogoutUseCase(repository: VolunteeringRepository) : LogoutUseCase {
        return LogoutUseCase(repository)
    }

    @Provides
    fun provideGetFeedbackUseCase(repository: VolunteeringRepository) : GetFeedbacksUseCase {
        return GetFeedbacksUseCase(repository)
    }

    @Provides
    fun provideGetNotificationUseCase(repository: VolunteeringRepository) : GetNotificationUseCase {
        return GetNotificationUseCase(repository)
    }

    @Provides
    fun provideGetVolunteerRespondUseCase(repository: VolunteeringRepository) : GetVolunteerRespondUseCase {
        return GetVolunteerRespondUseCase(repository)
    }

    @Provides
    fun provideLeaveFeedbackUseCase(repository: VolunteeringRepository) : LeaveFeedbackUseCase {
        return LeaveFeedbackUseCase(repository)
    }

    @Provides
    fun provideCompleteTaskUseCase(repository: VolunteeringRepository) : CompleteTaskUseCase {
        return CompleteTaskUseCase(repository)
    }

    @Provides
    fun provideUriConverter(@ApplicationContext context: Context) : UriConverter {
        return UriConverter(context)
    }

    @Provides
    fun provideTokenManager(sharedPreferences : SharedPreferences) : TokenManager {
        return TokenManager(sharedPreferences)
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