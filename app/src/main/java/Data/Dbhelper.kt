package Data

import android.content.ContentValues
import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import android.graphics.Bitmap
import java.io.ByteArrayOutputStream

open class Dbhelper(context: Context) : SQLiteOpenHelper(context, DATABASE_NAME, null, DATABASE_VERSION) {

    companion object {
        const val DATABASE_VERSION = 1
        const val DATABASE_NAME = "barbers.db"
        const val TABLE_BARBERS = "barbers"
    }

    override fun onCreate(db: SQLiteDatabase) {

        db.execSQL(
            "CREATE TABLE $TABLE_BARBERS (" +
                    "id TEXT PRIMARY KEY," +
                    "name TEXT NOT NULL," +
                    "phone INTEGER NOT NULL," +
                    "email TEXT NOT NULL," +
                    "imgBin BLOB)" // Campo para almacenar la imagen
        )
    }

    override fun onUpgrade(db: SQLiteDatabase, oldVersion: Int, newVersion: Int) {
        // Eliminar la tabla si existe y volver a crearla
        db.execSQL("DROP TABLE IF EXISTS $TABLE_BARBERS")
        onCreate(db)
    }
}