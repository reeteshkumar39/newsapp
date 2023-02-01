package com.example.demo.views.base

import androidx.annotation.LayoutRes
import androidx.fragment.app.Fragment

open class BaseFragment : Fragment {
    internal constructor() : super()
    internal constructor(@LayoutRes layoutResId: Int) : super(layoutResId)
}