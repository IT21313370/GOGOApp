package mad.project.gogoapp

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import com.google.firebase.ktx.Firebase
import mad.project.gogoapp.databinding.ActivityViewSingleJobDetailsBinding

import android.text.format.DateFormat

import java.util.*

class ViewSingleJobDetailsActivity : AppCompatActivity() {

//    view binding
    private lateinit var binding:ActivityViewSingleJobDetailsBinding

    private var id = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityViewSingleJobDetailsBinding.inflate(layoutInflater)
        setContentView(binding.root)

//        get job id from intent
        id = intent.getStringExtra("id")!!

        loadJobDetails()

    }

    private fun loadJobDetails() {
        val ref = FirebaseDatabase.getInstance().getReference("Job Data")
        ref.child(id)
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






//
                }

                override fun onCancelled(error: DatabaseError) {

                }
            })
    }

    companion object {
        fun formatTimeStamp(timestamp: Long): String {
            val cal = Calendar.getInstance(Locale.ENGLISH)
            cal.timeInMillis = timestamp

            return DateFormat.format ("dd/MM/yyyy", cal).toString()

        }
    }
}