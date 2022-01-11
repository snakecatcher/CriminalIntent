package ru.cargo5.criminalintent

import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import ru.cargo5.criminalintent.adapters.CrimeAdapter
import java.util.*
import javax.security.auth.callback.Callback

private const val TAG = "CrimeListFragment"

class CrimeListFragment:Fragment(), CrimeAdapter.Callbacks {

    interface FragmentCallback{
        fun onCrimeSelected(crimeId: UUID)
    }

    private var callback: CrimeAdapter.Callbacks? = null
    private var fragmentCallback: FragmentCallback? = null


    private val adapter: CrimeAdapter by lazy {
        CrimeAdapter(this)
    }

    private val crimeListViewModel:CrimeListViewModel by lazy {
        ViewModelProviders.of(this).get(CrimeListViewModel::class.java)
    }

    private lateinit var crimeRecyclerView: RecyclerView


    companion object{
        fun newInstance():CrimeListFragment{
            return CrimeListFragment()
        }
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        callback = this as CrimeAdapter.Callbacks?
        fragmentCallback = context as FragmentCallback?
    }

    override fun onDetach() {
        super.onDetach()
        callback = null
        fragmentCallback = null
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_crime_list, container, false)
        crimeRecyclerView = view.findViewById(R.id.crime_recycler_view) as RecyclerView
        crimeRecyclerView.adapter = adapter
        return view
    }

    private fun updateUI(crimes: List<Crime>){
        adapter.submitList(crimes)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        crimeListViewModel.crimeListLiveData.observe(
            viewLifecycleOwner,
            Observer {crimes -> crimes?.let {
                Log.i(TAG, "Got crimes ${crimes.size}")
                updateUI(crimes)
            }}
        )
    }

    override fun onCrimeSelected(crimeId: UUID) {
        Toast.makeText(context, "${crimeId.toString()} pressed!", Toast.LENGTH_SHORT).show()
        fragmentCallback?.onCrimeSelected(crimeId)
    }


}