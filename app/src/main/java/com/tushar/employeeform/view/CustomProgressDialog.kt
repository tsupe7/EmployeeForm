package com.tushar.employeeform.view

import android.app.Activity
import android.content.Context
import android.view.LayoutInflater
import androidx.appcompat.app.AlertDialog
import com.tushar.employeeform.databinding.DialogTransparentProgressBinding

/**
 * Created by Tushar on 28/8/21.
 */
class CustomProgressDialog(
    private val context: Activity,
    var isCancellable: Boolean = false,
    var isCanceledOnTouchOutside: Boolean = false
) {

    private var binding: DialogTransparentProgressBinding =
        DialogTransparentProgressBinding.inflate(context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater)
    private val builder: AlertDialog = AlertDialog.Builder(context).create()

    init {
        builder.setView(binding.root)
        builder.setCancelable(isCancellable)
        builder.setCanceledOnTouchOutside(isCanceledOnTouchOutside)
        builder.window?.setBackgroundDrawableResource(android.R.color.transparent)
    }

    fun show() {
        if (!context.isFinishing)
            builder.show()
    }

    var progressText: String? = null
        get() = binding.progressText.text.toString()
        set(value) {
            field = value
            binding.progressText.text = value
        }

    fun dismiss() {
        if (builder.isShowing && !context.isFinishing) {
            binding.progressText.text = null
            builder.dismiss()
        }
    }
}