package ru.cargo5.criminalintent

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.CheckBox
import android.widget.EditText
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders
import java.text.SimpleDateFormat
import java.util.*

private const val ARG_CRIME_ID = "crime_id"
private const val TAG = "CrimeFragment"
private const val DIALOG_DATE = "DialogDate"
private const val DIALOG_TIME = "DialogTime"

private const val REQUEST_DATE = 0
private const val REQUEST_TIME = 1

class CrimeFragment: Fragment(), DatePickerFragment.Callbacks, TimePickerFragment.Callbacks {
    private lateinit var crime: Crime
    private lateinit var titleField:EditText
    private lateinit var dateButton: Button
    private lateinit var timeButton: Button
    private lateinit var solvedCheckBox: CheckBox

    private val crimeDetailViewModel:CrimeDetailViewModel by lazy {
        ViewModelProviders.of(this).get(CrimeDetailViewModel::class.java)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        crime = Crime()
        val crimeId:UUID = arguments?.getSerializable(ARG_CRIME_ID) as UUID
        crimeDetailViewModel.loadCrime(crimeId)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_crime, container, false)
        titleField = view.findViewById(R.id.crime_title) as EditText
        dateButton = view.findViewById(R.id.crime_date) as Button
        timeButton = view.findViewById(R.id.crime_time) as Button
        solvedCheckBox = view.findViewById(R.id.crime_solved) as CheckBox
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        crimeDetailViewModel.crimeLiveData.observe(
            viewLifecycleOwner,
            Observer { crime -> crime?.let {
                this.crime = crime
                updateUI()
            } }
        )
    }

    private fun updateUI(){
        val sdf = SimpleDateFormat("dd.MM.yyyy E")
        val stf = SimpleDateFormat("HH:mm")
        titleField.setText(crime.title)
        dateButton.text = sdf.format(crime.date)
        timeButton.text = stf.format(crime.date)
        solvedCheckBox.apply {
            isChecked = crime.isSolved
            jumpDrawablesToCurrentState()
        }
    }

    override fun onStart() {
        super.onStart()

        val titleWatcher = object :TextWatcher{
            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
            }

            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
                crime.title = p0.toString()
            }

            override fun afterTextChanged(p0: Editable?) {

            }
        }
        titleField.addTextChangedListener(titleWatcher)

        solvedCheckBox.apply {
            setOnCheckedChangeListener{_, isChecked -> crime.isSolved = isChecked}
        }

        dateButton.setOnClickListener{
            DatePickerFragment.newInstance(crime.date).apply {
                setTargetFragment(this@CrimeFragment, REQUEST_DATE)
                show(this@CrimeFragment.requireFragmentManager(), DIALOG_DATE)
            }
        }

        timeButton.setOnClickListener {
            TimePickerFragment.newInstance(crime.date).apply {
                setTargetFragment(this@CrimeFragment, REQUEST_TIME)
                show(this@CrimeFragment.requireFragmentManager(), DIALOG_TIME)
            }
        }
    }

    override fun onStop() {
        super.onStop()
        crimeDetailViewModel.saveCrime(crime
        )
    }

    companion object{
        fun newInstance(crimeId:UUID):CrimeFragment{
            val args = Bundle().apply {
                putSerializable(ARG_CRIME_ID, crimeId)
            }
            return CrimeFragment().apply {
                arguments = args
            }
        }
    }

    override fun onDateSelected(date: Date) {
        crime.date = date
        updateUI()
    }

    override fun ontTimeSelected(date: Date) {
        crime.date = date
        updateUI()
    }


}