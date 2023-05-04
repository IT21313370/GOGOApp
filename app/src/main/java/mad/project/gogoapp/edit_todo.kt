package mad.project.gogoapp

import android.R.string
import android.content.Intent
import android.os.Bundle
import android.text.TextUtils.split
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import kotlin.collections.List


class edit_todo : AppCompatActivity() {

    private lateinit var time : TimePicker
    private lateinit var date : DatePicker
    private lateinit var spinner: Spinner
    private lateinit var payment : EditText
    private lateinit var title : EditText
    private lateinit var editBtn : Button
    private lateinit var mDatabaseRef: DatabaseReference
    var id: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_edit_todo)

        time = findViewById(R.id.editTimePicker) as TimePicker
        date = findViewById(R.id.editDatePicker) as DatePicker
        spinner = findViewById(R.id.editSpinner) as Spinner
        payment = findViewById(R.id.editPayment) as EditText
        title = findViewById(R.id.editTitle) as EditText
        editBtn = findViewById(R.id.editBtn) as Button
        val city_data = arrayOf("colombo", "galle", "matara")
        mDatabaseRef = FirebaseDatabase.getInstance().getReference("todo")

        val adapter: ArrayAdapter<String> = ArrayAdapter<String>(this,android.R.layout.simple_spinner_item, city_data)
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        spinner.setAdapter(adapter)

        val intent = intent
        var time_array = (intent.getStringExtra("time").toString()).split(":")
        time.setCurrentHour(time_array[0].toInt())
        time.setCurrentMinute(time_array[1].toInt())
        var date_array = (intent.getStringExtra("date").toString()).split("/")
        date.init(date_array[0].toInt(),date_array[1].toInt()-1,date_array[2].toInt(),null)
        spinner.setSelection(adapter.getPosition(intent.getStringExtra("location")))
        payment.setText(intent.getStringExtra("payment"))
        title.setText(intent.getStringExtra("title"))
        id = intent.getStringExtra("id")

        editBtn.setOnClickListener {
            val day = date.getDayOfMonth()
            val month = date.getMonth() + 1
            val year = date.getYear()
            if (title.text.toString()!="") {
                if (time.getCurrentHour().toString() + ":" + time.getCurrentMinute()
                        .toString() != ":"
                ) {
                    if (year.toString() + "/" + month.toString() + "/" + day.toString() != "//") {
                        println(payment.toString())
                        if (payment.text.toString() != "") {
                            val upload = TodoClass(
                                title.text.toString(),
                                time.getCurrentHour().toString() + ":" + time.getCurrentMinute()
                                    .toString(),
                                year.toString() + "/" + month.toString() + "/" + day.toString(),
                                spinner.getSelectedItem().toString(),
                                payment.text.toString().toDouble()
                            )
                            mDatabaseRef.child(id.toString()).setValue(upload)
                            payment.setText("")
                            spinner.setSelection(0)
                            Toast.makeText(this, "Update Successful!", Toast.LENGTH_SHORT).show()
                            startActivity(Intent(this, List_todo::class.java))
                        } else {
                            Toast.makeText(this, "Payment Required!", Toast.LENGTH_SHORT).show()
                        }
                    } else {
                        Toast.makeText(this, "Date Required!", Toast.LENGTH_SHORT).show()
                    }
                } else {
                    Toast.makeText(this, "Time Required!", Toast.LENGTH_SHORT).show()
                }
            }else{
                Toast.makeText(this, "Title Required!", Toast.LENGTH_SHORT).show()
            }
        }

    }
}