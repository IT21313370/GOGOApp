package mad.project.gogoapp.models

class ModelComment {

//    variables
    var id =""
    var jobId =""
    var timestamp =""
    var comment =""
    var rating =""
    var uid =""

//    empty constructor,required by firebase

    constructor()

//    param constructor
    constructor(
        id: String,
        jobId: String,
        timestamp: String,
        comment: String,
        rating: String,
        uid: String
    ) {
        this.id = id
        this.jobId = jobId
        this.timestamp = timestamp
        this.comment = comment
        this.rating = rating
        this.uid = uid
    }


}