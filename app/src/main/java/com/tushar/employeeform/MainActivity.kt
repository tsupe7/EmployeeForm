package com.tushar.employeeform


import android.os.Bundle
import android.view.KeyEvent
import android.view.View
import android.view.inputmethod.EditorInfo
import android.widget.TextView
import androidx.activity.viewModels
import com.tushar.employeeform.base.BaseActivity
import com.tushar.employeeform.databinding.ActivityMainBinding
import com.tushar.employeeform.model.EmployeeDetail
import com.tushar.employeeform.util.CalendarDatePicker
import com.tushar.employeeform.util.DatePicker
import com.tushar.employeeform.util.clickWithDebounce
import java.util.*

/**
 * Created by Tushar on 28/8/21.
 */

class MainActivity : BaseActivity<MainViewModel>() {

    private lateinit var activityMainBinding: ActivityMainBinding

    override val viewModel: MainViewModel by viewModels()

    override fun getLayoutBinding() = ActivityMainBinding.inflate(layoutInflater).apply {
        activityMainBinding = this
    }

    override fun setupView(savedInstanceState: Bundle?) {
        activityMainBinding.run {
            toolbar.ivSetting.visibility= View.VISIBLE
            etFromDate.setInputClickable(CalendarDatePicker.showDatePicker(this@MainActivity, object :
                DatePicker {
                override fun onDateSelect(strDate: String) {
                    etFromDate.text = strDate
                }
            }))

            etToDate.setInputClickable(CalendarDatePicker.showDatePicker(this@MainActivity, object :
                DatePicker {
                override fun onDateSelect(strDate: String) {
                    etToDate.text = strDate
                }
            }))

            setEventListeners()
            btnSave.clickWithDebounce({ saveEmployeeDetail() })
            tvDelete.clickWithDebounce({ clearFields() })
        }
    }


    private fun setEventListeners() {
        val focusListener =
            View.OnFocusChangeListener { _, hasFocus -> if (!hasFocus) enableButton() }
            activityMainBinding.run {
            etSchoolName.onFocusChangeListener = focusListener
            etDegree.onFocusChangeListener = focusListener
            etFieldsOfStudy.onFocusChangeListener = focusListener

            etFieldsOfStudy.setOnEditorActionListener(TextView.OnEditorActionListener { v, actionId, event ->
                if (actionId == EditorInfo.IME_ACTION_SEARCH
                    || actionId == EditorInfo.IME_ACTION_DONE
                    || event.action == KeyEvent.ACTION_DOWN
                    && event.keyCode == KeyEvent.KEYCODE_ENTER
                ) {
                    etFieldsOfStudy.clearFocus()
                    enableButton()
                }
                false
            })
        }
    }

    private fun ActivityMainBinding.validateSchoolName(enableButton: Boolean): Boolean {
        var enableButton1 = enableButton
        if (etSchoolName.text.toString().isBlank()) {
            enableButton1 = false

        }
        else if(etSchoolName.text?.length!! < 2) {
            etSchoolName.errorText = getString(R.string.enter_school_Name)
            enableButton1 = false
        }

        return enableButton1
    }

    private fun ActivityMainBinding.validateDegree(enableButton: Boolean): Boolean {
        var enableButton1 = enableButton
        if (etDegree.text.toString().isBlank()) {
            enableButton1 = false
        }
        else if(etDegree.text?.length!! < 2) {
            etDegree.errorText = getString(R.string.enter_degree)
            enableButton1 = false
        }

        return enableButton1
    }

    private fun ActivityMainBinding.validateFieldsOfStudy(enableButton: Boolean): Boolean {
        var enableButton1 = enableButton
        if (etFieldsOfStudy.text.toString().isBlank()) {
            enableButton1 = false
        } else if(etFieldsOfStudy.text?.length!! < 2){
            etFieldsOfStudy.errorText = getString(R.string.enter_fields_of_study)
            enableButton1 = false
        }
        return enableButton1
    }

    private fun enableButton(){

        var enableButton = true
        activityMainBinding.run {
            if (etSchoolName.text.toString().isBlank())
                enableButton = false
            if (etDegree.text.toString().isBlank())
                enableButton = false
            if (etFieldsOfStudy.text.toString().isBlank())
                enableButton = false
            enableButton = validateSchoolName(enableButton)
            enableButton = validateDegree(enableButton)
            enableButton = validateFieldsOfStudy(enableButton)

            btnSave.isEnabled = enableButton
        }
    }

    private fun saveEmployeeDetail(){
        activityMainBinding.run {
            var employeeDetail= EmployeeDetail(schoolName = etSchoolName.text, degree = etDegree.text,fieldsOfStudy = etFieldsOfStudy.text,
                fromDate = etFromDate.text, toDate = etToDate.text,details = etDetails.text)
            viewModel.saveEmployeeDetail(employeeDetail)
            clearFields()
        }
    }

    private fun clearFields(){
        activityMainBinding.run {
            etSchoolName.text = ""
            etDegree.text = ""
            etFieldsOfStudy.text = ""
            etFromDate.text = ""
            etToDate.text = ""
            etDetails.text = ""
            btnSave.isEnabled = false
        }
    }
}