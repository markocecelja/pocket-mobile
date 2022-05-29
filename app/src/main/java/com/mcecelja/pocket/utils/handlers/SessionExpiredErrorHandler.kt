package com.mcecelja.pocket.utils.handlers

import android.app.Activity
import android.content.Intent
import com.mcecelja.pocket.Pocket
import com.mcecelja.pocket.data.PreferenceManager
import com.mcecelja.pocket.enums.PreferenceEnum
import com.mcecelja.pocket.ui.login.LoginActivity
import com.mcecelja.pocket.utils.RestUtil

class SessionExpiredErrorHandler: ErrorHandler {

    override fun handleError(activity: Activity) {
        PreferenceManager.removePreference(PreferenceEnum.TOKEN)
        RestUtil.clearHeader()
        val loginIntent = Intent(Pocket.application, LoginActivity::class.java)
        activity.startActivity(loginIntent)
        activity.finish()
    }
}