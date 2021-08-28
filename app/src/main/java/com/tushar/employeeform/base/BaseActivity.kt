package com.tushar.employeeform.base

import android.app.Activity
import android.os.Bundle
import android.view.inputmethod.InputMethodManager
import android.widget.Toast
import androidx.annotation.StringRes
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.viewbinding.ViewBinding
import com.tushar.employeeform.databinding.BaseLayoutBinding
import com.tushar.employeeform.util.ViewUtil.hideView
import com.tushar.employeeform.util.ViewUtil.showView
import com.tushar.employeeform.view.CustomProgressDialog

/**
 * Created by Tushar on 28/8/21.
 * Base Activity for all activity.
 *
 * Handles common operations.
 * Observes message live data from base view model to display toast messages.
 */
abstract class BaseActivity<T : BaseViewModel> : AppCompatActivity() {

    abstract val viewModel: T

    lateinit var baseLayoutBinding: BaseLayoutBinding

    private val progressDialog: CustomProgressDialog? by lazy { CustomProgressDialog(this) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        baseLayoutBinding = BaseLayoutBinding.inflate(layoutInflater)
        setContentView(baseLayoutBinding.root)
        baseLayoutBinding.mainViewContainer.addView(getLayoutBinding().root)
        initObserver()
        setupView(savedInstanceState)
    }

    /**
     * Observes the message changes from base view model.
     */
    private fun initObserver() {
        viewModel.messageString.observe(this, Observer {
            it.data?.run { showMessage(this) }
        })

        viewModel.messageStringId.observe(this, Observer {
            it.data?.run { showMessage(this) }
        })
    }

    /**
     * Shows the fill screen progress.
     */
    protected fun showProgressbar(cancelable: Boolean = false) {
        progressDialog?.run {
            isCanceledOnTouchOutside = cancelable
            isCancellable = cancelable
            show()
        }
    }

    /**
     * Hides the progress.
     */
    protected fun hideProgressbar() {
        progressDialog?.dismiss()
    }

    /**
     * Shows the fill screen progress.
     *
     * @param text - display over progress
     */
    protected fun showProgressbarWithText(text: String) {
        baseLayoutBinding.progressText.text = text
        showView(baseLayoutBinding.progressText, baseLayoutBinding.progressbar)
        hideView(baseLayoutBinding.mainViewContainer)
    }

    protected fun showNoInternetError() {
        showView(baseLayoutBinding.errorText)
        hideView(
            baseLayoutBinding.mainViewContainer,
            baseLayoutBinding.progressText,
            baseLayoutBinding.progressbar
        )
    }

    fun hideKeyboard() {
        val inputMethodManager: InputMethodManager =
            getSystemService(Activity.INPUT_METHOD_SERVICE) as InputMethodManager
        inputMethodManager.toggleSoftInput(InputMethodManager.HIDE_IMPLICIT_ONLY, 0)
    }

    fun showMessage(message: String) =
        Toast.makeText(applicationContext, message, Toast.LENGTH_SHORT).show()

    fun showMessage(@StringRes resId: Int) = showMessage(getString(resId))

    protected abstract fun setupView(savedInstanceState: Bundle?)

    protected abstract fun getLayoutBinding(): ViewBinding
}