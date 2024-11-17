package Interface

import Entities.Barber


interface IDBManager {
    fun add(contact: Barber)
    fun update(contact: Barber)
    fun remove(id: String)
    fun getAll(): List<Barber>
    fun getById(id: String): Barber?

}