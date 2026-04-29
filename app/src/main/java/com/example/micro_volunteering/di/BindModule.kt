package com.example.micro_volunteering.di

import com.example.micro_volunteering.data.repository.VolunteeringRepositoryImpl
import com.example.micro_volunteering.domain.repository.VolunteeringRepository
import com.example.micro_volunteering.domain.usecase.AcceptVolunteerUseCase
import com.example.micro_volunteering.domain.usecase.AuthorizationUserUseCase
import com.example.micro_volunteering.domain.usecase.CheckOrganizationVerifiedUseCase
import com.example.micro_volunteering.domain.usecase.CheckUserLoggedInUseCase
import com.example.micro_volunteering.domain.usecase.CompleteTaskUseCase
import com.example.micro_volunteering.domain.usecase.CreateTaskUseCase
import com.example.micro_volunteering.domain.usecase.CreateTokenUseCase
import com.example.micro_volunteering.domain.usecase.DeleteTaskUseCase
import com.example.micro_volunteering.domain.usecase.DeleteUserUseCase
import com.example.micro_volunteering.domain.usecase.GetFeedbacksUseCase
import com.example.micro_volunteering.domain.usecase.GetNotificationsUseCase
import com.example.micro_volunteering.domain.usecase.GetTaskDetailsUseCase
import com.example.micro_volunteering.domain.usecase.GetTaskRespondersUseCase
import com.example.micro_volunteering.domain.usecase.GetTasksOrganizationUseCase
import com.example.micro_volunteering.domain.usecase.GetTasksUseCase
import com.example.micro_volunteering.domain.usecase.GetUnverifiedOrganizationUseCase
import com.example.micro_volunteering.domain.usecase.GetUnverifiedOrganizationsUseCase
import com.example.micro_volunteering.domain.usecase.GetUserInfoUseCase
import com.example.micro_volunteering.domain.usecase.GetUserRoleUseCase
import com.example.micro_volunteering.domain.usecase.LeaveFeedbackUseCase
import com.example.micro_volunteering.domain.usecase.LogoutUseCase
import com.example.micro_volunteering.domain.usecase.NotificationPermissionRequestedUseCase
import com.example.micro_volunteering.domain.usecase.RegistrationUserUseCase
import com.example.micro_volunteering.domain.usecase.RejectOrganizationUseCase
import com.example.micro_volunteering.domain.usecase.RejectVolunteerUseCase
import com.example.micro_volunteering.domain.usecase.ResendCodeUseCase
import com.example.micro_volunteering.domain.usecase.RespondToTaskUseCase
import com.example.micro_volunteering.domain.usecase.SetNotificationPermissionRequestedUseCase
import com.example.micro_volunteering.domain.usecase.UpdateTaskUseCase
import com.example.micro_volunteering.domain.usecase.UpdateUserInfoUseCase
import com.example.micro_volunteering.domain.usecase.UploadAvatarUseCase
import com.example.micro_volunteering.domain.usecase.UploadDocumentUseCase
import com.example.micro_volunteering.domain.usecase.VerifyEmailUseCase
import com.example.micro_volunteering.domain.usecase.VerifyOrganizationUseCase
import com.example.micro_volunteering.domain.usecase.impl.AcceptVolunteerUseCaseImpl
import com.example.micro_volunteering.domain.usecase.impl.AuthorizationUserUseCaseImpl
import com.example.micro_volunteering.domain.usecase.impl.CheckOrganizationVerifiedUseCaseImpl
import com.example.micro_volunteering.domain.usecase.impl.CheckUserLoggedInUseCaseImpl
import com.example.micro_volunteering.domain.usecase.impl.CompleteTaskUseCaseImpl
import com.example.micro_volunteering.domain.usecase.impl.CreateTaskUseCaseImpl
import com.example.micro_volunteering.domain.usecase.impl.CreateTokenUseCaseImpl
import com.example.micro_volunteering.domain.usecase.impl.DeleteTaskUseCaseImpl
import com.example.micro_volunteering.domain.usecase.impl.DeleteUserUseCaseImpl
import com.example.micro_volunteering.domain.usecase.impl.GetFeedbacksUseCaseImpl
import com.example.micro_volunteering.domain.usecase.impl.GetNotificationsUseCaseImpl
import com.example.micro_volunteering.domain.usecase.impl.GetTaskDetailsUseCaseImpl
import com.example.micro_volunteering.domain.usecase.impl.GetTaskRespondersUseCaseImpl
import com.example.micro_volunteering.domain.usecase.impl.GetTasksOrganizationUseCaseImpl
import com.example.micro_volunteering.domain.usecase.impl.GetTasksUseCaseImpl
import com.example.micro_volunteering.domain.usecase.impl.GetUnverifiedOrganizationUseCaseImpl
import com.example.micro_volunteering.domain.usecase.impl.GetUnverifiedOrganizationsUseCaseImpl
import com.example.micro_volunteering.domain.usecase.impl.GetUserInfoUseCaseImpl
import com.example.micro_volunteering.domain.usecase.impl.GetUserRoleUseCaseImpl
import com.example.micro_volunteering.domain.usecase.impl.LeaveFeedbackUseCaseImpl
import com.example.micro_volunteering.domain.usecase.impl.LogoutUseCaseImpl
import com.example.micro_volunteering.domain.usecase.impl.NotificationPermissionRequestedUseCaseImpl
import com.example.micro_volunteering.domain.usecase.impl.RegistrationUserUseCaseImpl
import com.example.micro_volunteering.domain.usecase.impl.RejectOrganizationUseCaseImpl
import com.example.micro_volunteering.domain.usecase.impl.RejectVolunteerUseCaseImpl
import com.example.micro_volunteering.domain.usecase.impl.ResendCodeUseCaseImpl
import com.example.micro_volunteering.domain.usecase.impl.RespondToTaskUseCaseImpl
import com.example.micro_volunteering.domain.usecase.impl.SetNotificationPermissionRequestedUseCaseImpl
import com.example.micro_volunteering.domain.usecase.impl.UpdateTaskUseCaseImpl
import com.example.micro_volunteering.domain.usecase.impl.UpdateUserInfoUseCaseImpl
import com.example.micro_volunteering.domain.usecase.impl.UploadAvatarUseCaseImpl
import com.example.micro_volunteering.domain.usecase.impl.UploadDocumentUseCaseImpl
import com.example.micro_volunteering.domain.usecase.impl.VerifyEmailUseCaseImpl
import com.example.micro_volunteering.domain.usecase.impl.VerifyOrganizationUseCaseImpl
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

    @Binds
    abstract fun bindAuthorizationUserUseCase(impl: AuthorizationUserUseCaseImpl): AuthorizationUserUseCase

    @Binds
    abstract fun bindRegistrationUserUseCase(impl: RegistrationUserUseCaseImpl): RegistrationUserUseCase

    @Binds
    abstract fun bindVerifyEmailUseCase(impl: VerifyEmailUseCaseImpl): VerifyEmailUseCase

    @Binds
    abstract fun bindResendCodeUseCase(impl: ResendCodeUseCaseImpl): ResendCodeUseCase

    @Binds
    abstract fun bindLogoutUseCase(impl: LogoutUseCaseImpl): LogoutUseCase

    @Binds
    abstract fun bindDeleteUserUseCase(impl: DeleteUserUseCaseImpl): DeleteUserUseCase

    @Binds
    abstract fun bindGetUserRoleUseCase(impl: GetUserRoleUseCaseImpl): GetUserRoleUseCase

    @Binds
    abstract fun bindUserLoggedUseCase(impl: CheckUserLoggedInUseCaseImpl): CheckUserLoggedInUseCase

    @Binds
    abstract fun bindUserInfoUseCase(impl: GetUserInfoUseCaseImpl): GetUserInfoUseCase

    @Binds
    abstract fun bindUpdateUserInfoUseCase(impl: UpdateUserInfoUseCaseImpl): UpdateUserInfoUseCase

    @Binds
    abstract fun bindTaskListUseCase(impl: GetTasksUseCaseImpl): GetTasksUseCase

    @Binds
    abstract fun bindTaskListOrganizationUseCase(impl: GetTasksOrganizationUseCaseImpl): GetTasksOrganizationUseCase

    @Binds
    abstract fun bindTaskUseCase(impl: GetTaskDetailsUseCaseImpl): GetTaskDetailsUseCase

    @Binds
    abstract fun bindCreateTaskUseCase(impl: CreateTaskUseCaseImpl): CreateTaskUseCase

    @Binds
    abstract fun bindUpdateTaskUseCase(impl: UpdateTaskUseCaseImpl): UpdateTaskUseCase

    @Binds
    abstract fun bindDeleteTaskUseCase(impl: DeleteTaskUseCaseImpl): DeleteTaskUseCase

    @Binds
    abstract fun bindCompleteTaskUseCase(impl: CompleteTaskUseCaseImpl): CompleteTaskUseCase

    @Binds
    abstract fun bindRespondUseCase(impl: RespondToTaskUseCaseImpl): RespondToTaskUseCase

    @Binds
    abstract fun bindGetTaskRespondersUseCase(impl: GetTaskRespondersUseCaseImpl): GetTaskRespondersUseCase

    @Binds
    abstract fun bindLeaveFeedbackUseCase(impl: LeaveFeedbackUseCaseImpl): LeaveFeedbackUseCase

    @Binds
    abstract fun bindGetFeedbacksUseCase(impl: GetFeedbacksUseCaseImpl): GetFeedbacksUseCase

    @Binds
    abstract fun bindAcceptVolunteerUseCase(impl: AcceptVolunteerUseCaseImpl): AcceptVolunteerUseCase

    @Binds
    abstract fun bindRejectVolunteerUseCase(impl: RejectVolunteerUseCaseImpl): RejectVolunteerUseCase

    @Binds
    abstract fun bindUnverifiedOrganizationListUseCase(impl: GetUnverifiedOrganizationsUseCaseImpl): GetUnverifiedOrganizationsUseCase

    @Binds
    abstract fun bindUnverifiedOrganizationUseCase(impl: GetUnverifiedOrganizationUseCaseImpl): GetUnverifiedOrganizationUseCase

    @Binds
    abstract fun bindVerifyOrganizationUseCase(impl: VerifyOrganizationUseCaseImpl): VerifyOrganizationUseCase

    @Binds
    abstract fun bindRejectOrganizationUseCase(impl: RejectOrganizationUseCaseImpl): RejectOrganizationUseCase

    @Binds
    abstract fun bindCheckOrganizationVerifiedUseCase(impl: CheckOrganizationVerifiedUseCaseImpl): CheckOrganizationVerifiedUseCase

    @Binds
    abstract fun bindUploadAvatarUseCase(impl: UploadAvatarUseCaseImpl): UploadAvatarUseCase

    @Binds
    abstract fun bindUploadDocumentUseCase(impl: UploadDocumentUseCaseImpl): UploadDocumentUseCase

    @Binds
    abstract fun bindUpdateTokenUseCase(impl: CreateTokenUseCaseImpl): CreateTokenUseCase

    @Binds
    abstract fun bindGetNotificationUseCase(impl: GetNotificationsUseCaseImpl): GetNotificationsUseCase

    @Binds
    abstract fun bindNotificationPermissionRequestedUseCase(impl: NotificationPermissionRequestedUseCaseImpl): NotificationPermissionRequestedUseCase

    @Binds
    abstract fun bindSetNotificationPermissionRequestedUseCase(impl: SetNotificationPermissionRequestedUseCaseImpl): SetNotificationPermissionRequestedUseCase
}