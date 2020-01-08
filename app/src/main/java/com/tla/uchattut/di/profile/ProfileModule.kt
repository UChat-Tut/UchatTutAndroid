package com.tla.uchattut.di.profile

import com.tla.uchattut.data.repositories.profile.FakeProfileRepository
import com.tla.uchattut.domain.profile.ProfileInteractor
import com.tla.uchattut.domain.profile.ProfileRepository
import com.tla.uchattut.presentation._common.viewModel
import com.tla.uchattut.presentation.profile.view.ProfileFragment
import com.tla.uchattut.presentation.profile.view_model.ProfileViewModel
import dagger.Binds
import dagger.Module
import dagger.Provides

@Module(includes = [ProfileModule.ProfileAbstractModule::class])
class ProfileModule(
    val fragment: ProfileFragment
) {

    @Provides
    fun provideViewModel(interactor: ProfileInteractor): ProfileViewModel =
        fragment.viewModel { ProfileViewModel(interactor) }

    @Module
    interface ProfileAbstractModule {
        @Binds
        @ProfileScope
        fun bindProfileRepository(repository: FakeProfileRepository): ProfileRepository
    }
}