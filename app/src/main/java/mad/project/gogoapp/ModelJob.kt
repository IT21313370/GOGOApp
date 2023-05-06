package mad.project.gogoapp

class ModelJob {

    var id: String = ""
    var job: String = ""
    var location: String = ""
    var fName: String = ""
    var lName: String = ""
    var fee: String = ""
    var actHrs: String = ""
    var description: String = ""
    var timestamp: Long = 0
    var uid: String = ""

    //empty constructor
    constructor()

    //parameterized constructor
    constructor(id: String, job: String, location: String, fName: String, lName: String, fee: String, actHrs: String, description: String, timestamp: Long, uid: String) {
        this.id = id
        this.job = job
        this.location = location
        this.fName = fName
        this.lName = lName
        this.fee = fee
        this.actHrs = actHrs
        this.description = description
        this.timestamp = timestamp
        this.uid = uid
    }
}