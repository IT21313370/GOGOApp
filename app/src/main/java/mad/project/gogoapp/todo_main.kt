package mad.project.gogoapp

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button

class todo_main : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_todo_main)
        val add = findViewById(R.id.todo_insert_btn) as Button
        val list = findViewById(R.id.todo_list_btn) as Button

        add.setOnClickListener {
            startActivity(Intent(this, add_todo::class.java))
        }

        list.setOnClickListener {
            startActivity(Intent(this, List_todo::class.java))
        }
    }
}