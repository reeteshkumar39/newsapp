package com.example.demo.views.view.fragments.dashboard

import androidx.test.ext.junit.runners.AndroidJUnit4
import com.example.demo.views.repositories.NewsFeedRepo
import junit.framework.TestCase
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class DashboardViewModelTest: TestCase(){

    private lateinit var viewModel: DashboardViewModel
    private lateinit var repo : NewsFeedRepo

    override fun setUp() {
        super.setUp()
        //viewModel = DashboardViewModel()
    }
}