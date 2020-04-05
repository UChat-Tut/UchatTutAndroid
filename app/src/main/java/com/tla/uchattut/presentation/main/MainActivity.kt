package com.tla.uchattut.presentation.main

import android.os.Build
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.tla.uchattut.R
import com.tla.uchattut.data.network.model.UserNetworkModel
import com.tla.uchattut.domain.auth.AuthInteractor
import com.tla.uchattut.presentation._common.BaseFragment
import com.tla.uchattut.presentation._common.popEntireBackStack
import kotlinx.android.synthetic.main.activity_main.*
import javax.inject.Inject

class MainActivity : AppCompatActivity() {

    @Inject
    lateinit var authInteractor: AuthInteractor

    val _foundStudentLiveData = MutableLiveData<UserNetworkModel>()
    val foundStudentLiveData: LiveData<UserNetworkModel> = _foundStudentLiveData

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        replaceScreen(MainFragment())

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            window.decorView.systemUiVisibility = View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR
            window.statusBarColor = ContextCompat.getColor(this, R.color.colorPrimary)
        }
    }

    override fun onStart() {
        super.onStart()

        progressBar.visibility = View.GONE
    }

    fun replaceScreen(fragment: Fragment, addToBackStack: Boolean = false) {
        supportFragmentManager.popEntireBackStack()
        val transaction = supportFragmentManager.beginTransaction()
            .replace(R.id.mainFragmentContainer, fragment)
        if (addToBackStack) {
            transaction.addToBackStack(null)
        }
        transaction.commit()
    }

    fun addScreen(fragment: Fragment, addToBackStack: Boolean = false) {
        supportFragmentManager.popEntireBackStack()
        val transaction = supportFragmentManager.beginTransaction()
            .add(R.id.mainFragmentContainer, fragment)
        if (addToBackStack) {
            transaction.addToBackStack(null)
        }
        transaction.commit()
    }

    override fun onBackPressed() {
        val backPressableFragments = supportFragmentManager.fragments
            .filterIsInstance(BaseFragment::class.java)

        for (fragment in backPressableFragments) {
            if (fragment.onBackPressed()) {
                return
            }
        }

        super.onBackPressed()
    }
}
