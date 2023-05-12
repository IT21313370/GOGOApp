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
                .setMessage("Are you sure you want to delete this job?")
                .setPositiveButton("Confirm"){a, d->
                    Toast.makeText(context, "Deleting...", Toast.LENGTH_SHORT).show()
                    deleteJob(model, holder)
                }
                .setNegativeButton("Cancel"){a, d->
                    a.dismiss()
                }
                .show()
        }

        //set edit job function here
        holder.editBtn.setOnClickListener {
            //confirm before deletion
            val builder = AlertDialog.Builder(context)
            builder.setTitle("Update Job Info")
                .setMessage("Are you sure you want to edit this Job")
                .setPositiveButton("Confirm"){a, d->
                    Toast.makeText(context, "Loading update View...", Toast.LENGTH_SHORT).show()
//                    val intent = Intent(context, UpdateJobDActivity::class.java)
//                    context.startActivity(intent)
                    editJob(model, holder)
                }
                .setNegativeButton("Cancel"){a, d->
                    a.dismiss()
                }
                .show()
        }

    }




    private fun editJob(model: ModelJob, holder: AdapterJob.HolderJob){
        //create alert dialog box
        val builder = AlertDialog.Builder(context)
//        builder.setTitle("Update Job Info")

        //inflate custom layout for dialog box
        val inflater = LayoutInflater.from(context)
        val view = inflater.inflate(R.layout.dialog_edit_job, null)

        //set existing job details as default values
        view.findViewById<EditText>(R.id.jobEt).setText(model.job)
        view.findViewById<EditText>(R.id.locationEt).setText(model.location)
        view.findViewById<EditText>(R.id.fNameEt).setText(model.fName)
        view.findViewById<EditText>(R.id.lNameEt).setText(model.lName)
        view.findViewById<EditText>(R.id.feeEt).setText(model.fee)
        view.findViewById<EditText>(R.id.actHrsEt).setText(model.actHrs)
        view.findViewById<EditText>(R.id.descriptionEt).setText(model.description)

        //click listener for update button
        builder.setPositiveButton("Update"){dialog,_->
            //get updated details from the input fields
            val newJobTitle = view.findViewById<EditText>(R.id.jobEt).text.toString().trim()
            val newJobLocation = view.findViewById<EditText>(R.id.locationEt).text.toString().trim()
            val newFirstName = view.findViewById<EditText>(R.id.fNameEt).text.toString().trim()
            val newLastName = view.findViewById<EditText>(R.id.lNameEt).text.toString().trim()
            val newFee = view.findViewById<EditText>(R.id.feeEt).text.toString().trim()
            val newActHrs = view.findViewById<EditText>(R.id.actHrsEt).text.toString().trim()
            val newDescription = view.findViewById<EditText>(R.id.descriptionEt).text.toString().trim()

            //update the job details in the firebase database
            val ref = FirebaseDatabase.getInstance().getReference("Job Data")
            val id = model.id
            val jobMap = hashMapOf<String,Any>(
                "job" to newJobTitle,
                "location" to newJobLocation,
                "fName" to newFirstName,
                "lName" to newLastName,
                "fee" to newFee,
                "actHrs" to newActHrs,
                "description" to newDescription
            )
            ref.child(id)
                .updateChildren(jobMap)
                .addOnSuccessListener {
                    Toast.makeText(context,"Job updated Successfully",Toast.LENGTH_SHORT).show()
                }
                .addOnFailureListener{e->
                    Toast.makeText(context,"Unable to update due to ${e.message}",Toast.LENGTH_SHORT).show()
                }
            dialog.dismiss()
        }

        //set a click listener for cancel button
        builder.setNegativeButton("Cancel"){dialog,_->
            dialog.dismiss()
        }

        //set the layout for dialog
        builder.setView(view)

        //show the dialog
        builder.show()

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
                Toast.makeText(context, "Unable to delete due to ${e.message}", Toast.LENGTH_SHORT).show()
            }
    }

    inner class  HolderJob(itemView: View): RecyclerView.ViewHolder(itemView){
        //init ui views
        var jobTv: TextView = binding.jobTv
        var deleteBtn: ImageButton = binding.deleteBtn
        var editBtn: ImageButton = binding.editBtn
    }

    override fun getFilter(): Filter {
        return object : Filter() {
            override fun performFiltering(constraint: CharSequence?): FilterResults {
                val searchText = constraint.toString().toLowerCase()
                val filterResults = FilterResults()

                if (searchText.isEmpty()) {
                    filterResults.values = filterList
                    filterResults.count = filterList.size
                } else {
                    val filteredList = ArrayList<ModelJob>()

                    for (modelJob in filterList) {
                        if (modelJob.job.toLowerCase().contains(searchText)) {
                            filteredList.add(modelJob)
                        }
                    }

                    filterResults.values = filteredList
                    filterResults.count = filteredList.size
                }

                return filterResults
            }

            override fun publishResults(constraint: CharSequence?, results: FilterResults?) {
                jobArrayList = results?.values as ArrayList<ModelJob>
                notifyDataSetChanged()
            }
        }
    }

}