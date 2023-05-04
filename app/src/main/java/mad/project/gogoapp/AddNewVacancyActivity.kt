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


        binding.saveButton.setOnClickListener {
            val vacancyTitle = binding.addVacancyTitle.text.toString()
            val fName = binding.addVacancyFName.text.toString()
            val lName = binding.addVacancyLName.text.toString()
            val phoneNum = binding.addVacancyPhoneNum.text.toString()
            val myLocation = binding.addVacancyLocation.text.toString()
            val description = binding.addVacancyDescription.text.toString()


            databaseReference = FirebaseDatabase.getInstance().getReference("Vacancy List")

//            val key = databaseReference.push().key //generate a unique key

            val vacancy = NewVacancyData(vacancyTitle, fName, lName, phoneNum, myLocation, description)
            databaseReference.child(vacancyTitle).setValue(vacancy).addOnSuccessListener {
                binding.addVacancyTitle.text.clear()
                binding.addVacancyFName.text.clear()
                binding.addVacancyLName.text.clear()
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
