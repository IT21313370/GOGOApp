package mad.project.gogoapp

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import mad.project.gogoapp.databinding.ActivityAddNewJobBinding
import mad.project.gogoapp.databinding.ActivityJobUpdateBinding
import mad.project.gogoapp.databinding.ActivityUpdateJobDactivityBinding

class UpdateJobDActivity : AppCompatActivity() {

    private lateinit var binding: ActivityUpdateJobDactivityBinding
    private lateinit var databaseReference: DatabaseReference

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityUpdateJobDactivityBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.updateButton.setOnClickListener {
            val referenceJobTitle = binding.jobEt.text.toString()
            val updateJobLocation = binding.locationEt.text.toString()
            val updateFName = binding.fNameEt.text.toString()
            val updateLName = binding.lNameEt.text.toString()
            val updateFee = binding.feeEt.text.toString()
            val updateActiveHrs = binding.actHrsEt.text.toString()
            val updateJobDescription = binding.descriptionEt.text.toString()
            updateData(referenceJobTitle, updateFName, updateLName, updateFee, updateActiveHrs, updateJobLocation,
                updateJobDescription)
        }
    }

    private fun updateData(jobTitle: String, fName: String, lName: String, fee: String, activeHrs: String, myLocation: String, description: String) {
        databaseReference = FirebaseDatabase.getInstance().getReference("Job Data")
        val jobs = mapOf<String, String>("fName" to fName, "lName" to lName, "fee" to fee, "activeHrs" to activeHrs, "myLocation" to myLocation,
            "description" to description)
        databaseReference.child(jobTitle).updateChildren(jobs).addOnSuccessListener {
            binding.jobEt.text.clear()
            binding.locationEt.text.clear()
            binding.fNameEt.text.clear()
            binding.lNameEt.text.clear()
            binding.feeEt.text.clear()
            binding.locationEt.text.clear()
            binding.actHrsEt.text.clear()
            binding.descriptionEt.text.clear()
            Toast.makeText(this, "Updated", Toast.LENGTH_SHORT).show()
        }.addOnFailureListener {
            Toast.makeText(this, "Unable to update", Toast.LENGTH_SHORT).show()
        }
    }



}