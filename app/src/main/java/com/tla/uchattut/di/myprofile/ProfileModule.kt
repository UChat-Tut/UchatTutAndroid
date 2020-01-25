package com.tla.uchattut.di.myprofile

import com.tla.uchattut.data.repositories.profile.FakeProfileRepository
import com.tla.uchattut.domain.profile.ProfileInteractor
import com.tla.uchattut.domain.profile.ProfileRepository
import com.tla.uchattut.presentation._common.BaseFragment
import com.tla.uchattut.presentation._common.viewModel
import com.tla.uchattut.presentation.profile.view_model.MyProfileViewModel
import dagger.Binds
import dagger.Module
import dagger.Provides

@Module(includes = [ProfileModule.ProfileAbstractModule::class])
class ProfileModule(
    val fragment: BaseFragment
) {

    @Provides
    fun provideViewModel(interactor: ProfileInteractor): MyProfileViewModel =
        fragment.viewModel { MyProfileViewModel(interactor) }

    @Module
    interface ProfileAbstractModule {
        @Binds
        @MyProfileScope
        fun bindProfileRepository(repository: FakeProfileRepository): ProfileRepository
    }
}