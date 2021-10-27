package ru.cargo5.criminalintent

import androidx.lifecycle.ViewModel
import ru.cargo5.criminalintent.database.CrimeRepository

class CrimeListViewModel:ViewModel() {
    private val crimeRepository = CrimeRepository.get()
    val crimeListLiveData = crimeRepository.getCrimes()
}