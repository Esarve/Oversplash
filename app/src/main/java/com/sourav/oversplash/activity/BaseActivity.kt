package com.sourav.oversplash.activity

import android.os.Bundle
import android.util.TypedValue
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.color.DynamicColors

open class BaseActivity: AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        initDynamicColors();
        updateStatusBarColor()
    }

    private fun initDynamicColors() {
        DynamicColors.applyToActivityIfAvailable(this);
    }

    private fun updateStatusBarColor() {
        val typedValue = TypedValue()
        theme.resolveAttribute(android.R.attr.windowBackground, typedValue, true)
        val windowColor: Int = if (typedValue.type >= TypedValue.TYPE_FIRST_COLOR_INT && typedValue.type <= TypedValue.TYPE_LAST_COLOR_INT){
            typedValue.data;
        }else -1

        window.apply {
            statusBarColor = windowColor
        }
    }
}