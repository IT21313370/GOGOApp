package mad.project.gogoapp

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.appcompat.app.AlertDialog
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.database.FirebaseDatabase
import mad.project.gogoapp.databinding.RowJobBinding

class AdapterJob :androidx.recyclerview.widget.RecyclerView.Adapter<AdapterJob.HolderJob>,
    Filterable {

    private var filterList: ArrayList<ModelJob>
    private val context: Context
    var jobArrayList: ArrayList<ModelJob>

    //constructor


    private lateinit var binding: RowJobBinding



    //constructor
    constructor(context: Context, jobArrayList: ArrayList<ModelJob>) {
        this.context = context
        this.jobArrayList = jobArrayList
        this.filterList = jobArrayList
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): HolderJob {
        //inflate/bind row_category.xml
        binding = RowJobBinding.inflate(LayoutInflater.from(context), parent, false)

        return HolderJob(binding.root)
    }

    override fun getItemCount(): Int {
        return jobArrayList.size //number of items in list
    }

    override fun onBindViewHolder(holder: HolderJob, position: Int) {
        //Get data , set data, handle clicks etc..

        //get data
        val model = jobArrayList[position]
        val id = model.id
        val job = model.job
        val uid = model.uid
        val timestamp = model.timestamp

//        handle item click,open job details
        holder.itemView.setOnClickListener{
//            intent with job id
            val intent = Intent(context,ViewSingleJobDetailsActivity::class.java)
            intent.putExtra("id",id)
            context.startActivity(intent)
        }

        //set data
        holder.jobTv.text = job

        //handler click, delete category
        holder.deleteBtn.setOnClickListener {
            //confirm before deletion
            val builder = AlertDialog.Builder(context)
            builder.setTitle("Delete")
                .setMessage("Are you sure you want to delete this category?")
                .setPositiveButton("Confirm"){a, d->
                    Toast.makeText(context, "Deleting...", Toast.LENGTH_SHORT).show()
                    deleteJob(model, holder)
                }
                .setNegativeButton("Cancel"){a, d->
                    a.dismiss()
                }
                .show()
        }

    }

    private fun deleteJob(model: ModelJob, holder: HolderJob) {
        //get id of category to delete
        val id = model.id
        //Firebase DB > Categories > categoryId
        val ref = FirebaseDatabase.getInstance().getReference("Job Data")
        ref.child(id)
            .removeValue()
            .addOnSuccessListener {
                Toast.makeText(context, "Deleted...", Toast.LENGTH_SHORT).show()
            }
            .addOnFailureListener { e->
                Toast.makeText(context, "Unable to dlete due to ${e.message}", Toast.LENGTH_SHORT).show()
            }
    }

    inner class  HolderJob(itemView: View): RecyclerView.ViewHolder(itemView){
        //init ui views
        var jobTv: TextView = binding.jobTv
        var deleteBtn: ImageButton = binding.deleteBtn
        var editBtn: ImageButton = binding.editBtn
    }

    override fun getFilter(): Filter {
        TODO("Not yet implemented")
    }

}