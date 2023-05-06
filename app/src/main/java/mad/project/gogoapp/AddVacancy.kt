package mad.project.gogoapp


import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button


class AddVacancy : Fragment() {


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view =  inflater.inflate(R.layout.fragment_add_vacancy, container, false)
        val button = view.findViewById<Button>(R.id.add_new_vacancy)
        val button01 = view.findViewById<Button>(R.id.updateVacancy)
        val button02 = view.findViewById<Button>(R.id.deleteVacancy)
        val button03 = view.findViewById<Button>(R.id.viewVacancy)
        button.setOnClickListener{
            val intent = Intent(activity, AddNewVacancyActivity::class.java)
            startActivity(intent)
        }
        button01.setOnClickListener {
            val intent = Intent(activity, UpdateVacancyActivity::class.java)
            startActivity(intent)
        }
        button02.setOnClickListener {
            val intent = Intent(activity, DeleteVacancyActivity::class.java)
            startActivity(intent)
        }
        button03.setOnClickListener {
            val intent = Intent(activity, ViewVacancyActivity::class.java)
            startActivity(intent)
        }
        return view
    }
}