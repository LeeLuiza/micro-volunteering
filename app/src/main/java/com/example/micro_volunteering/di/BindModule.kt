package com.example.micro_volunteering.di

import com.example.micro_volunteering.data.repository.VolunteeringRepositoryImpl
import com.example.micro_volunteering.domain.repository.VolunteeringRepository
import com.example.micro_volunteering.presentation.utils.ResourceProvider
import com.example.micro_volunteering.presentation.utils.ResourceProviderImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module()
@InstallIn(SingletonComponent::class)
abstract class BindModule {

    @Binds
    abstract fun bindRepository(impl: VolunteeringRepositoryImpl) : VolunteeringRepository

    @Binds
    abstract fun bindResourceProvider(impl: ResourceProviderImpl): ResourceProvider
}