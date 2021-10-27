package ru.cargo5.criminalintent.database

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Query
import ru.cargo5.criminalintent.Crime
import java.util.*

@Dao
interface CrimeDao {
    @Query("select * from crime")
    fun getCrimes(): LiveData<List<Crime>>

    @Query("select * from crime where id = (:id)")
    fun getCrime(id: UUID): LiveData<Crime?>
}