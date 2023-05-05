package mad.project.gogoapp

import android.app.ProgressDialog
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.google.firebase.auth.FirebaseAuth
import mad.project.gogoapp.databinding.ActivityAddNewJobBinding

class AddNewJobActivity02 : AppCompatActivity() {

    private lateinit var binding: ActivityAddNewJobBinding
    private lateinit var firebaseAuth: FirebaseAuth
    private lateinit var progressDialog: ProgressDialog

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAddNewJobBinding.inflate(layoutInflater)
        setContentView(binding.root)

        //use this activity for retrieve job list
        //fire base auth need here for display email
    }
}