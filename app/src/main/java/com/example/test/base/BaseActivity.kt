package com.example.test.base

import android.os.Bundle
import android.view.View
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.isVisible
import androidx.viewbinding.ViewBinding

abstract class BaseActivity<BINDING : ViewBinding> : AppCompatActivity() {

    lateinit var binding: BINDING

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = getViewBinding()
        setContentView(binding.root)

        onActivityCreated(savedInstanceState)
        supportActionBar?.hide()
    }

    abstract fun getViewBinding(): BINDING

    open fun onActivityCreated(savedInstanceState: Bundle?) = Unit

    open fun getLoadingView(): View? = null

    fun showLoadingByState(isVisible: Boolean) {
        getLoadingView()?.isVisible = isVisible
    }
}
