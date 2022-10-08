package com.kalsoft.e_commerce.fragments

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.provider.MediaStore
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import com.google.android.gms.tasks.OnCompleteListener
import com.google.android.gms.tasks.OnFailureListener
import com.google.android.gms.tasks.OnSuccessListener
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.messaging.FirebaseMessaging
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.UploadTask
import com.kalsoft.e_commerce.BaseFragment
import com.kalsoft.e_commerce.R
import com.kalsoft.e_commerce.databinding.CartFragmentBinding
import com.kalsoft.e_commerce.databinding.SignupFragmentBinding
import com.kalsoft.e_commerce.helper.Commons
import com.kalsoft.e_commerce.helper.Titlebar
import com.rengwuxian.materialedittext.MaterialEditText
import java.util.*
import kotlin.collections.HashMap

class SignupFragment : BaseFragment() {
    var binding: SignupFragmentBinding? = null
    var etName: MaterialEditText? = null
    var etEmail: MaterialEditText? = null
    var etAboutInfo: MaterialEditText? = null
    var etPassword: MaterialEditText? = null
    var btnRegister: Button? = null
    var imgProfile: ImageView? = null
    var imgAddImage: ImageView? = null
    var auth: FirebaseAuth? = null
    var rootRef: DatabaseReference? = null
    var userRef: DatabaseReference? = null
    var Image_Request_Code = 7
    var imageUrl: String? = ""

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_signup, container, false)

        getActivityContext()?.lockMenu()
        getActivityContext?.hideBttomBar()

        etName = binding?.etName
        etAboutInfo = binding?.etAboutInfo
        etEmail = binding?.etEmail
        etPassword = binding?.etPassword
        btnRegister = binding?.btnRegister
        imgProfile = binding?.imgProfile
        imgAddImage = binding?.imgAddImage
        auth = FirebaseAuth.getInstance()
        rootRef = FirebaseDatabase.getInstance().getReference()

        btnRegister?.setOnClickListener(View.OnClickListener {
            val name = etName?.text.toString().trim()
            val email = etEmail?.text.toString().trim()
            val password = etPassword?.text.toString().trim()
            val aboutInfo = etAboutInfo?.text.toString().trim()

            if (imageUrl!!.isEmpty()) {
                Commons.Toast(getActivityContext!!, "Please Upload Image")
            } else if (name.isEmpty()) {
                Commons.Toast(getActivityContext!!, "Please Enter Name")
            } else if (email.isEmpty()) {
                Commons.Toast(getActivityContext!!, "Please Enter Email")
            } else if (password.isEmpty()) {
                Commons.Toast(getActivityContext!!, "Please Enter Password")
            } else if (aboutInfo.isEmpty()) {
                Commons.Toast(getActivityContext!!, "Please Enter About Info")
            } else {
                getToken(name, email, password, imageUrl!!, aboutInfo)
            }
        })

        imgAddImage?.setOnClickListener(View.OnClickListener {
            val intent = Intent()
            intent.type = "image/*"
            intent.action = Intent.ACTION_GET_CONTENT
            startActivityForResult(intent, Image_Request_Code)
        })

        return binding?.root!!
    }

    override fun setTitlebar(titlebar: Titlebar) {
        titlebar.hideTitleBar()
    }

    private fun getToken(
        name: String,
        email: String,
        password: String,
        imageUrl: String,
        aboutInfo: String
    ) {
        FirebaseMessaging.getInstance().token.addOnCompleteListener(OnCompleteListener { task ->
            if (task.isSuccessful) {
                val token = task.result
                register(name, email, password, token, imageUrl, aboutInfo)
            } else {
                Commons.Toast(getActivityContext!!, "" + task.exception?.localizedMessage)
            }
        })
    }

    private fun register(
        name: String,
        email: String,
        password: String,
        token: String,
        imageUrl: String,
        aboutInfo: String
    ) {
        auth!!.createUserWithEmailAndPassword(email, password).addOnCompleteListener { task ->
            if (task.isSuccessful) {
                val firebaseUser = auth!!.currentUser
                val userid = firebaseUser!!.uid
                val hashMap: HashMap<String, String> = HashMap()
                hashMap["id"] = userid
                hashMap["name"] = name
                hashMap["email"] = email
                hashMap["password"] = password
                hashMap["token"] = token
                hashMap["profilePic"] = imageUrl
                hashMap["aboutInfo"] = aboutInfo
                userRef = rootRef?.child("Users")?.child(userid)
                userRef?.setValue(hashMap)?.addOnCompleteListener(OnCompleteListener {
                    if (it.isSuccessful) {
                        Commons.Toast(getActivityContext!!, "Registered Successfully")
                        getActivityContext!!.clearBackStack()
                        getActivityContext!!.replaceFragment(
                            LoginFragment(),
                            LoginFragment::class.java.simpleName,
                            false,
                            false
                        )
                    } else {
                        Commons.Toast(getActivityContext!!, "" + task.exception?.localizedMessage)
                    }
                })
            } else {
                Commons.Toast(getActivityContext!!, "" + task.exception?.localizedMessage)
            }
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == Image_Request_Code
            && resultCode == AppCompatActivity.RESULT_OK
            && data != null
            && data.data != null
        ) {
            val file_uri = data.data
            val bitmap =
                MediaStore.Images.Media.getBitmap(getActivityContext!!.contentResolver, file_uri)
            imgProfile?.setImageBitmap(bitmap)
            uploadImageToFirebase(file_uri!!)
        }
    }

    private fun uploadImageToFirebase(fileUri: Uri) {
        val fileName = UUID.randomUUID().toString() + ".jpg"
        val refStorage = FirebaseStorage.getInstance().reference.child("profilePictures/$fileName")

        refStorage.putFile(fileUri)
            .addOnSuccessListener(
                OnSuccessListener<UploadTask.TaskSnapshot> { taskSnapshot ->
                    taskSnapshot.storage.downloadUrl.addOnSuccessListener {
                        imageUrl = it.toString()
                    }
                }).addOnFailureListener(OnFailureListener { e ->
                print(e.message)
            })
    }
}