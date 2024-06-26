package com.example.accounting.utils

import android.annotation.SuppressLint
import android.app.Dialog
import android.content.Context
import android.os.Bundle
import android.text.TextUtils
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.DatePicker
import android.widget.EditText
import com.example.accounting.R

class SelectTimeDialog(context: Context) : Dialog(context), View.OnClickListener {
    private lateinit var hourEt: EditText
    private lateinit var minuteEt: EditText
    private lateinit var datePicker: DatePicker
    private lateinit var ensureBtn: Button
    private lateinit var cancelBtn: Button
    private var onEnsureListener: OnEnsureListener? = null

    fun setOnEnsureListener(onEnsureListener: OnEnsureListener?) {
        this.onEnsureListener = onEnsureListener
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.dialog_time)
        hourEt = findViewById(R.id.dialog_time_et_hour)
        minuteEt = findViewById(R.id.dialog_time_et_minute)
        datePicker = findViewById(R.id.dialog_time_dp)
        ensureBtn = findViewById(R.id.dialog_time_btn_ensure)
        cancelBtn = findViewById(R.id.dialog_time_btn_cancel)
        ensureBtn.setOnClickListener(this)
        cancelBtn.setOnClickListener(this)
        hideDatePickerHeader()
    }

    override fun onClick(v: View?) {
        when (v?.id) {
            R.id.dialog_time_btn_cancel -> cancel()
            R.id.dialog_time_btn_ensure -> {
                val year = datePicker.year
                val month = datePicker.month + 1
                val dayOfMonth = datePicker.dayOfMonth
                var monthStr = month.toString()
                if (month < 10) {
                    monthStr = "0$month"
                }
                var dayStr = dayOfMonth.toString()
                if (dayOfMonth < 10) {
                    dayStr = "0$dayOfMonth"
                }
                var hourStr = hourEt.text.toString()
                var minuteStr = minuteEt.text.toString()
                var hour = 0
                if (!TextUtils.isEmpty(hourStr)) {
                    hour = Integer.parseInt(hourStr) % 24
                }
                var minute = 0
                if (!TextUtils.isEmpty(minuteStr)) {
                    minute = Integer.parseInt(minuteStr) % 60
                }
                hourStr = hour.toString()
                minuteStr = minute.toString()
                if (hour < 10) {
                    hourStr = "0$hour"
                }
                if (minute < 10) {
                    minuteStr = "0$minute"
                }
                val timeFormat = "$dayStr $monthStr $year $hourStr:$minuteStr"
                onEnsureListener?.onEnsure(timeFormat, year, month, dayOfMonth)
                cancel()
            }
        }
    }

    @SuppressLint("DiscouragedApi")
    private fun hideDatePickerHeader() {
        val rootView = datePicker.getChildAt(0) as ViewGroup? ?: return
        val headerView = rootView.getChildAt(0) ?: return

        var headerId = context.resources.getIdentifier("day_picker_selector_layout", "id", "android")
        if (headerId == headerView.id) {
            headerView.visibility = View.GONE
            val layoutParamsRoot = rootView.layoutParams
            layoutParamsRoot.width = ViewGroup.LayoutParams.WRAP_CONTENT
            rootView.layoutParams = layoutParamsRoot
            val animator = rootView.getChildAt(1) as ViewGroup
            val layoutParamsAnimator = animator.layoutParams
            layoutParamsAnimator.width = ViewGroup.LayoutParams.WRAP_CONTENT
            animator.layoutParams = layoutParamsAnimator
            val child = animator.getChildAt(0)
            val layoutParamsChild = child.layoutParams
            layoutParamsChild.width = ViewGroup.LayoutParams.WRAP_CONTENT
            child.layoutParams = layoutParamsChild
            return
        }
        headerId = context.resources.getIdentifier("date_picker_header", "id", "android")
        if (headerId == headerView.id) {
            headerView.visibility = View.GONE
        }
    }

    interface OnEnsureListener {
        fun onEnsure(time: String, year: Int, month: Int, day: Int)
    }
}


