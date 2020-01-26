package com.tla.uchattut.data.repositories.dialogues

import com.tla.uchattut.data.network.ResultWrapper
import com.tla.uchattut.data.repositories.dialogues.models.DialogueRepoModel
import com.tla.uchattut.di.dialogues.DialoguesScope
import com.tla.uchattut.domain.dialogues.DialoguesRepository
import javax.inject.Inject

@DialoguesScope
class FakeDialoguesRepository @Inject constructor() : DialoguesRepository {

    override suspend fun getChatList(): ResultWrapper<List<DialogueRepoModel>> {
        Thread.sleep(2000)

        return ResultWrapper.Success(
            listOf(
                DialogueRepoModel(
                    id = 0,
                    name = "Lorem ipsum dolor sit amet, hendrerit eu tortor massa arcu in orci, congue ut, mauris purus vitae, tincidunt malesuada enim quam. Quis mi neque sed nec sagittis mauris, libero massa, nam ante, lacus ipsum arcu nulla phasellus, ut suscipit magna tellus nam eveniet rhoncus. Tin",
                    imageUrl = "https://storage.jewnetwork.com/content/users/avatars/312/avatar_312_500.jpg?1558620514",
                    lastMessage = DialogueRepoModel.LastMessageModel(
                        sender = 0,
                        message = "Lorem ipsum dolor sit amet, hendrerit eu tortor massa arcu in orci, congue ut, mauris purus vitae, tincidunt malesuada enim quam. Quis mi neque sed nec sagittis mauris, libero massa, nam ante, lacus ipsum arcu nulla phasellus, ut suscipit magna tellus nam eveniet rhoncus. Tincidunt placerat. A pharetra fringilla at quod senectus pellentesque, orci egestas elit, elementum bibendum felis ipsum metus amet scelerisque, vestibulum suspendisse facilisis non interdum magnis. Morbi suspendisse orci lectus nulla ligula.",
                        time = "Lorem ipsum dolor sit amet, hendrerit eu tortor massa arcu in orci, congue ut, mauris purus vitae, tincidunt malesuada enim quam. Quis mi neque sed nec sagittis mauris"
                    ),
                    unreadMessageCount = 0
                ),
                DialogueRepoModel(
                    id = 1,
                    name = "Dialogue 2",
                    imageUrl = "https://storage.jewnetwork.com/content/users/avatars/312/avatar_312_500.jpg?1558620514",
                    lastMessage = DialogueRepoModel.LastMessageModel(
                        sender = 0,
                        message = "Lorem ipsum dolor sit amet, hendrerit eu tortor massa arcu in orci, congue ut, mauris purus vitae, tinci",
                        time = "12:10"
                    ),
                    unreadMessageCount = 1
                ),
                DialogueRepoModel(
                    id = 2,
                    name = "Dialogue 3",
                    imageUrl = "https://storage.jewnetwork.com/content/users/avatars/312/avatar_312_500.jpg?1558620514",
                    lastMessage = DialogueRepoModel.LastMessageModel(
                        sender = 0,
                        message = "",
                        time = "12:10"
                    ),
                    unreadMessageCount = 2
                ),
                DialogueRepoModel(
                    id = 3,
                    name = "Dialogue 4",
                    imageUrl = "https://storage.jewnetwork.com/content/users/avatars/312/avatar_312_500.jpg?1558620514",
                    lastMessage = DialogueRepoModel.LastMessageModel(
                        sender = 0,
                        message = "Lorem ipsum dolor sit",
                        time = "12:10"
                    ),
                    unreadMessageCount = 3
                ),
                DialogueRepoModel(
                    id = 4,
                    name = "Dialogue 1",
                    imageUrl = "https://storage.jewnetwork.com/content/users/avatars/312/avatar_312_500.jpg?1558620514",
                    lastMessage = DialogueRepoModel.LastMessageModel(
                        sender = 0,
                        message = "Lorem ipsum dolor sit",
                        time = "12:10"
                    ),
                    unreadMessageCount = 0
                ),
                DialogueRepoModel(
                    id = 5,
                    name = "Dialogue 1",
                    imageUrl = "https://storage.jewnetwork.com/content/users/avatars/312/avatar_312_500.jpg?1558620514",
                    lastMessage = DialogueRepoModel.LastMessageModel(
                        sender = 0,
                        message = "Lorem ipsum dolor sit",
                        time = "12:10"
                    ),
                    unreadMessageCount = 0
                ),
                DialogueRepoModel(
                    id = 6,
                    name = "Dialogue 1",
                    imageUrl = "https://storage.jewnetwork.com/content/users/avatars/312/avatar_312_500.jpg?1558620514",
                    lastMessage = DialogueRepoModel.LastMessageModel(
                        sender = 0,
                        message = "Lorem ipsum dolor sit",
                        time = "12:10"
                    ),
                    unreadMessageCount = 0
                ),
                DialogueRepoModel(
                    id = 7,
                    name = "Dialogue 1",
                    imageUrl = "https://storage.jewnetwork.com/content/users/avatars/312/avatar_312_500.jpg?1558620514",
                    lastMessage = DialogueRepoModel.LastMessageModel(
                        sender = 0,
                        message = "Lorem ipsum dolor sit",
                        time = "12:10"
                    ),
                    unreadMessageCount = 120
                ),
                DialogueRepoModel(
                    id = 8,
                    name = "Dialogue 1",
                    imageUrl = "https://storage.jewnetwork.com/content/users/avatars/312/avatar_312_500.jpg?1558620514",
                    lastMessage = DialogueRepoModel.LastMessageModel(
                        sender = 0,
                        message = "Lorem ipsum dolor sit",
                        time = "12:10"
                    ),
                    unreadMessageCount = 120000000
                )
            )
        )
    }
}