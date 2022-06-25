package com.example.composeloginzkb.ui.datepicker

import android.app.DatePickerDialog
import android.content.Context
import android.content.DialogInterface
import android.widget.DatePicker
import java.time.LocalDateTime

object DatePickerUtil {
    fun openDatePicker(context: Context, onDatePicked: (LocalDateTime) -> Unit) {

        val today = LocalDateTime.now()
        val dateDialog = createDatePicker(context, DateSetListener(onDatePicked), today)
        dateDialog.setButton(
            DialogInterface.BUTTON_NEGATIVE,
            context.getString(android.R.string.cancel)
        ) { dialog, _ ->
            dialog.dismiss()
        }
        dateDialog.show()
    }

    private fun createDatePicker(
        context: Context,
        listener: DatePickerDialog.OnDateSetListener,
        dateTime: LocalDateTime
    ): DatePickerDialog {
        //Note: DatePicker counts Months from 0-11 but LocalDateTime counts 1-12. So we need to subtract 1.
        return DatePickerDialog(
            context,
            listener,
            dateTime.year,
            dateTime.month.value - 1,
            dateTime.dayOfMonth
        )
    }

    private class DateSetListener(private val onDateSet: (LocalDateTime) -> Unit) :
        DatePickerDialog.OnDateSetListener {
        override fun onDateSet(view: DatePicker?, year: Int, month: Int, dayOfMonth: Int) {
            //Note: DatePicker counts Months from 0-11 but LocalDateTime counts 1-12. So we need to add 1.
            val localDateTime = LocalDateTime.of(year, month + 1, dayOfMonth, 0, 0, 0)
            onDateSet(localDateTime)
        }
    }
}