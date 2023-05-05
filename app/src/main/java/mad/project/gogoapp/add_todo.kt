package mad.project.gogoapp

import android.os.Bundle
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase

class add_todo : AppCompatActivity() {
    private lateinit var time : TimePicker
    private lateinit var date : DatePicker
    private lateinit var spinner: Spinner
    private lateinit var payment : EditText
    private lateinit var title : EditText
    private lateinit var addBtn : Button
    private lateinit var mDatabaseRef: DatabaseReference

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_todo)

        time = findViewById(R.id.addTimePicker) as TimePicker
        date = findViewById(R.id.addDatePicker) as DatePicker
        spinner = findViewById(R.id.addSpinner) as Spinner
        payment = findViewById(R.id.addPayment) as EditText
        title = findViewById(R.id.addTitle) as EditText
        addBtn = findViewById(R.id.addBtn) as Button
        val city_data = arrayOf("colombo", "galle", "matara")
        mDatabaseRef = FirebaseDatabase.getInstance().getReference("todo")

        val adapter: ArrayAdapter<String> = ArrayAdapter<String>(this,android.R.layout.simple_spinner_item, city_data)
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        spinner.setAdapter(adapter)

        addBtn.setOnClickListener {
            val day = date.getDayOfMonth()
            val month = date.getMonth() + 1
            val year = date.getYear()
            if (title.text.toString()!=""){
                if (time.getCurrentHour().toString()+":"+time.getCurrentMinute().toString()!=":"){
                    if (year.toString()+"/"+month.toString()+"/"+day.toString()!="//"){
                        println(payment.toString())
                        if (payment.text.toString()!=""){
                            val upload = TodoClass(
                                title.text.toString(),
                                time.getCurrentHour().toString()+":"+time.getCurrentMinute().toString(),
                                year.toString()+"/"+month.toString()+"/"+day.toString(),
                                spinner.getSelectedItem().toString(),
                                payment.text.toString().toDouble()
                            )
                            val uploadId = mDatabaseRef.push().getKey()
                            mDatabaseRef.child(uploadId.toString()).setValue(upload)
                            title.setText("")
                            payment.setText("")
                            spinner.setSelection(0)
                            Toast.makeText(this, "Insert Successful!", Toast.LENGTH_SHORT).show()
                        }else{
                            Toast.makeText(this, "Payment Required!", Toast.LENGTH_SHORT).show()
                        }
                    }else{
                        Toast.makeText(this, "Date Required!", Toast.LENGTH_SHORT).show()
                    }
                }else{
                    Toast.makeText(this, "Time Required!", Toast.LENGTH_SHORT).show()
                }
            }else{
                Toast.makeText(this, "Title Required!", Toast.LENGTH_SHORT).show()
            }
        }

    }
}