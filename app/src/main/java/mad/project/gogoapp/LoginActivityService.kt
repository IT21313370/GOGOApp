package mad.project.gogoapp

import android.app.ProgressDialog
import android.content.Intent
import android.graphics.drawable.ColorDrawable
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Patterns
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import mad.project.gogoapp.databinding.ActivityLoginServiceBinding
import mad.project.gogoapp.databinding.ActivitySignupServiceBinding

class LoginActivityService : AppCompatActivity() {

    private lateinit var binding: ActivityLoginServiceBinding
    private lateinit var firebaseAuth: FirebaseAuth
    //progress dialog
    private lateinit var progressDialog: ProgressDialog


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLoginServiceBinding.inflate(layoutInflater)
        setContentView(binding.root)


        firebaseAuth = FirebaseAuth.getInstance()


        //init progress dialog, will show while login user
        progressDialog = ProgressDialog(this)
        progressDialog.setTitle("Please wait")
        progressDialog.setCanceledOnTouchOutside(false)


        //handle click, not having account go to register
        binding.noAccountTv.setOnClickListener {
            startActivity(Intent(this, SignupActivityService::class.java))
        }



        //handle click, begin login
        binding.loginBtn.setOnClickListener {
            /*
            * 1) Input data
            * 2) Validate Data
            * 3) Login - firebase Auth
            * 4) Check user type - Firebase Auth
            *  If user - move to user dashboard
            *   if admin - move to admin dashboard
            * */
            validateData()
        }
        binding.forgotPassword.setOnClickListener{
            val builder = AlertDialog.Builder(this)
            val view = layoutInflater.inflate(R.layout.dialog_forgot, null)
            val userEmail = view.findViewById<EditText>(R.id.editBox)

            builder.setView(view)
            val dialog = builder.create()

            view.findViewById<Button>(R.id.btnReset).setOnClickListener{
                compareEmail(userEmail)
                dialog.dismiss()
            }
            view.findViewById<Button>(R.id.btnCancel).setOnClickListener{
                dialog.dismiss()
            }
            if (dialog.window != null){
                dialog.window!!.setBackgroundDrawable(ColorDrawable(0))
            }
            dialog.show()
        }

//        binding.signupRedirectText.setOnClickListener{
//            val signupIntent = Intent(this, SignupActivityService::class.java)
//            startActivity(signupIntent)
//        }
    }
    //outside onCreate


    private var email = ""
    private var password = ""

    private fun validateData() {
        //1) Input data
        email = binding.emailEt.text.toString().trim()
        password = binding.passwordEt.text.toString().trim()

        //2) Validate Data
        if(!Patterns.EMAIL_ADDRESS.matcher(email).matches()){
            //invalid email pattern
            Toast.makeText(this, "Invalid Email Pattern...", Toast.LENGTH_SHORT).show()
        }
        else if (password.isEmpty()){
            //empty password
            Toast.makeText(this, "Enter Password...", Toast.LENGTH_SHORT).show()

        }
        else{
            loginUser()
        }
    }

    private fun loginUser() {
        //3) Login - firebase Auth


        //show progress
        progressDialog.setMessage("Logging In...")
        progressDialog.show()

        firebaseAuth.signInWithEmailAndPassword(email, password)
            .addOnSuccessListener {
                //login success
                checkUser()
            }
            .addOnFailureListener { e->
                //failed login
                Toast.makeText(this, "Login failed due to ${e.message}", Toast.LENGTH_SHORT).show()

            }
    }

    private fun checkUser() {
        /*4) Check user type - Firebase Auth
            *  If user - move to user dashboard
            *   if admin - move to admin dashboard*/
        progressDialog.setMessage("Checking User...")

        val firebaseUser = firebaseAuth.currentUser!!

        val ref = FirebaseDatabase.getInstance().getReference("Users")
        ref.child(firebaseUser.uid)
            .addListenerForSingleValueEvent(object : ValueEventListener {

                override fun onDataChange(snapshot: DataSnapshot) {
                    progressDialog.dismiss()

                    //get user type e.g user/admin
                    val userType = snapshot.child("userType").value
                    if (userType == "user"){
                        //it's user, open user dashboard
                        startActivity(Intent(this@LoginActivityService, MainActivity::class.java))
                        finish()
                    }
                    else if (userType == "admin"){
                        //it's admin, open admin dashboard
                        startActivity(Intent(this@LoginActivityService, MainActivity::class.java))
                        finish()
                    }
                }

                override fun onCancelled(error: DatabaseError) {

                }

            })
    }


    private fun compareEmail(email: EditText){
        if(email.text.toString().isEmpty()){
            return
        }
        if(!Patterns.EMAIL_ADDRESS.matcher(email.text.toString()).matches()){
            return
        }
        firebaseAuth.sendPasswordResetEmail(email.text.toString()).addOnCompleteListener{ task ->
            if(task.isSuccessful){
                Toast.makeText(this, "Check you email", Toast.LENGTH_SHORT).show()
            }

        }
    }
}






