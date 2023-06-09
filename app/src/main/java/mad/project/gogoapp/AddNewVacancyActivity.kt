package mad.project.gogoapp
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase

import mad.project.gogoapp.databinding.ActivityAddNewVacancyBinding

class AddNewVacancyActivity : AppCompatActivity() {

    private lateinit var binding: ActivityAddNewVacancyBinding
    private lateinit var databaseReference: DatabaseReference

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAddNewVacancyBinding.inflate(layoutInflater)
        setContentView(binding.root)

        databaseReference = FirebaseDatabase.getInstance().getReference("Vacancy List")

        binding.saveButton.setOnClickListener {
            val vacancyTitle = binding.addVacancyTitle.text.toString()
            val phoneNum = binding.addVacancyPhoneNum.text.toString()
            val myLocation = binding.addVacancyLocation.text.toString()
            val description = binding.addVacancyDescription.text.toString()

            if (vacancyTitle.isBlank()) {
                binding.addVacancyTitle.error = "Title is required"
                binding.addVacancyTitle.requestFocus()
                return@setOnClickListener
            }

            if (phoneNum.isBlank()) {
                binding.addVacancyPhoneNum.error = "Phone number is required"
                binding.addVacancyPhoneNum.requestFocus()
                return@setOnClickListener
            }

            if (myLocation.isBlank()) {
                binding.addVacancyLocation.error = "Location is required"
                binding.addVacancyLocation.requestFocus()
                return@setOnClickListener
            }

            if (description.isBlank()) {
                binding.addVacancyDescription.error = "Description is required"
                binding.addVacancyDescription.requestFocus()
                return@setOnClickListener
            }

            val vacancy = NewVacancyData(vacancyTitle,  phoneNum, myLocation, description)
            databaseReference.child(vacancyTitle).setValue(vacancy).addOnSuccessListener {
                binding.addVacancyTitle.text.clear()
                binding.addVacancyPhoneNum.text.clear()
                binding.addVacancyLocation.text.clear()
                binding.addVacancyDescription.text.clear()

                Toast.makeText(this, "Saved successfully", Toast.LENGTH_SHORT).show()

                val intent = Intent(this@AddNewVacancyActivity, MainActivity::class.java)
                startActivity(intent)
                finish()
            }.addOnFailureListener{
                Toast.makeText(this, "Failed", Toast.LENGTH_SHORT).show()
            }
        }
    }
}
