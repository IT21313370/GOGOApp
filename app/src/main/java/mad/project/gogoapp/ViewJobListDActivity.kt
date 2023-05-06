package mad.project.gogoapp

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle

import android.content.Intent
import android.text.Editable
import android.text.TextWatcher
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener

import mad.project.gogoapp.databinding.ActivityViewJobListDactivityBinding


class ViewJobListDActivity : AppCompatActivity() {

    private lateinit var binding: ActivityViewJobListDactivityBinding
    private lateinit var firebaseAuth: FirebaseAuth


    //array list to hold categories
    private lateinit var  jobArrayList: ArrayList<ModelJob>
    //adapter
    private lateinit var adapterJob: AdapterJob


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityViewJobListDactivityBinding.inflate(layoutInflater)
        setContentView(binding.root)

        //init firebase auth
        firebaseAuth = FirebaseAuth.getInstance()
        checkUser()
        loadCategories()

        //search
        binding.searchEt.addTextChangedListener(object : TextWatcher{
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {

            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                //called as and when user type anything
                try {
                    adapterJob.filter.filter(s)
                }
                catch (e: Exception){

                }
            }

            override fun afterTextChanged(s: Editable?) {

            }
        })



        //handle click, start add category page
        binding.addNewJobs.setOnClickListener {
            startActivity(Intent(this, AddNewJobDActivity::class.java))
        }



    }

    private fun loadCategories() {
        //init arraylist
        jobArrayList = ArrayList()

        //get all categories from firebase database... Firebase DB > Categories
        val ref = FirebaseDatabase.getInstance().getReference("Job Data")
        ref.addValueEventListener(object : ValueEventListener{
            override fun onDataChange(snapshot: DataSnapshot) {
                //clear list before starting adding data into it
                jobArrayList.clear()
                for (ds in snapshot.children){
                    //get data as model
                    val model = ds.getValue(ModelJob::class.java)

                    //add to arraylist
                    jobArrayList.add(model!!)
                }
                //setup adapter
                adapterJob = AdapterJob(this@ViewJobListDActivity, jobArrayList)
                //set adapter to recyclerview
                binding.jobRv.adapter = adapterJob
            }

            override fun onCancelled(error: DatabaseError) {

            }
        })
    }

    private fun checkUser() {
        //get current user
        val firebaseUser = firebaseAuth.currentUser
        if (firebaseUser == null){
            //not logged in, goto main screen
            startActivity(Intent(this, MainActivity::class.java))
            finish()
        }
        else{
            //logged in get and show user info
            val email = firebaseUser.email
            //set to textview of toolbar
            binding.subTitleTv.text = email
        }
    }
}