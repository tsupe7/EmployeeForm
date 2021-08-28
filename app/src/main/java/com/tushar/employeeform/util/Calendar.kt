package com.tushar.employeeform.util

import android.app.DatePickerDialog
import android.content.Context
import com.tushar.employeeform.R
import java.text.SimpleDateFormat
import java.util.*
import java.util.Calendar

/**
 * Created by Tushar on 28/8/21.
 */
object CalendarDatePicker {

    lateinit var sdf : SimpleDateFormat

    fun showDatePicker(context : Context,  datePicker: DatePicker): () -> Unit = {
        val calendar = Calendar.getInstance()
        calendar.add(Calendar.YEAR, -12)
        val dateSetListener =
            DatePickerDialog.OnDateSetListener { _, year, month, dayOfMonth ->
                calendar.set(Calendar.YEAR, year)
                calendar.set(Calendar.MONTH, month)
                calendar.set(Calendar.DAY_OF_MONTH, dayOfMonth)
                sdf = SimpleDateFormat(DateFormatConstant.DD_MMM_YYYY, Locale.getDefault())
                datePicker.onDateSelect(sdf.format(calendar.time))
            }
        val datePicker = DatePickerDialog(
            context,
            R.style.DialogTheme,
            dateSetListener,
            calendar.get(Calendar.YEAR),
            calendar.get(Calendar.MONTH),
            calendar.get(Calendar.DAY_OF_MONTH)
        )
        datePicker.datePicker.maxDate = calendar.timeInMillis
        datePicker.show()
    }
}

interface DatePicker{
    fun onDateSelect(strDate : String)
}