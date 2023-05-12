package mad.project.gogoapp

import android.Manifest
import android.app.*
import android.content.Intent
import android.content.pm.PackageManager
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import com.google.firebase.ktx.Firebase
import mad.project.gogoapp.databinding.ActivityViewSingleJobDetailsBinding

import android.text.format.DateFormat
import android.view.LayoutInflater
import android.widget.Button
import android.widget.Toast
import androidx.core.app.ActivityCompat
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import com.example.simplenotification.NotificationActivity
import com.google.firebase.auth.FirebaseAuth
import mad.project.gogoapp.adapter.AdapterComment
import mad.project.gogoapp.databinding.DialogCommentAddBinding
import mad.project.gogoapp.models.ModelComment
import java.text.SimpleDateFormat

import java.util.*
import kotlin.collections.ArrayList
import kotlin.collections.HashMap

class ViewSingleJobDetailsActivity : AppCompatActivity() {

    private  lateinit var showNotificationBtn: Button



//    view binding
    private lateinit var binding:ActivityViewSingleJobDetailsBinding

//Array lis to hold comment
    private lateinit var commentArrayList: ArrayList<ModelComment>

//    adapter to be set recyclerview
    private lateinit var adapterComment: AdapterComment

    private var jobId = ""



    private lateinit var firebaseAuth:FirebaseAuth
    private lateinit var progressDialog: ProgressDialog

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityViewSingleJobDetailsBinding.inflate(layoutInflater)
        setContentView(binding.root)



        showNotificationBtn = findViewById(R.id.showNotificationBtn)

        showNotificationBtn.setOnClickListener{
            showNotification()
        }

        firebaseAuth = FirebaseAuth.getInstance()

//        get job id from intent
        jobId = intent.getStringExtra("id")!!

        loadJobDetails()
        showComments()

        progressDialog = ProgressDialog(this)
        progressDialog.setTitle("Please wait...")
        progressDialog.setCanceledOnTouchOutside(false)

        //handle click, go back
        binding.backBtn.setOnClickListener {
            onBackPressed()
        }

    }

//    notification
private fun showNotification(){
    createNotificationChannel()

    val date = Date()
    val notificationId = SimpleDateFormat("ddHHmmss",Locale.US).format(date).toInt()

    val  mainIntent = Intent(this,NotificationActivity::class.java)

    mainIntent.putExtra("KEY_NAME","GOGO")
    mainIntent.putExtra("KEY_TYPE","Notification Content")
    mainIntent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
    val mainPendingIntent = PendingIntent.getActivity(this,1,mainIntent, PendingIntent.FLAG_IMMUTABLE)

    val callIntent = Intent(this,NotificationActivity::class.java)
    callIntent.putExtra("KEY_NAME","GOGO")
    callIntent.putExtra("KEY_TYPE","Call Button Clicked")
    val callPendingIntent = PendingIntent.getActivity(this,2,callIntent, PendingIntent.FLAG_IMMUTABLE)

    val messageIntent = Intent(this,NotificationActivity::class.java)
    messageIntent.putExtra("KEY_NAME","GOGO")
    messageIntent.putExtra("KEY_TYPE","Message Button Clicked")
    val messagePendingIntent = PendingIntent.getActivity(this,3,messageIntent, PendingIntent.FLAG_IMMUTABLE)



    val notificationBuilder = NotificationCompat.Builder(this,"$CHANNEL_ID")

    notificationBuilder.setSmallIcon(R.drawable.logo3d)

    notificationBuilder.setContentTitle("New Job Request")

    notificationBuilder.setContentText("This is for your job skill accept or decline that.")

    notificationBuilder.priority = NotificationCompat.PRIORITY_HIGH
    notificationBuilder.setAutoCancel(true)

    notificationBuilder.setContentIntent(mainPendingIntent)

    notificationBuilder.addAction(R.drawable.baseline_call_24,"Call",callPendingIntent)

    notificationBuilder.addAction(R.drawable.baseline_message_24,"Message",messagePendingIntent)


    val notificationManagerCompat = NotificationManagerCompat.from(this)

    if (ActivityCompat.checkSelfPermission(
            this,
            Manifest.permission.POST_NOTIFICATIONS
        ) != PackageManager.PERMISSION_GRANTED
    ) {
        // TODO: Consider calling
        //    ActivityCompat#requestPermissions
        // here to request the missing permissions, and then overriding
        //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
        //                                          int[] grantResults)
        // to handle the case where the user grants the permission. See the documentation
        // for ActivityCompat#requestPermissions for more details.
        return
    }
    notificationManagerCompat.notify(notificationId, notificationBuilder.build())

}

    private fun createNotificationChannel(){
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.O){
            val name:CharSequence = "MyNotification"
            val description = "My notification channel description"

            val importance = NotificationManager.IMPORTANCE_DEFAULT
            val notificationChannel = NotificationChannel(CHANNEL_ID,name,importance)
            notificationChannel.description=description
            val notificationManager = getSystemService(NOTIFICATION_SERVICE) as NotificationManager
            notificationManager.createNotificationChannel(notificationChannel)

        }
    }
//    .........

    private fun showComments() {
//        init array list
        commentArrayList = ArrayList()

//        db path to load comment
        val ref = FirebaseDatabase.getInstance().getReference("Job Data")
        ref.child(jobId).child("Comments")
            .addValueEventListener(object :ValueEventListener{
                override fun onDataChange(snapshot: DataSnapshot) {
//                    clear list
                    for(ds in snapshot.children){
                        val model = ds.getValue(ModelComment::class.java)

                        commentArrayList.add(model!!)
                    }

                    // update the total comment count
                    val totalComments = commentArrayList.size
                    binding.commentCountTv.text = "Comments ($totalComments)"

//                    setup adapter
                    adapterComment = AdapterComment(this@ViewSingleJobDetailsActivity,commentArrayList)
//                    setup adapter to recycler view
                    binding.commentsRv.adapter = adapterComment

                }


                override fun onCancelled(error: DatabaseError) {

                }
            })
    }

