package com.example.storyapp.customview

import android.content.Context
import android.graphics.drawable.Drawable
import android.text.Editable
import android.text.TextWatcher
import android.util.AttributeSet
import android.view.MotionEvent
import android.view.inputmethod.InputMethodManager
import androidx.appcompat.widget.AppCompatEditText
import androidx.core.content.ContextCompat
import com.example.storyapp.R


class PasswordEditText: AppCompatEditText {
    private var validPassword: Boolean = false
    private val invalidBackground: Drawable
    private val validBackground: Drawable



    constructor(context: Context) : super(context) {
        invalidBackground = ContextCompat.getDrawable(context, R.drawable.bg_et_disable)!!
        validBackground = ContextCompat.getDrawable(context, R.drawable.bg_et)!!
        init()
    }
    constructor(context: Context, attrs: AttributeSet) : super(context, attrs) {
        invalidBackground = ContextCompat.getDrawable(context, R.drawable.bg_et_disable)!!
        validBackground = ContextCompat.getDrawable(context, R.drawable.bg_et)!!
        init()
    }
    constructor(context: Context, attrs: AttributeSet, defStyleAttr: Int) : super(context, attrs, defStyleAttr) {
        invalidBackground = ContextCompat.getDrawable(context, R.drawable.bg_et_disable)!!
        validBackground = ContextCompat.getDrawable(context, R.drawable.bg_et)!!
        init()
    }

    private fun init() {
        addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence, start: Int, count: Int, after: Int) {
                // Do nothing.
            }
            override fun onTextChanged(s: CharSequence, start: Int, before: Int, count: Int) {
                validPassword = s.toString().isValidPassword()
                updateBackground()
            }
            override fun afterTextChanged(s: Editable) {
                // Do nothing.
            }
        })
    }
    private fun updateBackground() {
        background = if (!validPassword && hasFocus()) {
            setError("password must contain minimum 8 characters")
            invalidBackground
        } else {
            validBackground
        }
    }

    override fun onTouchEvent(event: MotionEvent): Boolean {
        if (event.action == MotionEvent.ACTION_UP) {
            requestFocus()
            showSoftInput()
            return true
        }
        return super.onTouchEvent(event)
    }

    private fun showSoftInput() {
        val imm = context.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        imm.showSoftInput(this, InputMethodManager.SHOW_IMPLICIT)
    }

    private fun String.isValidPassword() = isNotEmpty() && (length >= PW_LENGTH)

    companion object{
        private const val PW_LENGTH = 8
    }
}