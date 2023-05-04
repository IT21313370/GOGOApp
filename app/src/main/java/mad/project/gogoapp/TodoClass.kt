package mad.project.gogoapp

class TodoClass(toString: String, s1: String, s2: String, toString1: String, toDouble: Double) {

    private var id: String? = null
    private var title: String? = null
    private var time: String? = null
    private var date: String? = null
    private var location: String? = null
    private var payment = 0.0



    fun TodoClass(title: String?, time: String?, date: String?, location: String?, payment: Double) {
        this.title = title
        this.time = time
        this.date = date
        this.location = location
        this.payment = payment
    }

    fun TodoClass(
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

    fun getTime(): String? {
        return time
    }

    fun getTitle(): String? {
        return title
    }

    fun setTitle(title: String?) {
        this.title = title
    }

    fun setTime(time: String?) {
        this.time = time
    }

    fun getDate(): String? {
        return date
    }

    fun setDate(date: String?) {
        this.date = date
    }

    fun getLocation(): String? {
        return location
    }

    fun setLocation(location: String?) {
        this.location = location
    }

    fun getPayment(): Double {
        return payment
    }

    fun setPayment(payment: Double) {
        this.payment = payment
    }

    fun getId(): String? {
        return id
    }

    fun setId(id: String?) {
        this.id = id
    }
}