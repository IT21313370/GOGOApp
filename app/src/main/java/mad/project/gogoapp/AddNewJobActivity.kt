package mad.project.gogoapp

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import mad.project.gogoapp.databinding.ActivityAddNewJobBinding

class AddNewJobActivity : AppCompatActivity() {

    private lateinit var binding: ActivityAddNewJobBinding
    private lateinit var databaseReference: DatabaseReference

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAddNewJobBinding.inflate(layoutInflater)
        setContentView(binding.root)


        binding.saveButton.setOnClickListener {
            val jobTitle = binding.addJobTitle.text.toString()
            val fName = binding.addJobFName.text.toString()
            val lName = binding.addJobLName.text.toString()
            val fee = binding.addJobFee.text.toString()
            val activeHrs = binding.addJobActiveHrs.text.toString()
            val myLocation = binding.addJobLocation.text.toString()
            val experience = binding.addJobExperience.text.toString()
            val description = binding.addJobDescription.text.toString()


            databaseReference = FirebaseDatabase.getInstance().getReference("Job List")

//            val key = databaseReference.push().key //generate a unique key

                val jobs = NewJobData(jobTitle, fName, lName, fee, activeHrs, myLocation, experience, description)
                databaseReference.child(jobTitle).setValue(jobs).addOnSuccessListener {
                    binding.addJobTitle.text.clear()
                    binding.addJobFName.text.clear()
                    binding.addJobLName.text.clear()
                    binding.addJobFee.text.clear()
                    binding.addJobActiveHrs.text.clear()
                    binding.addJobLocation.text.clear()
                    binding.addJobExperience.text.clear()
                    binding.addJobDescription.text.clear()

                    Toast.makeText(this, "Saved successfully", Toast.LENGTH_SHORT).show()

                    val intent = Intent(this@AddNewJobActivity, MainActivity::class.java)
                    startActivity(intent)
                    finish()
                }.addOnFailureListener{
                    Toast.makeText(this, "Failed", Toast.LENGTH_SHORT).show()
                }

        }

    }
}