package ru.cargo5.criminalintent

import android.app.Application
import ru.cargo5.criminalintent.database.CrimeRepository

class CriminalIntentApplication: Application() {
    override fun onCreate() {
        super.onCreate()
        CrimeRepository.initialize(this)
    }

}