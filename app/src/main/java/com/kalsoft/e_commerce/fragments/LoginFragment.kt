package com.kalsoft.e_commerce.fragments

import android.Manifest
import android.content.Intent
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.os.Environment
import android.provider.Settings
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.LinearLayout
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import com.google.android.gms.tasks.OnCompleteListener
import com.google.firebase.auth.FirebaseAuth
import com.kalsoft.e_commerce.BaseFragment
import com.kalsoft.e_commerce.R
import com.kalsoft.e_commerce.databinding.CartFragmentBinding
import com.kalsoft.e_commerce.databinding.LoginFragmentBinding
import com.kalsoft.e_commerce.helper.Commons
import com.kalsoft.e_commerce.helper.Titlebar
import com.karumi.dexter.Dexter
import com.karumi.dexter.MultiplePermissionsReport
import com.karumi.dexter.PermissionToken
import com.karumi.dexter.listener.PermissionRequest
import com.karumi.dexter.listener.multi.MultiplePermissionsListener
import com.rengwuxian.materialedittext.MaterialEditText

class LoginFragment : BaseFragment() {
    var binding: LoginFragmentBinding? = null
    var etEmail: MaterialEditText? = null
    var etPassword: MaterialEditText? = null
    var layoutCreateAccount: LinearLayout? = null
    var btnLogin: Button? = null
    var mAuth: FirebaseAuth? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_login, container, false)

        getActivityContext()?.lockMenu()
        getActivityContext?.hideBttomBar()

        etEmail = binding?.etEmail
        etPassword = binding?.etPassword
        layoutCreateAccount = binding?.layoutCreateAccount
        btnLogin = binding?.btnLogin
        mAuth = FirebaseAuth.getInstance()

        val user = FirebaseAuth.getInstance().currentUser
        if (user != null) {
            getActivityContext!!.replaceFragment(
                HomeFragment(),
                HomeFragment::class.java.simpleName,
                false,
                false
            )
        }

        btnLogin?.setOnClickListener {
            val email = etEmail?.text.toString().trim()
            val password = etPassword?.text.toString().trim()
            if (!email.isEmpty() && !password.isEmpty()) {
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
                    if (!Environment.isExternalStorageManager()) {
                        val intent = Intent(Settings.ACTION_MANAGE_APP_ALL_FILES_ACCESS_PERMISSION)
                        intent.addCategory("android.intent.category.DEFAULT")
                        intent.data =
                            Uri.parse(String.format("package:%s", getActivityContext!!.packageName))
                        startActivityForResult(intent, 2296)
                    } else {
                        checkPermission2(email, password)
                    }
                } else {
                    checkPermission(email, password)
                }
            } else {
                Toast.makeText(
                    getActivityContext!!,
                    "Please enter email or password",
                    Toast.LENGTH_SHORT
                ).show()
            }
        }

        layoutCreateAccount?.setOnClickListener {
            getActivityContext!!.replaceFragment(
                SignupFragment(),
                SignupFragment::class.java.simpleName,
                true,
                false
            )
        }

        return binding?.root!!
    }

    override fun setTitlebar(titlebar: Titlebar) {
        titlebar.hideTitleBar()
    }

    fun checkPermission(email: String, password: String) {
        Dexter.withContext(getActivityContext!!)
            .withPermissions(
                Manifest.permission.WRITE_EXTERNAL_STORAGE,
                Manifest.permission.RECORD_AUDIO
            ).withListener(object : MultiplePermissionsListener {
                override fun onPermissionsChecked(report: MultiplePermissionsReport) {
                    if (report.areAllPermissionsGranted()) {
                        login(email, password)
                    }
                }

                override fun onPermissionRationaleShouldBeShown(
                    permissions: List<PermissionRequest?>?,
                    token: PermissionToken?
                ) {
                    token!!.continuePermissionRequest()
                }
            }).check()
    }

    fun checkPermission2(email: String, password: String) {
        Dexter.withContext(getActivityContext!!)
            .withPermissions(
                Manifest.permission.RECORD_AUDIO
            ).withListener(object : MultiplePermissionsListener {
                override fun onPermissionsChecked(report: MultiplePermissionsReport) {
                    if (report.areAllPermissionsGranted()) {
                        login(email, password)
                    }
                }

                override fun onPermissionRationaleShouldBeShown(
                    permissions: List<PermissionRequest?>?,
                    token: PermissionToken?
                ) {
                    token!!.continuePermissionRequest()
                }
            }).check()
    }

    private fun login(email: String, password: String) {
        mAuth?.signInWithEmailAndPassword(email, password)
            ?.addOnCompleteListener(OnCompleteListener {
                if (it.isSuccessful) {
                    getActivityContext!!.replaceFragment(
                        HomeFragment(),
                        HomeFragment::class.java.simpleName,
                        false,
                        false
                    )
                } else {
                    Commons.Toast(getActivityContext!!, it.exception?.localizedMessage!!)
                }
            })
    }
}