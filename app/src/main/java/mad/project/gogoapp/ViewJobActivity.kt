package mad.project.gogoapp

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import mad.project.gogoapp.databinding.ActivityViewJobBinding

class ViewJobActivity : AppCompatActivity() {

    private lateinit var binding: ActivityViewJobBinding
    private lateinit var databaseReference: DatabaseReference


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityViewJobBinding.inflate(layoutInflater)
        setContentView(binding.root)


        binding.searchButton.setOnClickListener{
            val searchJob: String = binding.searchJob.text.toString()
            if (searchJob.isNotEmpty()){
                readData(searchJob)
            } else {
                Toast.makeText( this, "Please enter the phone number", Toast.LENGTH_SHORT).show()
            }
        }
        fun readData(jobTitle: String){
            databaseReference = FirebaseDatabase.getInstance().getReference("Job List")
            databaseReference.child(jobTitle).get().addOnSuccessListener {
                if (it.exists()){
                    val fName = it.child("fName").value
                    val lName = it.child("lName").value
                    val fee = it.child("fee").value
                    val activeHrs = it.child("activeHrs").value
                    val myLocation = it.child("myLocation").value
                    val experience = it.child("experience").value
                    val description = it.child("description").value
                    Toast.makeText(this, "Results Found", Toast.LENGTH_SHORT).show()
                    binding.searchJob.text.clear()
                    binding.readFName.text = fName.toString()
                    binding.readLName.text = lName.toString()
                    binding.readFee.text = fee.toString()
                    binding.readActiveHrs.text = activeHrs.toString()
                    binding.readMyLocation.text = myLocation.toString()
                    binding.readExperience.text = experience.toString()
                    binding.readDescription.text = description.toString()
                } else {
                    Toast.makeText(this, "Job does not exist", Toast.LENGTH_SHORT).show()
                }
            }.addOnFailureListener {
                Toast.makeText(this, "something went wrong", Toast.LENGTH_SHORT).show()
            }
        }


    }

    private fun readData(jobTitle: String) {
        databaseReference = FirebaseDatabase.getInstance().getReference("Job List")
        databaseReference.child(jobTitle).get().addOnSuccessListener {
            if (it.exists()){
                val fName = it.child("fName").value
                val lName = it.child("lName").value
                val fee = it.child("fee").value
                val activeHrs = it.child("activeHrs").value
                val myLocation = it.child("myLocation").value
                val experience = it.child("experience").value
                val description = it.child("description").value
                Toast.makeText(this, "Results Found", Toast.LENGTH_SHORT).show()
                binding.searchJob.text.clear()
                binding.readFName.text = fName.toString()
                binding.readLName.text = lName.toString()
                binding.readFee.text = fee.toString()
                binding.readActiveHrs.text = activeHrs.toString()
                binding.readMyLocation.text = myLocation.toString()
                binding.readExperience.text = experience.toString()
                binding.readDescription.text = description.toString()
            } else {
                Toast.makeText(this, "Job does not exist", Toast.LENGTH_SHORT).show()
            }
        }.addOnFailureListener {
            Toast.makeText(this, "something went wrong", Toast.LENGTH_SHORT).show()
        }
    }
}