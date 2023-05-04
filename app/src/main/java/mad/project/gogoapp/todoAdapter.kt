package mad.project.gogoapp

import android.annotation.SuppressLint
import android.app.AlertDialog
import android.content.Intent
import android.system.Os.remove
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.firebase.ui.database.FirebaseRecyclerAdapter
import com.firebase.ui.database.FirebaseRecyclerOptions
import com.google.firebase.database.FirebaseDatabase

class todoAdapter(private val todoList: ArrayList<Todo>) : RecyclerView.Adapter<todoAdapter.viewholder>(){

    private val todo = todoList

    class viewholder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var edit: ImageView
        var delete: ImageView
        var time: TextView
        var date: TextView
        var location: TextView
        var payment: TextView
        var title: TextView

        init {
            time = itemView.findViewById<View>(R.id.list_time) as TextView
            date = itemView.findViewById<View>(R.id.list_date) as TextView
            location = itemView.findViewById<View>(R.id.list_location) as TextView
            payment = itemView.findViewById<View>(R.id.list_payment) as TextView
            title = itemView.findViewById<View>(R.id.list_title) as TextView
            edit = itemView.findViewById<View>(R.id.editicon) as ImageView
            delete = itemView.findViewById<View>(R.id.deleteicon) as ImageView
        }
    }

    override fun onBindViewHolder(holder: viewholder, position: Int) {
        holder.time.setText(todo[position].time)
        holder.date.setText(todo[position].date)
        holder.location.setText(todo[position].location)
        holder.payment.setText(todo[position].payment.toString())
        holder.title.setText(todo[position].title.toString())
        holder.edit.setOnClickListener(View.OnClickListener { view ->
            val i = Intent(view.context, edit_todo::class.java)
            i.putExtra("time", todo[position].time)
            i.putExtra("date", todo[position].date)
            i.putExtra("location", todo[position].location)
            i.putExtra("payment", todo[position].payment.toString())
            i.putExtra("title", todo[position].title)
            i.putExtra("id", todo[position].id.toString())
            view.context.startActivity(i)
        })
        holder.delete.setOnClickListener(View.OnClickListener {
            val builder = AlertDialog.Builder(holder.itemView.getContext())
            builder.setTitle("Delete Todo")
            builder.setMessage("Delete...?")
            builder.setPositiveButton(
                "Yes"
            ) { dialogInterface, i ->
                FirebaseDatabase.getInstance().reference.child("todo")
                    .child(todo[position].id.toString()).removeValue()
                todoList.clear()
            }
            builder.setNegativeButton(
                "No"
            ) { dialogInterface, i -> }
            builder.show()
        })
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): viewholder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.singlerow, parent, false)
        return viewholder(view)
    }

    override fun getItemCount(): Int {
        return todoList.size
    }
}
