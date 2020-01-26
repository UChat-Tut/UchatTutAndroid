package com.tla.uchattut.di.myprofile

import androidx.lifecycle.ViewModelProvider
import com.tla.uchattut.data.repositories.profile.FakeProfileRepository
import com.tla.uchattut.domain.profile.ProfileInteractor
import com.tla.uchattut.domain.profile.ProfileRepository
import com.tla.uchattut.presentation._common.BaseFragment
import com.tla.uchattut.presentation._common.factory
import com.tla.uchattut.presentation.profile.myprofile.MyProfileViewModel
import dagger.Binds
import dagger.Module
import dagger.Provides

@Module(includes = [MyProfileModule.ProfileAbstractModule::class])
class MyProfileModule {

    @Provides
    @MyProfileScope
    fun provideViewModelFactory(profileInteractor: ProfileInteractor): ViewModelProvider.Factory =
        factory { MyProfileViewModel(profileInteractor) }

    @Module
    interface ProfileAbstractModule {
        @Binds
        @MyProfileScope
        fun bindProfileRepository(repository: FakeProfileRepository): ProfileRepository
    }
}