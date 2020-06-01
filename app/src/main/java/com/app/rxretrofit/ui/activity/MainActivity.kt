package com.app.rxretrofit.ui.activity

import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import com.app.rxretrofit.R
import com.app.rxretrofit.apiservices.RestClient
import com.app.rxretrofit.databinding.ActivityMainBinding
import com.app.rxretrofit.ui.adapter.UserAdapter
import com.app.rxretrofit.ui.base.BaseActivity
import com.pagingdemo.utils.NetworkUtils
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : BaseActivity<ActivityMainBinding>() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setContentView(binding.root)

        binding.apply {
            rvUser.apply {
                layoutManager = LinearLayoutManager(this@MainActivity)
            }
        }
        if (NetworkUtils.isOnline(this)) {
            disposable.add(RestClient.get()!!.requestListUsers("1").subscribe({ response ->
                rvUser.adapter = UserAdapter(response.data!!)
            }, { error -> error.printStackTrace() }
            ))
        }

    }

    override fun getBindingObject(): ActivityMainBinding =
        ActivityMainBinding.inflate(layoutInflater)

}