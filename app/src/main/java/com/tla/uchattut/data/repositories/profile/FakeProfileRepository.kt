package com.tla.uchattut.data.repositories.profile

import com.tla.uchattut.data.repositories.profile.models.ProfileRepoModel
import com.tla.uchattut.di.myprofile.MyProfileScope
import com.tla.uchattut.domain.profile.ProfileRepository
import javax.inject.Inject

@MyProfileScope
class FakeProfileRepository @Inject constructor() : ProfileRepository {
    override fun getProfile(): ProfileRepoModel = ProfileRepoModel(
        id = 0,
        photoUrl = "https://us.123rf.com/450wm/gmast3r/gmast3r1710/gmast3r171002113/88668352-male-avatar-profile-vector-illustration.jpg?ver=6",
        name = "Дмитрий Кавычкин",
        phone = "+7(985)523-23-45",
        address = "ул. Шмякина, д.23"
    )
}