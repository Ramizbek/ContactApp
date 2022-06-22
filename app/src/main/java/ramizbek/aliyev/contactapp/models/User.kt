package ramizbek.aliyev.contactapp.models

class User {
    var id: Int? = null
    var name: String? = null
    var number: String? = null

    constructor(name: String?, number: String?) {
        this.name = name
        this.number = number
    }

    constructor()

    constructor(id: Int?, name: String?, number: String?) {
        this.id = id
        this.name = name
        this.number = number
    }


}