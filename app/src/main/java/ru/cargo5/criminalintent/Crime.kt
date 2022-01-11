package ru.cargo5.criminalintent
import androidx.recyclerview.widget.DiffUtil
import androidx.room.Entity
import androidx.room.PrimaryKey
import java.util.*
@Entity
data class Crime(
    @PrimaryKey val id: UUID = UUID.randomUUID(),
    var title: String = "",
    var date: Date = Date(),
    var isSolved:Boolean = false




) {
    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as Crime

        if (id != other.id) return false
        if (title != other.title) return false
        if (date != other.date) return false
        if (isSolved != other.isSolved) return false

        return true
    }

    override fun hashCode(): Int {
        var result = id.hashCode()
        result = 31 * result + title.hashCode()
        result = 31 * result + date.hashCode()
        result = 31 * result + isSolved.hashCode()
        return result
    }

    object CrimeDiff: DiffUtil.ItemCallback<Crime>(){
        override fun areItemsTheSame(oldItem: Crime, newItem: Crime): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: Crime, newItem: Crime): Boolean {
            return oldItem==newItem
        }

    }

}
