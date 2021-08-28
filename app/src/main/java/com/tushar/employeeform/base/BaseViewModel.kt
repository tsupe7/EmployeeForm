package com.tushar.employeeform.base

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.tushar.employeeform.util.SingleLiveEvent
import com.tushar.employeeform.util.Result

/**
 * Created by Tushar on 28/8/21.
 * Base ViewModel for all viewModel.
 * Handles common operations.
 * Very use full for data sharing withing activity
 * common data store here
 */
abstract class BaseViewModel : ViewModel() {

    protected val _messageStringId: SingleLiveEvent<Result<Int>> = SingleLiveEvent()
    val messageStringId: LiveData<Result<Int>>
        get() = _messageStringId

    protected val _messageString: SingleLiveEvent<Result<String>> = SingleLiveEvent()
    val messageString: LiveData<Result<String>>
        get() = _messageString
}