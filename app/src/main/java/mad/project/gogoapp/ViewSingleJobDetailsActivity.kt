package mad.project.gogoapp

import android.app.AlertDialog
import android.app.Dialog
import android.app.ProgressDialog
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
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth
import mad.project.gogoapp.adapter.AdapterComment
import mad.project.gogoapp.databinding.DialogCommentAddBinding
import mad.project.gogoapp.models.ModelComment

import java.util.*
import kotlin.collections.ArrayList
import kotlin.collections.HashMap

class ViewSingleJobDetailsActivity : AppCompatActivity() {

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

//                    setup adapter
                    adapterComment = AdapterComment(this@ViewSingleJobDetailsActivity,commentArrayList)
//                    setup adapter to recycler view
                    binding.commentsRv.adapter = adapterComment

                }


                override fun onCancelled(error: DatabaseError) {

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
        fun formatTimeStamp(timestamp: Long): String {
            val cal = Calendar.getInstance(Locale.ENGLISH)
            cal.timeInMillis = timestamp

            return DateFormat.format ("dd/MM/yyyy", cal).toString()

        }
    }



}
