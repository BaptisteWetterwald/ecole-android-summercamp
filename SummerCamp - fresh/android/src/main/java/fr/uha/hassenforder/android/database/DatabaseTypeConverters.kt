package fr.uha.hassenforder.android.database

import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.net.Uri
import androidx.room.TypeConverter
import fr.uha.hassenforder.android.ui.field.Time
import java.io.ByteArrayOutputStream
import java.time.Instant
import java.util.*

@Suppress("unused")
object DatabaseTypeConverters {
    @TypeConverter
    fun long2Date(time: Long): Date? {
        return if (time == -1L) null else Date(time)
    }

    @TypeConverter
    fun date2Long(date: Date?): Long {
        return date?.time ?: -1
    }

    @TypeConverter
    fun long2Instant(time: Long): Instant? {
        return if (time == -1L) null else Date(time).toInstant()
    }

    @TypeConverter
    fun instant2Long(instant: Instant?): Long {
        return instant?.toEpochMilli() ?: -1
    }

    @TypeConverter
    fun string2Uri(path:String): Uri? {
        if (path == "null://") return null
        return Uri.parse (path)
    }

    @TypeConverter
    fun uri2String(uri : Uri?): String {
        if (uri == null) return "null://"
        return uri.toString()
    }

    @TypeConverter
    fun toBitmap(content: ByteArray?): Bitmap? {
        return if (content == null) null else BitmapFactory.decodeByteArray(content, 0, content.size)
    }

    @TypeConverter
    fun fromBitmap(bitmap: Bitmap?): ByteArray? {
        if (bitmap == null) return null
        val stream = ByteArrayOutputStream()
        bitmap.compress(Bitmap.CompressFormat.PNG, 100, stream)
        return stream.toByteArray()
    }
}