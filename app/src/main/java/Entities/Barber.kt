package Entities

class Barber {
    private var _id: String = ""
    private var _name: String = ""

    private var _phone: Int = 0
    private var _email: String = ""
    private var imageUri: String =""


    constructor()

    constructor(
        id: String,
        name: String,
        phone: Int,
        email: String,
        img:String

    ) {
        this._id = id
        this._name = name

        this._phone = phone
        this._email = email
        this.imageUri= img

    }


    var id: String
        get() = this._id
        set(value) {
            this._id = value
        }

    var name: String
        get() = this._name
        set(value) {
            this._name = value
        }




    var phone: Int
        get() = this._phone
        set(value) {
            this._phone = value
        }

    var email: String
        get() = this._email
        set(value) {
            this._email = value
        }
    var image: String
        get() = this.imageUri
        set(value) {
            this.imageUri = value
        }
}