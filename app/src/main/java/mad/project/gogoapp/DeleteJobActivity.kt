package mad.project.gogoapp

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import mad.project.gogoapp.databinding.ActivityDeleteJobBinding

class DeleteJobActivity : AppCompatActivity() {

    private lateinit var binding: ActivityDeleteJobBinding
    private lateinit var databaseReference: DatabaseReference

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDeleteJobBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.deleteButton.setOnClickListener {
            val jobTitle = binding.deleteJob.text.toString()
            if (jobTitle.isNotEmpty()){
                deleteData(jobTitle)
            } else {
                Toast.makeText(this, "Please Enter Job Title", Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun deleteData(jobTitle: String) {
        databaseReference = FirebaseDatabase.getInstance().getReference("Job List")
        databaseReference.child(jobTitle).removeValue().addOnSuccessListener {
            binding.deleteJob.text.clear()
            Toast.makeText(this, "Deleted", Toast.LENGTH_SHORT).show()
        }.addOnFailureListener {
            Toast.makeText(this, "Unable to Delete", Toast.LENGTH_SHORT).show()
        }
    }
}