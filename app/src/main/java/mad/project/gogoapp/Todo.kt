package mad.project.gogoapp

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button


class Todo : Fragment() {


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
//         Inflate the layout for this fragment
        val view =  inflater.inflate(R.layout.fragment_todo, container, false)
        //val button = view.findViewById<Button>(R.id.todo_insert_button)
        //val button2 = view.findViewById<Button>(R.id.todo_list_button)
//        button.setOnClickListener{
//            val intent = Intent(activity, AddTodoActivity::class.java)
//            startActivity(intent)
//        }
       // button2.setOnClickListener{
//            val intent = Intent(activity, AddTodoActivity::class.java)
//            startActivity(intent)
//        }
        return view
        }
}