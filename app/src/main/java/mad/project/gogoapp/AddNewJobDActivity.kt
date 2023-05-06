package mad.project.gogoapp

import android.app.ProgressDialog
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.FirebaseDatabase
import mad.project.gogoapp.databinding.ActivityAddNewJobDactivityBinding
import java.text.DateFormat
import java.util.*
import kotlin.collections.HashMap

class AddNewJobDActivity : AppCompatActivity() {

    private lateinit var binding: ActivityAddNewJobDactivityBinding
    private lateinit var firebaseAuth: FirebaseAuth
    private lateinit var progressDialog: ProgressDialog

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAddNewJobDactivityBinding.inflate(layoutInflater)
        setContentView(binding.root)

        firebaseAuth = FirebaseAuth.getInstance()


        progressDialog = ProgressDialog(this)
        progressDialog.setTitle("Please wait...")
        progressDialog.setCanceledOnTouchOutside(false)

        binding.submitBtn.setOnClickListener {
            validateData()
        }

    }

    private var job = ""
    private var location = ""
    private var fName = ""
    private var lName = ""
    private var fee = ""
    private var actHrs = ""
    private var description = ""

    private fun validateData() {
        //validate data

        //get data
        job = binding.jobEt.text.toString().trim()
        location = binding.locationEt.text.toString().trim()
        fName = binding.fNameEt.text.toString().trim()
        lName = binding.lNameEt.text.toString().trim()
        fee = binding.feeEt.text.toString().trim()
        actHrs = binding.actHrsEt.text.toString().trim()
        description = binding.descriptionEt.text.toString().trim()

        //validate data
        if (job.isEmpty()){
            Toast.makeText(this, "Enter Category", Toast.LENGTH_SHORT).show()
        }
        else{
            addCategoryFirebase()
        }
    }

    private fun addCategoryFirebase() {
        //show progress
        progressDialog.show()

        //get timestamp
        val timestamp = System.currentTimeMillis()

        //setup data to add in firebase db
        val hashMap = HashMap<String, Any>()
        hashMap["id"] = "$timestamp"
        hashMap["job"] = job
        hashMap["location"] = location
        hashMap["fName"] = fName
        hashMap["lName"] = lName
        hashMap["fee"] = fee
        hashMap["actHrs"] = actHrs
        hashMap["description"] = description
        hashMap["timestamp"] = timestamp
        hashMap["uid"] = "${firebaseAuth.uid}"


        //add to firebase db: databse Root > Categories > categoryId > category info
        val ref = FirebaseDatabase.getInstance().getReference("Job Data")
        ref.child("$timestamp")
            .setValue(hashMap)
            .addOnSuccessListener {
                //added successfully
                progressDialog.dismiss()
                Toast.makeText(this, "Added Successfully...", Toast.LENGTH_SHORT).show()
            }
            .addOnFailureListener { e->
                //failed to add
                progressDialog.dismiss()
                Toast.makeText(this, "Failed to add due to ${e.message}", Toast.LENGTH_SHORT).show()
            }
    }


}