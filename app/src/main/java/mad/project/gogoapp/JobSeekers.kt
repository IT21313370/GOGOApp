package mad.project.gogoapp

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button


class JobSeekers : Fragment() {


    @SuppressLint("MissingInflatedId")
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view =  inflater.inflate(R.layout.fragment_job_seekers, container, false)
        val button001 = view.findViewById<Button>(R.id.jobSeekerListBtn)
        button001.setOnClickListener{
            val intent = Intent(activity, ViewPublicJobListActivity::class.java)
            startActivity(intent)
        }
        return view
    }
}