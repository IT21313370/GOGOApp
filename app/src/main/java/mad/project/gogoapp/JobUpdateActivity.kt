package mad.project.gogoapp

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import mad.project.gogoapp.databinding.ActivityAddNewJobBinding
import mad.project.gogoapp.databinding.ActivityJobUpdateBinding

class JobUpdateActivity : AppCompatActivity() {

    private lateinit var binding: ActivityJobUpdateBinding
    private lateinit var databaseReference: DatabaseReference

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityJobUpdateBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.updateButton.setOnClickListener {
            val referenceJobTitle = binding.referenceJobTitle.text.toString()
            val updateFName = binding.updateFName.text.toString()
            val updateLName = binding.updateLName.text.toString()
            val updateJobFee = binding.updateJobFee.text.toString()
            val updateActiveHrs = binding.updateActiveHrs.text.toString()
            val updateJobLocation= binding.updateJobLocation.text.toString()
            val updateJobExperience = binding.updateJobExperience.text.toString()
            val updateJobDescription = binding.updateJobDescription.text.toString()
            updateData(referenceJobTitle, updateFName, updateLName, updateJobFee, updateActiveHrs, updateJobLocation, updateJobExperience,
            updateJobDescription)
        }
    }

    private fun updateData(jobTitle: String, fName: String, lName:String, fee: String, activeHrs: String, myLocation: String,
    experience: String, description: String) {
        databaseReference = FirebaseDatabase.getInstance().getReference("Job List")
        val jobs = mapOf<String, String>("fName" to fName, "lName" to lName, "fee" to fee, "activeHrs" to activeHrs, "myLocation" to myLocation,
        "experience" to experience, "description" to description)
        databaseReference.child(jobTitle).updateChildren(jobs).addOnSuccessListener {
            binding.referenceJobTitle.text.clear()
            binding.updateFName.text.clear()
            binding.updateLName.text.clear()
            binding.updateJobFee.text.clear()
            binding.updateActiveHrs.text.clear()
            binding.updateJobLocation.text.clear()
            binding.updateJobExperience.text.clear()
            binding.updateJobDescription.text.clear()
            Toast.makeText(this, "Updated", Toast.LENGTH_SHORT).show()
        }.addOnFailureListener {
            Toast.makeText(this, "unable to Updated", Toast.LENGTH_SHORT).show()
        }
    }


}