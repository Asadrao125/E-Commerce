package com.kalsoft.e_commerce.activities

import android.os.Bundle
import android.view.View
import android.widget.ImageView
import androidx.core.content.ContextCompat
import androidx.core.view.GravityCompat
import androidx.databinding.DataBindingUtil
import androidx.drawerlayout.widget.DrawerLayout
import androidx.fragment.app.Fragment
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer
import com.kalsoft.e_commerce.BaseActivity
import com.kalsoft.e_commerce.R
import com.kalsoft.e_commerce.database.Database
import com.kalsoft.e_commerce.databinding.MainActivityBinding
import com.kalsoft.e_commerce.dialogFragments.ExitDialog
import com.kalsoft.e_commerce.fragments.*
import com.kalsoft.e_commerce.helper.Commons
import com.kalsoft.e_commerce.helper.Titlebar
import com.kalsoft.e_commerce.models.Product

class MainActivity : BaseActivity(), View.OnClickListener {
    var binding: MainActivityBinding? = null
    var database: Database? = null
    var list: ArrayList<Product> = ArrayList()
    var cartCount = MutableLiveData<Int>()
    var favCount = MutableLiveData<Int>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main)

        setMainFrameLayoutID()
        setListener()

        preferenceHelper?.setLoginStatus("true")
        database = Database(this)
        database?.createDatabase()

        list.clear()
        list = database?.getAllProducts()!!
        Commons.TotalAmount = calculateTotalAmount(list)
        //Commons.Toast(this, "" + Commons.TotalAmount)
        //Commons.Toast(this, "" + list.size)
        //Commons.Toast(this, "" + database!!.getAllFavProducts(1).size)
        //Commons.Toast(this, "" + Commons.TotalAmount)

        favCount.value = database!!.getAllFavProducts(1).size
        favCount.observe(this, Observer {
            if (it == 0) {
                binding?.tvFavCount?.visibility = View.GONE
            } else {
                binding?.tvFavCount?.visibility = View.VISIBLE
                binding?.tvFavCount?.setText("" + it)
            }
        })

        cartCount.value = database?.getAllProducts()!!.size
        cartCount.observe(this, Observer {
            if (it == 0) {
                binding?.tvCartCount?.visibility = View.GONE
            } else {
                binding?.tvCartCount?.visibility = View.VISIBLE
                binding?.tvCartCount?.setText("" + it)
            }
        })

        replaceFragment(LoginFragment(), LoginFragment::class.java.simpleName, false, false)
    }

    fun calculateTotalAmount(list: ArrayList<Product>): Double {
        var sum = 0.0
        for (i in list) {
            sum = (sum + (i.price * i.quantity))
        }
        return sum
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
        if (binding?.drawerlayout?.isDrawerOpen(GravityCompat.START)!!) {
            binding?.drawerlayout!!.closeDrawers()
        } else {
            if (supportFragmentManager.backStackEntryCount >= 1) {
                supportFragmentManager.popBackStack()
            } else {
                val exitDialog = ExitDialog()
                exitDialog.show(supportFragmentManager, "exitDialog")
            }
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
        return fragments.get(fragments.size - 1)
    }

    override fun onClick(v: View?) {
        when (v?.id) {
            R.id.homeLayout -> {
                replaceFragment(
                    HomeFragment(),
                    HomeFragment::class.java.simpleName,
                    false,
                    true
                )
            }

            R.id.cartLayout -> {
                replaceFragment(
                    CartFragment(),
                    CartFragment::class.java.simpleName,
                    false,
                    true
                )
            }

            R.id.favoriteLayout -> {
                replaceFragment(
                    FavoriteFragment(),
                    FavoriteFragment::class.java.simpleName,
                    false,
                    true
                )
            }

            R.id.profileLayout -> {
                replaceFragment(
                    ProfileFragment(),
                    ProfileFragment::class.java.simpleName,
                    false,
                    true
                )
            }

            R.id.dlHomeLayout -> {
                replaceFragment(
                    HomeFragment(),
                    HomeFragment::class.java.simpleName,
                    false,
                    true
                )
            }

            R.id.dlCartLayout -> {
                replaceFragment(
                    CartFragment(),
                    CartFragment::class.java.simpleName,
                    false,
                    true
                )
            }

            R.id.dlFavoriteLayout -> {
                replaceFragment(
                    FavoriteFragment(),
                    FavoriteFragment::class.java.simpleName,
                    false,
                    true
                )
            }

            R.id.dlProfileLayout -> {
                replaceFragment(
                    ProfileFragment(),
                    ProfileFragment::class.java.simpleName,
                    false,
                    true
                )
            }
        }
    }

    fun setBottomImageColors() {
        if (getLastFragment()::class.java.simpleName.toString()
                .equals(HomeFragment()::class.java.simpleName)
        ) {
            setTintColor(binding?.imgHome)
            setNormal(binding?.imgCart)
            setNormal(binding?.imgFavorite)
            setNormal(binding?.imgProfile)
        } else if (getLastFragment()::class.java.simpleName.toString()
                .equals(CartFragment()::class.java.simpleName)
        ) {
            setTintColor(binding?.imgCart)
            setNormal(binding?.imgHome)
            setNormal(binding?.imgFavorite)
            setNormal(binding?.imgProfile)
        } else if (getLastFragment()::class.java.simpleName.toString()
                .equals(FavoriteFragment()::class.java.simpleName)
        ) {
            setTintColor(binding?.imgFavorite)
            setNormal(binding?.imgHome)
            setNormal(binding?.imgCart)
            setNormal(binding?.imgProfile)
        } else if (getLastFragment()::class.java.simpleName.toString()
                .equals(ProfileFragment()::class.java.simpleName)
        ) {
            setTintColor(binding?.imgProfile)
            setNormal(binding?.imgHome)
            setNormal(binding?.imgCart)
            setNormal(binding?.imgFavorite)
        }
    }

    fun closeDrawer() {
        binding?.drawerlayout?.closeDrawers()
    }

    private fun setTintColor(bottomImage: ImageView?) {
        bottomImage?.setColorFilter(
            ContextCompat.getColor(
                applicationContext!!,
                R.color.red_theme_color
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