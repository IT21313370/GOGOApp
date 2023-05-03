//package mad.project.todolist
//
//import android.os.Bundle
//import android.text.Editable
//import android.text.TextWatcher
//import android.widget.EditText
//import android.widget.TextView
//import androidx.appcompat.app.AppCompatActivity
//import androidx.recyclerview.widget.LinearLayoutManager
//import androidx.recyclerview.widget.RecyclerView
//import com.google.firebase.database.*
//
//class List_todo : AppCompatActivity() {
//
//    private lateinit var dbref : DatabaseReference
//    private lateinit var todoRecyclerview : RecyclerView
//    private lateinit var todoArray : ArrayList<Todo>
//    private lateinit var search : EditText
//    private lateinit var list_total : TextView
//    private var total:Double = 0.0
//
//    override fun onCreate(savedInstanceState: Bundle?) {
//        super.onCreate(savedInstanceState)
//        setContentView(R.layout.activity_list)
//
//        todoRecyclerview = findViewById(R.id.recyclerview)
//        todoRecyclerview.layoutManager = LinearLayoutManager(this)
//        todoRecyclerview.setHasFixedSize(true)
//        search = findViewById(R.id.txtSearch) as EditText
//        list_total = findViewById(R.id.list_total) as TextView
//
//        todoArray = arrayListOf<Todo>()
//        getTodoData()
//
//        search.addTextChangedListener(object : TextWatcher {
//            override fun afterTextChanged(s: Editable?) {
//            }
//
//            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
//            }
//
//            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
//                filter(s.toString())
//            }
//        })
//
//    }
//
//    private fun getTodoData() {
//
//        dbref = FirebaseDatabase.getInstance().getReference("todo")
//
//        dbref.addValueEventListener(object : ValueEventListener {
//
//            override fun onDataChange(snapshot: DataSnapshot) {
//
//                if (snapshot.exists()){
//
//                    for (todoSnapshot in snapshot.children){
//
//                        val todo = Todo()
//                        todo.setId(todoSnapshot.key)
//                        todo.setTitle(todoSnapshot.child("title").getValue(String::class.java))
//                        todo.setTime(todoSnapshot.child("time").getValue(String::class.java))
//                        todo.setDate(todoSnapshot.child("date").getValue(String::class.java))
//                        todo.setLocation(todoSnapshot.child("location").getValue(String::class.java))
//                        todo.setPayment(todoSnapshot.child("payment").getValue(Double::class.java)!!)
//                        total= total+todoSnapshot.child("payment").getValue(Double::class.java)!!.toDouble()
//                        todoArray.add(todo!!)
//
//                    }
//
//                    list_total.setText("Total Price : "+total.toString())
//                    todoRecyclerview.adapter = todoAdapter(todoArray)
//
//                }
//
//            }
//
//            override fun onCancelled(error: DatabaseError) {
//                TODO("Not yet implemented")
//            }
//
//        })
//
//    }
//
//    private  fun filter(e: String) {
//
//        val filteredItem = ArrayList<Todo>()
//
//        for (item in todoArray) {
//            if (item.location!!.toLowerCase().contains(e.toLowerCase())||item.title!!.toLowerCase().contains(e.toLowerCase())) {
//                filteredItem.add(item)
//            }
//        }
//
//        todoRecyclerview.adapter = todoAdapter(filteredItem)
//    }
//}