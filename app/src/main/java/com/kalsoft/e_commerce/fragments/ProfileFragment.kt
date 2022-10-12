package com.kalsoft.e_commerce.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.annotation.NonNull
import androidx.databinding.DataBindingUtil
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.*
import com.kalsoft.e_commerce.BaseFragment
import com.kalsoft.e_commerce.R
import com.kalsoft.e_commerce.databinding.ProfileFragmentBinding
import com.kalsoft.e_commerce.helper.Commons
import com.kalsoft.e_commerce.helper.Titlebar
import com.squareup.picasso.Picasso
import com.technado.demoapp.models.UserModel
import de.hdodenhof.circleimageview.CircleImageView

class ProfileFragment : BaseFragment() {
    var binding: ProfileFragmentBinding? = null
    var tvName: TextView? = null
    var tvLastSeen: TextView? = null
    var tvEmail: TextView? = null
    var tvAboutInfo: TextView? = null
    var profilePic: CircleImageView? = null
    var profilePicUrl: String? = ""
    var auth: FirebaseAuth? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_profile, container, false)

        getActivityContext()?.lockMenu()
        getActivityContext?.hideBttomBar()

        tvName = binding?.tvName
        tvEmail = binding?.tvEmail
        tvAboutInfo = binding?.tvAboutInfo
        profilePic = binding?.profilePic
        auth = FirebaseAuth.getInstance()

        setProfile(auth?.uid!!)

        return binding?.root!!
    }

    override fun setTitlebar(titlebar: Titlebar) {
        titlebar.setBackTitle(getActivityContext!!, "Profile", 0)
    }

    private fun setProfile(id: String) {
        val userRef: DatabaseReference =
            FirebaseDatabase.getInstance().getReference().child("Users")
        userRef.child(id).addValueEventListener(object : ValueEventListener {
            override fun onDataChange(@NonNull dataSnapshot: DataSnapshot) {
                val user: UserModel = dataSnapshot.getValue(UserModel::class.java)!!
                tvName?.setText(user.name)
                tvEmail?.setText(user.email)
                tvLastSeen?.setText(user.email)
                tvAboutInfo?.setText(user.aboutInfo)
                profilePicUrl = user.profilePic
                Commons.LoadImage(user.profilePic, profilePic!!)
            }

            override fun onCancelled(@NonNull databaseError: DatabaseError) {}
        })
    }
}