package ru.cargo5.criminalintent.database

import androidx.room.TypeConverter
import java.util.*

class CrimeTypeConverters {
    @TypeConverter
    fun fromDate(date:Date?):Long?{
        return date?.time
    }

    @TypeConverter
    fun toDate(millisSincEpoch:Long?): Date?{
        return millisSincEpoch?.let { Date(it) }
    }

    @TypeConverter
    fun toUUID(uuid: String?):UUID?{
        return UUID.fromString(uuid)
    }

    @TypeConverter
    fun fromUUID(uuid: UUID?):String?{
        return uuid?.toString()
    }
}