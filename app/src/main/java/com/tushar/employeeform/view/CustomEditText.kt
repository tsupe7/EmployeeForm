package com.tushar.employeeform.view

import android.content.Context
import android.graphics.drawable.Drawable
import android.text.Editable
import android.text.InputFilter
import android.text.InputType
import android.text.TextWatcher
import android.text.method.DigitsKeyListener
import android.text.method.PasswordTransformationMethod
import android.util.AttributeSet
import android.view.LayoutInflater
import android.view.View
import android.view.inputmethod.EditorInfo
import android.widget.FrameLayout
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.core.view.isVisible
import com.tushar.employeeform.R
import com.tushar.employeeform.databinding.LayoutCustomEdittextBinding
import com.tushar.employeeform.util.clickWithDebounce


/**
 * Created by Tushar on 28/8/21.
 */

class CustomEditText @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : FrameLayout(context, attrs, defStyleAttr) {
    private var binding: LayoutCustomEdittextBinding =
        LayoutCustomEdittextBinding.inflate(LayoutInflater.from(context), this, true)

    var title: String? = null
        get() = binding.tvTitle.text.toString()
        set(value) {
            field = value
            binding.tvTitle.setText(value)
        }

    var isMandatory: Boolean = true
        set(value) {
            field = value
            binding.tvMandatory.isVisible = value
        }


    var text: String? = null
        get() = binding.editText.text.toString()
        set(value) {
            field = value
            binding.editText.setText(value)
        }

    var hint: String? = null
        set(value) {
            field = value
            binding.editText.hint = value
        }

    var imeOptions: Int = EditorInfo.IME_ACTION_NEXT
        set(value) {
            field = value
            binding.editText.imeOptions = value
        }

    var inputType: Int = EditorInfo.TYPE_CLASS_TEXT
        set(value) {
            field = value
            binding.editText.inputType = value
        }

    var maxLength: Int = Int.MAX_VALUE
        set(value) {
            field = value
            binding.editText.filters = arrayOf(InputFilter.LengthFilter(value))
        }

    var isLayoutEnabled: Boolean = true
        set(value) {
            field = value
            binding.editText.isEnabled = value
            binding.rightDrawableView.isEnabled = false
        }

    var inpuStatus: InputStatus = InputStatus.NORMAL
        set(value) {
            field = value
            setStatus(value)
        }

    var errorText: String? = null
        set(value) {
            field = value
            inpuStatus = if (errorText.isNullOrBlank())
                InputStatus.NORMAL
            else
                InputStatus.ERROR
        }

    var rightImage: Drawable? = null
        set(value) {
            field = value
            if (value == null) {
                binding.rightDrawableView.visibility = View.GONE
            } else {
                binding.rightDrawableView.setImageDrawable(value)
            }
        }

    var digits: String? = null
        set(value) {
            field = value
            if (value != null) {
                binding.editText.keyListener = DigitsKeyListener.getInstance(value)
                binding.editText.setRawInputType(inputType)
            } else
                binding.editText.inputType = inputType
        }

    override fun setOnFocusChangeListener(l: OnFocusChangeListener?) {
        binding.editText.onFocusChangeListener = l
    }

    private fun setStatus(value: InputStatus) {
        when (value) {
            InputStatus.NORMAL -> {
                binding.editText.background =
                    ContextCompat.getDrawable(context, R.drawable.input_field_normal_bg)
                binding.tvError.text = null
                binding.tvError.isVisible = false
                isLayoutEnabled = true
            }
            InputStatus.ERROR -> {
                binding.editText.background =
                    ContextCompat.getDrawable(context, R.drawable.input_field_error_bg)
                binding.tvError.text = errorText
                binding.tvError.isVisible = true
                isLayoutEnabled = true
            }
            InputStatus.DISABLED -> {
                binding.editText.background =
                    ContextCompat.getDrawable(context, R.drawable.input_field_normal_bg)
                binding.tvError.text = null
                binding.tvError.isVisible = false
                isLayoutEnabled = false
            }
        }
    }

    init {
        val typedArray = context.obtainStyledAttributes(attrs, R.styleable.CustomEditText)
        text = typedArray.getString(R.styleable.CustomEditText_text)
        hint = typedArray.getString(R.styleable.CustomEditText_hint)
        title = typedArray.getString(R.styleable.CustomEditText_title)
        imeOptions = typedArray.getInt(
            R.styleable.CustomEditText_android_imeOptions,
            EditorInfo.IME_ACTION_NEXT
        )
        inputType = typedArray.getInt(
            R.styleable.CustomEditText_android_inputType,
            InputType.TYPE_CLASS_TEXT
        )
        maxLength = typedArray.getInt(R.styleable.CustomEditText_maxLength, Int.MAX_VALUE)
        isLayoutEnabled = typedArray.getBoolean(R.styleable.CustomEditText_layoutEnabled, true)
        isMandatory = typedArray.getBoolean(R.styleable.CustomEditText_mandatory, false)
        rightImage = typedArray.getDrawable(R.styleable.CustomEditText_rightImage)
        digits = typedArray.getString(R.styleable.CustomEditText_android_digits)
        typedArray.recycle()
    }

    private fun togglePasswordVisibility(isChecked: Boolean) {
        binding.run {
            if (isChecked)
                editText.transformationMethod = null
            else
                editText.transformationMethod = PasswordTransformationMethod()

            editText.post { editText.setSelection(editText.text.length) }
        }

    }

    fun onTextChangeListener(onTextChanged: (String) -> Unit) {
        val textWatcher = object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
//do nothing
            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
//do nothing
            }

            override fun afterTextChanged(s: Editable?) {
                onTextChanged(s.toString())
            }
        }
        binding.editText.addTextChangedListener(textWatcher)
    }


    fun setRightImageViewVisibility(value: Boolean) {
        binding.rightDrawableView.isVisible = value
    }

    fun setInputClickable(action: () -> Unit) {
        binding.editText.isFocusable = false
        binding.editText.isCursorVisible = false
        binding.editText.isClickable = true
        binding.rightDrawableView.isClickable = false
        binding.rightDrawableView.isFocusable = false
        binding.editText.clickWithDebounce(action)
    }

    override fun setOnKeyListener(l: OnKeyListener?) {
        binding.editText.setOnKeyListener(l)
    }

    override fun clearFocus() {
        binding.editText.clearFocus()
    }

    fun setOnEditorActionListener(l: TextView.OnEditorActionListener) {
        binding.editText.setOnEditorActionListener(l)
    }
}