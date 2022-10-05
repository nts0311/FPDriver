package com.sonnt.fpdriver.features._base

import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment

open class BaseFragment: Fragment() {
    protected fun setActionBarTitle(title: String) {
        (requireActivity() as AppCompatActivity).supportActionBar?.title = title
    }
}