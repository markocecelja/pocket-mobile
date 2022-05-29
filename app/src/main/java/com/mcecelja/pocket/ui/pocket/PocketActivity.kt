package com.mcecelja.pocket.ui.pocket

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.mcecelja.pocket.R
import com.mcecelja.pocket.data.PreferenceManager
import com.mcecelja.pocket.databinding.ActivityPocketBinding
import com.mcecelja.pocket.enums.PreferenceEnum
import com.mcecelja.pocket.ui.login.LoginActivity

class PocketActivity : AppCompatActivity() {

    private lateinit var activityPocketBinding: ActivityPocketBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        activityPocketBinding = ActivityPocketBinding.inflate(layoutInflater)

        setContentView(activityPocketBinding.root)

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
}