//    rating count
private fun loadRatingsCount() {
    val ref = FirebaseDatabase.getInstance().getReference("Job Data")
    ref.child(jobId).child("Ratings")
        .addListenerForSingleValueEvent(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                var count = 0
                var totalRating = 0.0
                for (ds in snapshot.children) {
                    val rating = ds.getValue(Double::class.java)
                    if (rating != null) {
                        count++
                        totalRating += rating
                    }
                }
                val averageRating = if (count > 0) totalRating / count else 0.0
                val ratingText = "Ratings: $count, Average rating: %.2f".format(averageRating)
                binding.ratingsCountTv.text = ratingText
            }

            override fun onCancelled(error: DatabaseError) {
                Toast.makeText(
                    this@ViewSingleJobDetailsActivity,
                    "" + error.message,
                    Toast.LENGTH_SHORT
                ).show()
            }
        })
}


    private fun loadJobDetails() {
        val ref = FirebaseDatabase.getInstance().getReference("Job Data")
        ref.child(jobId)
            .addListenerForSingleValueEvent(object : ValueEventListener{
                override fun onDataChange(snapshot: DataSnapshot) {
//                    get data
                    val actHrs = "${snapshot.child("actHrs").value}"
                    val description = "${snapshot.child("description").value}"
                    val fName = "${snapshot.child("fName").value}"
                    val fee = "${snapshot.child("fee").value}"

                    val job = "${snapshot.child("job").value}"
                    val lName = "${snapshot.child("lName").value}"
                    val location = "${snapshot.child("location").value}"
                    val timestamp = "${snapshot.child("timestamp").value}"
                    val uid = "${snapshot.child("uid").value}"

                    val date = formatTimeStamp(timestamp.toLong())

                    binding.jobTv.text = job
                    binding.nameTv.text=fName+"_"+lName
                    binding.locationTv.text=location
                    binding.actHTv.text=actHrs
                    binding.dateTv.text=date
                    binding.feeTv.text=fee
                    binding.descriptionTv.text=description


                    //                    handle click show add comment dialog
                    binding.addCommentBtn.setOnClickListener{
                        if (firebaseAuth.currentUser==null){
//                            user not logged in,dont allow adding comment
                            val context = applicationContext
                            val text = "You're not logged in"
                            val duration = Toast.LENGTH_SHORT

                            val toast = Toast.makeText(context, text, duration)
                            toast.show()

                        }
                        else{
                            addCommentDialog()

                        }
                    }


                }

                override fun onCancelled(error: DatabaseError) {

                }

            })



    }
    private var comment=""
    private var rating=""

    private fun addCommentDialog() {
//        inflate/bind view for dialog

        val commentAddBinding = DialogCommentAddBinding.inflate(LayoutInflater.from(this))

//        setup alert dialog
        val builder = AlertDialog.Builder(this,R.style.CustomDialog)
        builder.setView(commentAddBinding.root)

//          create and show alert dialog
        val alertDialog = builder.create()
        alertDialog.show()

//        handle cliak,dismiss dialog
        commentAddBinding.backBtn.setOnClickListener{alertDialog.dismiss()}

//        handle click,add comment
        commentAddBinding.submitBtn.setOnClickListener{

            rating = commentAddBinding.ratingTil.rating.toString()
            comment = commentAddBinding.commentEt.text.toString().trim()

            if (comment.isEmpty()){

                val context = applicationContext
                val text = "Enter Comment And Rating"
                val duration = Toast.LENGTH_SHORT

                val toast = Toast.makeText(context, text, duration)
                toast.show()

            }
            else{
                alertDialog.dismiss()
                addComment()

            }

            if (rating.isEmpty()) {
                // User has not selected a valid rating

            } else {
                // User has selected a valid rating
                alertDialog.dismiss()
                addRating()
            }
        }


    }

    private fun addRating() {
        //        show progress
        progressDialog.setMessage("Adding Rating")
        progressDialog.show()
    }

    private fun addComment() {
//        show progress
        progressDialog.setMessage("Adding Comment")
        progressDialog.show()

        val timestamp = "${System.currentTimeMillis()}"

//        setup data to add in db for comment

        val hashMap = HashMap<String,Any>()
        hashMap["id"] = "$timestamp"
        hashMap["jobId"] = "$jobId"
        hashMap["timestamp"]="$timestamp"
        hashMap["comment"] = "$comment"
        hashMap["rating"] = "$rating"
        hashMap["uid"] = "${firebaseAuth.uid}"

//        db path to add data into it

        val ref = FirebaseDatabase.getInstance().getReference("Job Data")
        ref.child(jobId).child("Comments").child(timestamp)
            .setValue(hashMap)
            .addOnSuccessListener {

                progressDialog.dismiss()

                val context = applicationContext
                val text = "Comment added,,,"
                val duration = Toast.LENGTH_SHORT

                val toast = Toast.makeText(context, text, duration)
                toast.show()

            }
            .addOnFailureListener{e->

                val context = applicationContext
                val text = "Failed to add comment due to ${e.message} "
                val duration = Toast.LENGTH_SHORT

                val toast = Toast.makeText(context, text, duration)
                toast.show()

            }

    }

    companion object {
        private const val CHANNEL_ID = "channel01"
        fun formatTimeStamp(timestamp: Long): String {
            val cal = Calendar.getInstance(Locale.ENGLISH)
            cal.timeInMillis = timestamp

            return DateFormat.format ("dd/MM/yyyy", cal).toString()

        }
    }




}

