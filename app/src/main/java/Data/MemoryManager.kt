package Data

import Entities.Barber
import Interface.IDBManager

object MemoryManager : IDBManager {
    private var barbList = mutableListOf<Barber>()

    override fun add(barb: Barber) {
        barbList.add(barb)
    }

    override fun update(contact: Barber) {
        remove(contact.id)
        barbList.add(contact)
    }

    override fun remove(id: String) {
        barbList.removeIf { it.id.trim() == id.trim() }
    }

    override fun getAll(): List<Barber> = barbList.toList()

    override fun getById(id: String): Barber? {
        try {
            var result = barbList.filter { (it.id) == id }
            return if (!result.any()) null else result[0]
        } catch (e: Exception) {
            throw e
        }
    }


}