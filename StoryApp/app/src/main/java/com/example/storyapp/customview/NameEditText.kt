package com.example.storyapp.customview

import android.content.Context
import android.graphics.Canvas
import android.graphics.Rect
import android.graphics.drawable.Drawable
import android.text.Editable
import android.text.TextWatcher
import android.util.AttributeSet
import android.view.View
import androidx.appcompat.widget.AppCompatEditText
import androidx.core.content.ContextCompat
import com.example.storyapp.R

class NameEditText: AppCompatEditText{
    private var hasFocus = false
    private lateinit var invalidBackground: Drawable
    private lateinit var validBackground: Drawable
    private var validName: Boolean = false

    constructor(context: Context) : super(context) {
        init()
    }
    constructor(context: Context, attrs: AttributeSet) : super(context, attrs) {
        init()
    }
    constructor(context: Context, attrs: AttributeSet, defStyleAttr: Int) : super(context, attrs, defStyleAttr) {
        init()
    }

    private fun init() {
        invalidBackground = ContextCompat.getDrawable(context, R.drawable.bg_et_disable) as Drawable
        validBackground = ContextCompat.getDrawable(context, R.drawable.bg_et) as Drawable

        addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence, start: Int, count: Int, after: Int) {
                // Do nothing.
            }
            override fun onTextChanged(s: CharSequence, start: Int, before: Int, count: Int) {
                validName = s.toString().isValidName()
            }
            override fun afterTextChanged(s: Editable) {
                // Do nothing.
            }
        })
    }
    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)
        hint = "Name"
        textAlignment = View.TEXT_ALIGNMENT_VIEW_START
        background = if(!validName&&hasFocus){
            setError(context.getString(R.string.name_empty))
            invalidBackground
        } else{
            validBackground
        }
    }

    override fun onFocusChanged(focused: Boolean, direction: Int, previouslyFocusedRect: Rect?) {
        super.onFocusChanged(focused, direction, previouslyFocusedRect)
        hasFocus = focused
    }

    private fun String.isValidName() = isNotBlank() && isNotEmpty()
}