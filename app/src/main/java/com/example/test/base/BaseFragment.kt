package com.example.test.base

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.result.contract.ActivityResultContracts
import androidx.fragment.app.Fragment
import androidx.viewbinding.ViewBinding

abstract class BaseFragment<BINDING : ViewBinding> : Fragment() {

    private var _binding: BINDING? = null

    protected val binding
        get() = _binding!!

    private val requestPermission = registerForActivityResult(
        ActivityResultContracts.RequestMultiplePermissions()
    ) { permissions ->
        if (permissions.all { it.value }) {
            onPermissionGranted()
        } else {
            onPermissionDenied()
        }
    }

    abstract fun getViewBinding(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): BINDING

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        initData()
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        if (_binding == null) {
            _binding = getViewBinding(inflater, container, savedInstanceState)
        }
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initView()
        initPermission()
        initObserver()
        initViewListener()
        afterViewCreated()
    }

    open fun initViewListener() {
        //Empty, just define for children override
    }

    open fun initView() = Unit

    open fun initPermission() {
        // Returns the onPermissionGranted() event if all permissions are granted
        if (allPermissionsGranted()) {
            onPermissionGranted()
            return
        }

        // Request permission if not already
        requestAllPermission()
    }

    open fun initObserver() = Unit

    open fun initData() = Unit

    open fun afterViewCreated() = Unit

    open fun onPermissionGranted() = Unit

    open fun onPermissionDenied() = Unit

    open fun getRequiredPermissions(): MutableList<String>? = null

    protected fun allPermissionsGranted(): Boolean {
        // If the permissions list is empty then default return true
        val permissions = getRequiredPermissions()
        if (permissions.isNullOrEmpty()) return true

        return requireContext().checkSelfPermissionCompat(permissions)
    }

    protected fun requestAllPermission() = getRequiredPermissions()?.let {
        requestPermission.launch(it.toTypedArray())
    }

    protected fun showLoadingByState(isVisible: Boolean) {
        val activity = activity ?: return
        if (activity is BaseActivity<*>) {
            activity.showLoadingByState(isVisible)
        }
    }

}
