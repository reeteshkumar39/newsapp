package com.example.demo.views.view.activities

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.navigation.NavController
import androidx.navigation.NavDirections
import androidx.navigation.findNavController
import com.divum.ibn.interfaces.OnFragmentInteractionListener
import com.example.demo.R
import com.example.demo.databinding.ActivityMainBinding

class MainActivity  : AppCompatActivity(), OnFragmentInteractionListener{

    private lateinit var navController: NavController
    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        navController = findNavController(R.id.nav_host_fragment)
    }


    override fun gotoFragment(action: Int) {
        if (navController.currentDestination == null) {
            navController.navigate(action)
        } else {
            navController.currentDestination?.let {
                if (it.id != action) {
                    navController.navigate(action)
                }
            }
        }
    }

    override fun gotoFragment(action: Int, data: Bundle) {
        if (navController.currentDestination == null) {
            navController.navigate(action, data)
        } else {
            navController.currentDestination?.let {
                if (it.id != action) {
                    navController.navigate(action, data)
                }
            }
        }
    }

    override fun gotoFragment(navDirections: NavDirections) {
        navController.navigate(navDirections)
    }

    override fun goBack() {
        onBackPressed()
    }

}