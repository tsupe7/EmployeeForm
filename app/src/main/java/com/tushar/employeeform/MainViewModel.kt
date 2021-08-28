package com.tushar.employeeform

import com.tushar.employeeform.base.BaseViewModel
import com.tushar.employeeform.model.EmployeeDetail

/**
 * Created by Tushar on 28/8/21.
 */
class MainViewModel : BaseViewModel() {

    var employeeDetails = ArrayList<EmployeeDetail>()

    fun saveEmployeeDetail(employeeDetail:EmployeeDetail){
        employeeDetails.add(employeeDetail)
    }
}