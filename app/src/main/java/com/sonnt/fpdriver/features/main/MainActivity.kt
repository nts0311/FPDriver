package com.sonnt.fpdriver.features.main

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.activity.OnBackPressedCallback
import androidx.activity.viewModels
import androidx.appcompat.widget.Toolbar
import androidx.navigation.NavController
import androidx.navigation.NavDestination
import androidx.navigation.findNavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.NavigationUI
import androidx.navigation.ui.setupWithNavController
import com.google.android.material.appbar.MaterialToolbar
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.sonnt.fpdriver.R
import com.sonnt.fpdriver.data.repos.OrderRepository
import com.sonnt.fpdriver.features._base.BaseActivity

class MainActivity: BaseActivity() {
    private val viewModel: MainActivityViewModel by viewModels()

    private lateinit var appBarConfiguration: AppBarConfiguration
    private lateinit var navController: NavController
    private lateinit var bottomNavBar: BottomNavigationView

    private val listener =
        NavController.OnDestinationChangedListener { controller, destination, arguments ->
            onNavDestinationChanged(controller, destination, arguments)
        }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setupViews()
        bindViewModel()
        viewModel.getActiveOrder()
    }

    private fun setupViews() {
        val toolbar = findViewById<MaterialToolbar>(R.id.toolbar)
        setSupportActionBar(toolbar)
        val host: NavHostFragment = supportFragmentManager
            .findFragmentById(R.id.my_nav_host_fragment) as NavHostFragment? ?: return

        // Set up Action Bar
        navController = host.navController

        appBarConfiguration = AppBarConfiguration(navController.graph)

        setupBottomNav()
    }

    private fun bindViewModel() {
        viewModel.screenDestination.observe(this) {destination ->
            if (destination == -1) return@observe
            navController.navigate(destination)
        }

        viewModel.orderCanceled?.observe(this) {reason ->
            createDialog(content = "Đơn hàng đã bị huỷ. Lý do: $reason")
            navController.navigate(R.id.ordersFragment)
        }
    }

    override fun onBackPressed() {
        if (bottomNavBar.selectedItemId != R.id.ordersFragment) {
            bottomNavBar.selectedItemId = R.id.ordersFragment
        } else {
            createDialog(
                title = "Xác nhận",
                content = "Bạn có chắc chắn muốn thoát ứng dụng?",
                positiveButtonTitle = "Thoát",
                positiveClicked =  {
                    finish()
                },
                negativeButtonTitle = "Huỷ"
            )
        }
    }

    private fun setupBottomNav() {
        bottomNavBar = findViewById(R.id.bottom_nav_view)
        bottomNavBar.setupWithNavController(navController)
        bottomNavBar.setOnItemSelectedListener { item ->
            NavigationUI.onNavDestinationSelected(item, navController)
            return@setOnItemSelectedListener true
        }
    }

    override fun onResume() {
        super.onResume()
        navController.addOnDestinationChangedListener(listener)
    }

    override fun onPause() {
        super.onPause()
        navController.removeOnDestinationChangedListener(listener)
    }

    private fun onNavDestinationChanged(navController: NavController, destination: NavDestination, args: Bundle?) {

    }
}