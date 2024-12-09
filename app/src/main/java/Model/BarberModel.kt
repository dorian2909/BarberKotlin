package Model
import Data.MemoryManager
import Entities.Barber
import Interface.IDBManager
import android.content.Context
import utn.cr.jonsbarb.R

class BarberModel(context: Context) {
    private var dbManager: IDBManager = MemoryManager
    private val _context: Context = context

    fun addBarber(contact: Barber) {
        dbManager.add(contact)
    }

    fun getBarbers() = dbManager.getAll()

    fun getBarber(id: String): Barber {
        val result = dbManager.getById(id)
        if (result == null) {
            val message = _context.getString(R.string.msgBarbernotFound)
            throw Exception(message)
        }
        return result
    }

    fun getBarberNames(): List<String> {
        return dbManager.getAll().map { it.name }
    }

    fun remBarber(id: String) {
        val result = dbManager.getById(id)
        if (result == null) {
            val message = _context.getString(R.string.msgBarbernotFound)
            throw Exception(message)
        }
        dbManager.remove(id)
    }

    fun updateBarber(contact: Barber) {
        dbManager.update(contact)
    }



    // Método isDuplicate para verificar duplicados por ID o correo electrónico
    fun isDuplicate(contact: Barber): Boolean {
        return dbManager.getAll().any {
            it.id == contact.id || it.email.equals(contact.email, ignoreCase = true)
        }
    }

}