package com.mcecelja.pocket.ui.pocket

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.view.WindowManager
import androidx.appcompat.app.AppCompatActivity
import com.mcecelja.pocket.R
import com.mcecelja.pocket.data.PreferenceManager
import com.mcecelja.pocket.databinding.ActivityPocketBinding
import com.mcecelja.pocket.enums.PreferenceEnum
import com.mcecelja.pocket.ui.login.LoginActivity
import com.mcecelja.pocket.utils.ErrorUtil
import org.koin.androidx.viewmodel.ext.android.viewModel

class PocketActivity : AppCompatActivity() {

    private lateinit var activityPocketBinding: ActivityPocketBinding

    private val pocketViewModel by viewModel<PocketViewModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        activityPocketBinding = ActivityPocketBinding.inflate(layoutInflater)

        setContentView(activityPocketBinding.root)

        pocketViewModel.setUser()

        pocketViewModel.user.observe(
            this,
            { PreferenceManager.savePreference(PreferenceEnum.CURRENT_USER_ID, it.id) })

        pocketViewModel.loadingVisibility.observe(
            this,
            { setupLoadingScreen(it) })

        pocketViewModel.error.observe(
            this,
            {
                ErrorUtil.showAlertMessageForErrorCode(
                    it,
                    this
                )

                ErrorUtil.handleError(it, this)
            }
        )

        val token = PreferenceManager.getPreference(PreferenceEnum.TOKEN)

        if (token.isEmpty()) {
            val loginIntent = Intent(this, LoginActivity::class.java)
            startActivity(loginIntent)
            finish()
        } else {
            if (savedInstanceState == null) {
                supportFragmentManager.beginTransaction()
                    .add(R.id.fl_fragmentContainer, TabFragment.create(), TabFragment.TAG)
                    .commit()
            }
        }
    }

    override fun onBackPressed() {
        if (pocketViewModel.allowBack.value!!) {
            super.onBackPressed()
        }
    }

    fun setupLoadingScreen(visibility: Int) {
        activityPocketBinding.rlMain.visibility = visibility

        if (visibility == View.VISIBLE) {
            this.window?.setFlags(
                WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE,
                WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE
            )

            pocketViewModel.setAllowBack(false)
        } else {
            this.window?.clearFlags(
                WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE
            )

            pocketViewModel.setAllowBack(true)
        }
    }
}