package mad.project.gogoapp

class Todo {

    var id: String? = null
    var title: String? = null
    var time: String? = null
    var date: String? = null
    var location: String? = null
    var payment = 0.0

    constructor() {}
    constructor(title: String?, time: String?, date: String?, location: String?, payment: Double) {
        this.title = title
        this.time = time
        this.date = date
        this.location = location
        this.payment = payment
    }

    constructor(
        id: String?,
        title: String?,
        time: String?,
        date: String?,
        location: String?,
        payment: Double
    ) {
        this.id = id
        this.title = title
        this.time = time
        this.date = date
        this.location = location
        this.payment = payment
    }
}

