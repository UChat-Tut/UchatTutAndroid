package com.tla.uchattut.di

import android.content.Context
import com.tla.uchattut.di.app.AppComponent
import com.tla.uchattut.di.app.AppModule
import com.tla.uchattut.di.app.DaggerAppComponent
import com.tla.uchattut.di.auth.AuthComponent
import com.tla.uchattut.di.auth.AuthModule
import com.tla.uchattut.di.auth.DaggerAuthComponent
import com.tla.uchattut.di.chat.ChatComponent
import com.tla.uchattut.di.chat.ChatModule
import com.tla.uchattut.di.chat.DaggerChatComponent
import com.tla.uchattut.di.dialogues.DaggerDialoguesComponent
import com.tla.uchattut.di.dialogues.DialoguesComponent
import com.tla.uchattut.di.dialogues.DialoguesModule
import com.tla.uchattut.di.library.DaggerLibraryComponent
import com.tla.uchattut.di.library.LibraryComponent
import com.tla.uchattut.di.library.LibraryModule
import com.tla.uchattut.di.myprofile.DaggerMyProfileComponent
import com.tla.uchattut.di.myprofile.MyProfileComponent
import com.tla.uchattut.di.myprofile.ProfileModule
import com.tla.uchattut.di.schedule.DaggerScheduleComponent
import com.tla.uchattut.di.schedule.ScheduleComponent
import com.tla.uchattut.di.schedule.ScheduleModule
import com.tla.uchattut.di.search_user.DaggerSearchUserComponent
import com.tla.uchattut.di.search_user.SearchUserComponent
import com.tla.uchattut.di.search_user.SearchUserModule
import com.tla.uchattut.di.tasks.DaggerTasksComponent
import com.tla.uchattut.di.tasks.TasksComponent
import com.tla.uchattut.di.tasks.TasksModule
import com.tla.uchattut.presentation.auth.view.AuthActivity
import com.tla.uchattut.presentation.chat.view.ChatFragment
import com.tla.uchattut.presentation.conversation.dialogues.view.DialoguesFragment
import com.tla.uchattut.presentation.conversation.search_user.view.SearchUserFragment
import com.tla.uchattut.presentation.library.view.LibraryFragment
import com.tla.uchattut.presentation.profile.view.MyProfileFragment
import com.tla.uchattut.presentation.profile.view.OtherProfileFragment
import com.tla.uchattut.presentation.schedule.view.ScheduleFragment
import com.tla.uchattut.presentation.tasks.view.TasksFragment
import kotlin.reflect.KClass

object DaggerContainer {
    private val components = HashMap<KClass<out DaggerComponent>, DaggerComponent?>()

    @Suppress("UNCHECKED_CAST")
    private fun <T : DaggerComponent> provide(
        componentClass: KClass<out T>,
        componentBuilder: () -> T
    ): T {
        if (components[componentClass] == null) components[componentClass] = componentBuilder()
        return components[componentClass] as T
    }

    fun clear(clazz: KClass<out DaggerComponent>) {
        components[clazz] = null
    }

    private fun appComponent(context: Context?): AppComponent =
        provide(AppComponent::class) {
            DaggerAppComponent.builder()
                .appModule(AppModule(context))
                .build()
        }

    fun authComponent(context: Context, activity: AuthActivity? = null): AuthComponent =
        DaggerAuthComponent.builder()
            .authModule(AuthModule(activity))
            .appModule(AppModule(context))
            .build()

    fun myProfileComponent(fragment: MyProfileFragment): MyProfileComponent =
        provide(MyProfileComponent::class) {
            DaggerMyProfileComponent.builder()
                .appComponent(appComponent(fragment.context))
                .profileModule(ProfileModule(fragment))
                .build()
        }


    fun chatComponent(fragment: ChatFragment): ChatComponent =
        provide(ChatComponent::class) {
            DaggerChatComponent.builder()
                .appComponent(appComponent(fragment.context))
                .chatModule(ChatModule(fragment))
                .build()
        }

    fun dialoguesComponent(fragment: DialoguesFragment): DialoguesComponent =
        provide(DialoguesComponent::class) {
            DaggerDialoguesComponent.builder()
                .appComponent(appComponent(fragment.context))
                .dialoguesModule(DialoguesModule(fragment))
                .build()
        }

    fun scheduleComponent(fragment: ScheduleFragment): ScheduleComponent =
        provide(ScheduleComponent::class) {
            DaggerScheduleComponent.builder()
                .appComponent(appComponent(fragment.context))
                .scheduleModule(ScheduleModule(fragment))
                .build()
        }

    fun tasksComponent(fragment: TasksFragment): TasksComponent =
        provide(TasksComponent::class) {
            DaggerTasksComponent.builder()
                .appComponent(appComponent(fragment.context))
                .tasksModule(TasksModule(fragment))
                .build()
        }

    fun libraryComponent(fragment: LibraryFragment): LibraryComponent =
        provide(LibraryComponent::class) {
            DaggerLibraryComponent.builder()
                .appComponent(appComponent(fragment.context))
                .libraryModule(LibraryModule(fragment))
                .build()
        }

    fun searchUserComponent(fragment: SearchUserFragment): SearchUserComponent =
        provide(SearchUserComponent::class) {
            DaggerSearchUserComponent.builder()
                .appComponent(appComponent(fragment.context))
                .searchUserModule(SearchUserModule(fragment))
                .build()
        }
}