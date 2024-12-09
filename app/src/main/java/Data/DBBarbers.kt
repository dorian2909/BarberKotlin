package utn.cr.jonsbarb
import Data.Dbhelper
import android.content.ContentValues
import android.content.Context
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import Entities.Barber

open class DBBarbers(context: Context) : Dbhelper(context) {

    fun insertarBarber(id: String, name: String, phone: Int, email: String, image: ByteArray?): Long {
        var barberId: Long = -1

        try {
            val db = writableDatabase
            val values = ContentValues()

            values.put("id", id)
            values.put("name", name)
            values.put("phone", phone)
            values.put("email", email)
            values.put("imgUri", image)

            barberId = db.insert(TABLE_BARBERS, null, values)
        } catch (e: Exception) {
            e.printStackTrace()
        }

        return barberId
    }

    fun mostrarBarbers(): ArrayList<Barber> {
        val db = readableDatabase
        val listaBarbers = ArrayList<Barber>()
        val cursor: Cursor = db.rawQuery("SELECT * FROM $TABLE_BARBERS ORDER BY name ASC", null)

        if (cursor.moveToFirst()) {
            do {
                val id = cursor.getString(cursor.getColumnIndexOrThrow("id"))
                val name = cursor.getString(cursor.getColumnIndexOrThrow("name"))
                val phone = cursor.getInt(cursor.getColumnIndexOrThrow("phone"))
                val email = cursor.getString(cursor.getColumnIndexOrThrow("email"))
                val image = cursor.getBlob(cursor.getColumnIndexOrThrow("imgUri"))

                listaBarbers.add(Barber(id, name, phone, email, image))
            } while (cursor.moveToNext())
        }

        cursor.close()
        return listaBarbers
    }

    fun verBarber(id: String): Barber? {
        val db = readableDatabase
        var barber: Barber? = null
        val cursor: Cursor = db.rawQuery("SELECT * FROM $TABLE_BARBERS WHERE id = ? LIMIT 1", arrayOf(id))

        if (cursor.moveToFirst()) {
            val name = cursor.getString(cursor.getColumnIndexOrThrow("name"))
            val phone = cursor.getInt(cursor.getColumnIndexOrThrow("phone"))
            val email = cursor.getString(cursor.getColumnIndexOrThrow("email"))
            val image = cursor.getBlob(cursor.getColumnIndexOrThrow("imgUri"))

            barber = Barber(id, name, phone, email, image)
        }

        cursor.close()
        return barber
    }

    fun editarBarber(id: String, name: String, phone: Int, email: String, image: ByteArray?): Boolean {
        var correcto = false

        try {
            val db = writableDatabase
            val values = ContentValues()

            values.put("name", name)
            values.put("phone", phone)
            values.put("email", email)
            values.put("imgUri", image)

            val rowsUpdated = db.update(TABLE_BARBERS, values, "id = ?", arrayOf(id))
            if (rowsUpdated > 0) {
                correcto = true
            }
        } catch (e: Exception) {
            e.printStackTrace()
        }

        return correcto
    }

    fun eliminarBarber(id: String): Boolean {
        var correcto = false

        try {
            val db = writableDatabase
            val rowsDeleted = db.delete(TABLE_BARBERS, "id = ?", arrayOf(id))
            if (rowsDeleted > 0) {
                correcto = true
            }
        } catch (e: Exception) {
            e.printStackTrace()
        }

        return correcto
    }
}
