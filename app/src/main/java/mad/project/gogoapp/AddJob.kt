package mad.project.gogoapp

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button


class AddJob : Fragment() {


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view =  inflater.inflate(R.layout.fragment_add_job, container, false)
        val button = view.findViewById<Button>(R.id.add_new_job)
        button.setOnClickListener{
            val intent = Intent(activity, AddNewJobActivity::class.java)
            startActivity(intent)
        }
        return view
    }
}