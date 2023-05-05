package mad.project.gogoapp

import android.app.ProgressDialog
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.google.firebase.auth.FirebaseAuth
import mad.project.gogoapp.databinding.ActivityAddNewJobBinding

class AddNewJobListActivity : AppCompatActivity() {

    private lateinit var binding: ActivityAddNewJobBinding


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAddNewJobBinding.inflate(layoutInflater)
        setContentView(binding.root)

        //use this activity for retrieve job list
        //fire base auth need here for display email
    }
}