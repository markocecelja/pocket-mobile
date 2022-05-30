package com.mcecelja.pocket.ui.login

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.view.WindowManager
import androidx.appcompat.app.AppCompatActivity
import com.mcecelja.pocket.Pocket
import com.mcecelja.pocket.R
import com.mcecelja.pocket.data.PreferenceManager
import com.mcecelja.pocket.databinding.ActivityLoginBinding
import com.mcecelja.pocket.enums.PreferenceEnum
import com.mcecelja.pocket.ui.pocket.PocketActivity
import com.mcecelja.pocket.utils.ErrorUtil
import org.koin.androidx.viewmodel.ext.android.viewModel

class LoginActivity : AppCompatActivity() {

    private lateinit var loginBinding: ActivityLoginBinding

    private val loginViewModel by viewModel<LoginViewModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        loginBinding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(loginBinding.root)

        if (savedInstanceState == null) {
            supportFragmentManager.beginTransaction()
                .add(R.id.fl_fragmentContainer, LoginFragment.create(), LoginFragment.TAG)
                .commit()
        }

        loginViewModel.loadingVisibility.observe(this, { setupLoadingScreen(it) })
        loginViewModel.token.observe(this, {
            PreferenceManager.savePreference(
                PreferenceEnum.TOKEN,
                it
            )
            val intent = Intent(Pocket.application, PocketActivity::class.java)
            this.startActivity(intent)
        })

        loginViewModel.error.observe(
            this,
            {
                ErrorUtil.showAlertMessageForErrorCode(
                    it,
                    this
                )

                ErrorUtil.handleError(it, this)
            }
        )
    }

    override fun onBackPressed() {
        if (loginViewModel.allowBack.value!!) {
            super.onBackPressed()
        }
    }

    fun setupLoadingScreen(visibility: Int) {
        loginBinding.rlLogin.visibility = visibility

        if (visibility == View.VISIBLE) {
            this.window?.setFlags(
                WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE,
                WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE
            )

            loginViewModel.setAllowBack(false)
        } else {
            this.window?.clearFlags(
                WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE
            )

            loginViewModel.setAllowBack(true)
        }
    }
}