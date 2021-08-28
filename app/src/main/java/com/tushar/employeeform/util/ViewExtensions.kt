package com.tushar.employeeform.util

import android.os.SystemClock
import android.view.View

/**
 * Created by Tushar on 28/8/21.
 * Any view related extension functions which are not part of the core ktx libraries can be
 * added here.
 */

fun View.clickWithDebounce(action: () -> Unit, debounceTime: Long = 1200L) {
    this.setOnClickListener(object : View.OnClickListener {
        private var lastClickTime: Long = 0

        override fun onClick(v: View) {
            if (SystemClock.elapsedRealtime() - lastClickTime < debounceTime) return
            else action()

            lastClickTime = SystemClock.elapsedRealtime()
        }
    })
}
