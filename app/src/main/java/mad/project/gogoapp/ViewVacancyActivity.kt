package mad.project.gogoapp

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import mad.project.gogoapp.databinding.ActivityViewVacancyBinding

class ViewVacancyActivity : AppCompatActivity() {

    private lateinit var binding: ActivityViewVacancyBinding
    private lateinit var databaseReference: DatabaseReference


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityViewVacancyBinding.inflate(layoutInflater)
        setContentView(binding.root)


        binding.searchButton.setOnClickListener{
            val searchVacancy: String = binding.searchVacancy.text.toString()
            if (searchVacancy.isNotEmpty()){
                readData(searchVacancy)
            } else {
                Toast.makeText( this, "Please enter ", Toast.LENGTH_SHORT).show()
            }
        }
        fun readData(vacancyTitle: String){
            databaseReference = FirebaseDatabase.getInstance().getReference("Vacancy List")
            databaseReference.child(vacancyTitle).get().addOnSuccessListener {
                if (it.exists()){
                    val vacancyTitle = it.child("vacancyTitle").value
                    val phoneNum = it.child("phoneNum").value
                    val myLocation = it.child("myLocation").value
                    val description = it.child("description").value
                    Toast.makeText(this, "Results Found", Toast.LENGTH_SHORT).show()
                    binding.searchVacancy.text.clear()
                    binding.readVacancy.text = vacancyTitle.toString()
                    binding.readPhoneNum.text = phoneNum.toString()
                    binding.readMyLocation.text = myLocation.toString()
                    binding.readDescription.text = description.toString()
                } else {
                    Toast.makeText(this, "Vacancy does not exist", Toast.LENGTH_SHORT).show()
                }
            }.addOnFailureListener {
                Toast.makeText(this, "something went wrong", Toast.LENGTH_SHORT).show()
            }
        }


    }

    private fun readData(vacancyTitle: String) {
        databaseReference = FirebaseDatabase.getInstance().getReference("Vacancy List")
        databaseReference.child(vacancyTitle).get().addOnSuccessListener {
            if (it.exists()){
                val vacancyTitle = it.child("vacancyTitle").value
                val phoneNum = it.child("phoneNum").value
                val myLocation = it.child("myLocation").value
                val description = it.child("description").value
                Toast.makeText(this, "Results Found", Toast.LENGTH_SHORT).show()
                binding.searchVacancy.text.clear()
                binding.readVacancy.text = vacancyTitle.toString()
                binding.readPhoneNum.text = phoneNum.toString()
                binding.readMyLocation.text = myLocation.toString()
                binding.readDescription.text = description.toString()
            } else {
                Toast.makeText(this, " Vacancy does not exist", Toast.LENGTH_SHORT).show()
            }
        }.addOnFailureListener {
            Toast.makeText(this, "something went wrong", Toast.LENGTH_SHORT).show()
        }
    }
}