package com.app.rxretrofit.ui.base

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.viewbinding.ViewBinding
import com.app.rxretrofit.utils.DisposableUtils
import io.reactivex.disposables.CompositeDisposable

abstract class BaseActivity<VB : ViewBinding> : AppCompatActivity() {
    var disposable = CompositeDisposable()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        DisposableUtils.getNewCompositeSubIfUnsubscribed(
            disposable
        )
        binding = getBindingObject()
    }

    protected lateinit var binding: VB

    override fun onDestroy() {
        super.onDestroy()
        DisposableUtils.unsubscribeIfNotNull(disposable)
    }

    abstract fun getBindingObject(): VB
}