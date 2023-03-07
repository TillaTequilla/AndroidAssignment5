package com.androidAssignment5.ui.mainActivity

import android.os.Bundle
import androidx.activity.viewModels
import com.androidAssignment5.R
import com.androidAssignment5.architecture.BaseActivity
import com.androidAssignment5.databinding.ActivityMainBinding
import com.androidAssignment5.util.Constance


class MainActivity : BaseActivity<ActivityMainBinding>(ActivityMainBinding::inflate){
    private val viewModel: MainActivityViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        viewModel.setId( intent.getStringExtra(Constance.INTENT_ID).toString())
        viewModel.setToken(getString(R.string.Token) + intent.getStringExtra(Constance.INTENT_ACCESS_TOKEN)
            .toString())
        super.onCreate(savedInstanceState)
    }
}

