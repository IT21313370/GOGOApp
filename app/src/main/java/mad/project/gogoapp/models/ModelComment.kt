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
data class EditCommentModel(
    val id: String, // the ID of the comment being edited
    val jobId: String, // the ID of the job associated with the comment
    val comment: String // the new comment text
)
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

    constructor(jobId: String, comment: String, rating: String)


}