package com.kalsoft.e_commerce.activities

import android.os.Bundle
import android.view.View
import android.widget.ImageView
import androidx.core.content.ContextCompat
import androidx.core.view.GravityCompat
import androidx.databinding.DataBindingUtil
import androidx.drawerlayout.widget.DrawerLayout
import androidx.fragment.app.Fragment
import com.kalsoft.e_commerce.BaseActivity
import com.kalsoft.e_commerce.R
import com.kalsoft.e_commerce.database.Database
import com.kalsoft.e_commerce.databinding.MainActivityBinding
import com.kalsoft.e_commerce.dialogFragments.ExitDialog
import com.kalsoft.e_commerce.fragments.*
import com.kalsoft.e_commerce.helper.Titlebar

class MainActivity : BaseActivity(), View.OnClickListener {
    var binding: MainActivityBinding? = null
    var database: Database? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main)

        setMainFrameLayoutID()
        setListener()

        preferenceHelper?.setLoginStatus("true")
        database = Database(this)
        database?.createDatabase()

        replaceFragment(LoginFragment(), LoginFragment::class.java.simpleName, false, false)
        setTintColor(binding?.imgHome)
    }

    fun setListener() {
        binding?.homeLayout?.setOnClickListener(this)
        binding?.cartLayout?.setOnClickListener(this)
        binding?.favoriteLayout?.setOnClickListener(this)
        binding?.profileLayout?.setOnClickListener(this)
        binding?.dlHomeLayout?.setOnClickListener(this)
        binding?.dlCartLayout?.setOnClickListener(this)
        binding?.dlFavoriteLayout?.setOnClickListener(this)
        binding?.dlProfileLayout?.setOnClickListener(this)
    }

    fun getTitlebar(): Titlebar {
        return binding!!.titlebar
    }

    fun hideBttomBar() {
        binding?.llBotomBar?.visibility = View.GONE
    }

    fun showBttomBar() {
        binding?.llBotomBar?.visibility = View.VISIBLE
    }

    override fun onBackPressed() {
        if (supportFragmentManager.backStackEntryCount >= 1) {
            val fragmentManager = supportFragmentManager
            val fragments: List<Fragment> = fragmentManager.fragments
            val last: Fragment = fragments.get(fragments.size - 1)
            //clearBackStack()
            //replaceFragment(HomeFragment(), HomeFragment::class.java.simpleName, false, false)
            setTintColor(binding?.imgHome)
            setNormal(binding?.imgCart)
            setNormal(binding?.imgFavorite)
            setNormal(binding?.imgProfile)
            supportFragmentManager.popBackStack()
        } else {
            val exitDialog = ExitDialog()
            exitDialog.show(supportFragmentManager, "exitDialog")
        }
    }

    fun mainHideTitle() {
        binding!!.titlebar.visibility = View.GONE
    }

    fun mainShowTitle() {
        binding!!.titlebar.visibility = View.VISIBLE
    }

    fun clearBackStack() {
        val fragmentManager = supportFragmentManager
        for (i in 0 until fragmentManager.backStackEntryCount) {
            fragmentManager.popBackStack()
        }
    }

    override fun setMainFrameLayoutID() {
        mainFrameLayoutID = binding?.mainContainer!!.id
    }

    fun unlockMenu() {
        binding!!.drawerlayout.setDrawerLockMode(DrawerLayout.LOCK_MODE_UNLOCKED)
    }

    fun lockMenu() {
        binding!!.drawerlayout.setDrawerLockMode(DrawerLayout.LOCK_MODE_LOCKED_CLOSED)
    }

    fun openDrawer() {
        binding!!.drawerlayout.openDrawer(GravityCompat.START)
    }

    fun closeDrawers() {
        binding!!.drawerlayout.closeDrawers()
    }

    fun getLastFragment(): Fragment {
        val fragmentManager = supportFragmentManager
        val fragments: List<Fragment> = fragmentManager.fragments
        val last: Fragment = fragments.get(fragments.size - 1)
        return last
    }

    override fun onClick(v: View?) {
        when (v?.id) {
            R.id.homeLayout -> {
                clearBackStack()
                if (!getLastFragment()::class.java.simpleName.toString()
                        .equals(HomeFragment()::class.java.simpleName)
                ) {
                    setTintColor(binding?.imgHome)
                    setNormal(binding?.imgCart)
                    setNormal(binding?.imgFavorite)
                    setNormal(binding?.imgProfile)
                    replaceFragment(
                        HomeFragment(),
                        HomeFragment::class.java.simpleName,
                        false,
                        true
                    )
                }
            }

            R.id.cartLayout -> {
                if (!getLastFragment()::class.java.simpleName.toString()
                        .equals(CartFragment()::class.java.simpleName)
                ) {
                    setTintColor(binding?.imgCart)
                    setNormal(binding?.imgHome)
                    setNormal(binding?.imgFavorite)
                    setNormal(binding?.imgProfile)
                    replaceFragment(CartFragment(), CartFragment::class.java.simpleName, true, true)
                }
            }

            R.id.favoriteLayout -> {
                if (!getLastFragment()::class.java.simpleName.toString()
                        .equals(FavoriteFragment()::class.java.simpleName)
                ) {
                    setTintColor(binding?.imgFavorite)
                    setNormal(binding?.imgHome)
                    setNormal(binding?.imgCart)
                    setNormal(binding?.imgProfile)
                    replaceFragment(
                        FavoriteFragment(),
                        FavoriteFragment::class.java.simpleName,
                        true,
                        true
                    )
                }
            }

            R.id.profileLayout -> {
                if (!getLastFragment()::class.java.simpleName.toString()
                        .equals(ProfileFragment()::class.java.simpleName)
                ) {
                    setTintColor(binding?.imgProfile)
                    setNormal(binding?.imgHome)
                    setNormal(binding?.imgCart)
                    setNormal(binding?.imgFavorite)
                    replaceFragment(
                        ProfileFragment(),
                        ProfileFragment::class.java.simpleName,
                        true,
                        true
                    )
                }
            }

            R.id.dlHomeLayout -> {
                clearBackStack()
                if (!getLastFragment()::class.java.simpleName.toString()
                        .equals(HomeFragment()::class.java.simpleName)
                ) {
                    setTintColor(binding?.imgHome)
                    setNormal(binding?.imgCart)
                    setNormal(binding?.imgFavorite)
                    setNormal(binding?.imgProfile)
                    replaceFragment(
                        HomeFragment(),
                        HomeFragment::class.java.simpleName,
                        false,
                        true
                    )
                }
            }

            R.id.dlCartLayout -> {
                if (!getLastFragment()::class.java.simpleName.toString()
                        .equals(CartFragment()::class.java.simpleName)
                ) {
                    setTintColor(binding?.imgCart)
                    setNormal(binding?.imgHome)
                    setNormal(binding?.imgFavorite)
                    setNormal(binding?.imgProfile)
                    replaceFragment(CartFragment(), CartFragment::class.java.simpleName, true, true)
                }
            }

            R.id.dlFavoriteLayout -> {
                if (!getLastFragment()::class.java.simpleName.toString()
                        .equals(FavoriteFragment()::class.java.simpleName)
                ) {
                    setTintColor(binding?.imgFavorite)
                    setNormal(binding?.imgHome)
                    setNormal(binding?.imgCart)
                    setNormal(binding?.imgProfile)
                    replaceFragment(
                        FavoriteFragment(),
                        FavoriteFragment::class.java.simpleName,
                        true,
                        true
                    )
                }
            }

            R.id.dlProfileLayout -> {
                if (!getLastFragment()::class.java.simpleName.toString()
                        .equals(ProfileFragment()::class.java.simpleName)
                ) {
                    setTintColor(binding?.imgProfile)
                    setNormal(binding?.imgHome)
                    setNormal(binding?.imgCart)
                    setNormal(binding?.imgFavorite)
                    replaceFragment(
                        ProfileFragment(),
                        ProfileFragment::class.java.simpleName,
                        true,
                        true
                    )
                }
            }
        }
    }

    private fun setTintColor(bottomImage: ImageView?) {
        bottomImage?.setColorFilter(
            ContextCompat.getColor(
                applicationContext!!,
                R.color.selected_bottom_color
            ), android.graphics.PorterDuff.Mode.MULTIPLY
        )
    }

    private fun setNormal(bottomImage: ImageView?) {
        bottomImage?.setColorFilter(
            ContextCompat.getColor(
                applicationContext!!,
                R.color.white
            ), android.graphics.PorterDuff.Mode.MULTIPLY
        )
    }
}