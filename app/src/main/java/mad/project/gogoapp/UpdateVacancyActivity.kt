package mad.project.gogoapp

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import mad.project.gogoapp.databinding.ActivityUpdateVacancyBinding

class UpdateVacancyActivity : AppCompatActivity() {

    private lateinit var binding: ActivityUpdateVacancyBinding
    private lateinit var databaseReference: DatabaseReference

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityUpdateVacancyBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.updateButton.setOnClickListener {
            val referenceVacancyTitle = binding.referenceVacancyTitle.text.toString()
            val updateFName = binding.updateFName.text.toString()
            val updateLName = binding.updateLName.text.toString()
            val updatePhoneNum = binding.updatePhoneNum.text.toString()
            val updateVacancyLocation= binding.updateVacancyLocation.text.toString()
            val updateVacancyDescription = binding.updateVacancyDescription.text.toString()
            updateData(referenceVacancyTitle, updateFName, updateLName,  updatePhoneNum, updateVacancyLocation,
                updateVacancyDescription)
        }
    }

    private fun updateData(vacancyTitle: String, fName: String, lName:String, phoneNum: String, myLocation: String,
                            description: String) {
        databaseReference = FirebaseDatabase.getInstance().getReference("Vacancy List")
        val vacancy = mapOf<String, String>("fName" to fName, "lName" to lName , "phoneNum" to phoneNum, "myLocation" to myLocation,
             "description" to description)
        databaseReference.child(vacancyTitle).updateChildren(vacancy).addOnSuccessListener {
            binding.referenceVacancyTitle.text.clear()
            binding.updateFName.text.clear()
            binding.updateLName.text.clear()
            binding.updatePhoneNum.text.clear()
            binding.updateVacancyLocation.text.clear()
            binding.updateVacancyDescription.text.clear()
            Toast.makeText(this, "Updated", Toast.LENGTH_SHORT).show()
        }.addOnFailureListener {
            Toast.makeText(this, "unable to Updated", Toast.LENGTH_SHORT).show()
        }
    }


}