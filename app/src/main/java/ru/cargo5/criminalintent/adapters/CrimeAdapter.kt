package ru.cargo5.criminalintent.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import ru.cargo5.criminalintent.Crime
import ru.cargo5.criminalintent.CrimeListFragment
import ru.cargo5.criminalintent.R
import java.util.*

public class CrimeAdapter(private var  callbacks: CrimeAdapter.Callbacks? = null):ListAdapter<Crime, CrimeAdapter.CrimeHolder>(
    Crime.CrimeDiff
){
    interface Callbacks{
        fun onCrimeSelected(crimeId: UUID)
    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CrimeHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.list_item_crime, parent, false)
        return CrimeHolder(view)
    }

    override fun onBindViewHolder(holder: CrimeHolder, position: Int) {
        val crime = getItem(position)
        holder.bind(crime)
    }

    inner class CrimeHolder(view: View):RecyclerView.ViewHolder(view), View.OnClickListener{
        private lateinit var crime: Crime
        val titleTextView: TextView = itemView.findViewById(R.id.crime_title)
        val dateTextView: TextView = itemView.findViewById(R.id.crime_date)
        val solvedImageView: ImageView = itemView.findViewById(R.id.crime_solved)

        init {
            itemView.setOnClickListener(this)
        }

        fun bind(crime:Crime){
            this.crime = crime
            titleTextView.text = this.crime.title
            dateTextView.text = this.crime.date.toString()
            solvedImageView.visibility = if(crime.isSolved){
                View.VISIBLE
            } else{
                View.GONE
            }
        }

        override fun onClick(p0: View?) {
            callbacks?.onCrimeSelected(crime.id)
        }
    }
}