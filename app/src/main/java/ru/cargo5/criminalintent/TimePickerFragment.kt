package ru.cargo5.criminalintent

import android.app.Dialog
import android.app.TimePickerDialog
import android.os.Bundle
import android.widget.DatePicker
import android.widget.TimePicker
import androidx.fragment.app.DialogFragment
import java.util.*

private const val ARG_TIME = "time"

class TimePickerFragment:DialogFragment() {
    interface Callbacks{
        fun ontTimeSelected(date: Date)
    }
    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val date = arguments?.getSerializable(ARG_TIME) as Date
        val calendar = Calendar.getInstance()
        calendar.time = date
        val initialYear = calendar.get(Calendar.YEAR)
        val initialMonth = calendar.get(Calendar.MONTH)
        val initialDay = calendar.get(Calendar.DAY_OF_MONTH)
        val initialHour = calendar.get(Calendar.HOUR_OF_DAY)
        val initialMin = calendar.get(Calendar.MINUTE)

        val timeListener = TimePickerDialog.OnTimeSetListener { _:TimePicker, hourOfDay:Int, minute:Int ->
            val resultTime:Date = GregorianCalendar(initialYear, initialMonth, initialDay, hourOfDay, minute).time
            targetFragment?.let {
                fragment ->  (fragment as Callbacks).ontTimeSelected(resultTime)
            }
        }

        return TimePickerDialog(requireContext(), timeListener, initialHour, initialMin, true)
    }

    companion object{
        fun newInstance(date: Date):TimePickerFragment{
            val args = Bundle().apply {
                putSerializable(ARG_TIME, date)
            }
            return TimePickerFragment().apply {
                arguments = args
            }
        }
    }
}