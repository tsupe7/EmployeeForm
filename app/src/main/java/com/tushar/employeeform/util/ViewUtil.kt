package com.tushar.employeeform.util

import android.view.View
import androidx.core.view.isVisible

/**
 * Created by Tushar on 28/8/21.
 */

object ViewUtil {

    fun hideView(vararg views: View) {
        for (view in views) {
            view.isVisible = false
        }
    }

    fun showView(vararg views: View) {
        for (view in views) {
            view.isVisible = true
        }
    }
}