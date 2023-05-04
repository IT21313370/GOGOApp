package mad.project.gogoapp

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import mad.project.gogoapp.databinding.ActivityDeleteVacancyBinding

class DeleteVacancyActivity : AppCompatActivity() {

    private lateinit var binding: ActivityDeleteVacancyBinding
    private lateinit var databaseReference: DatabaseReference

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDeleteVacancyBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.deleteButton.setOnClickListener {
            val vacancyTitle = binding.deleteVacancy.text.toString()
            if (vacancyTitle.isNotEmpty()){
                deleteData(vacancyTitle)
            } else {
                Toast.makeText(this, "Please Enter Job Vacancy Title", Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun deleteData(vacancyTitle: String) {
        databaseReference = FirebaseDatabase.getInstance().getReference("Vacancy List")
        databaseReference.child(vacancyTitle).removeValue().addOnSuccessListener {
            binding.deleteVacancy.text.clear()
            Toast.makeText(this, "Deleted", Toast.LENGTH_SHORT).show()
        }.addOnFailureListener {
            Toast.makeText(this, "Unable to Delete", Toast.LENGTH_SHORT).show()
        }
    }
}