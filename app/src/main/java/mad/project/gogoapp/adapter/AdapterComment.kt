package mad.project.gogoapp.adapter

import android.app.AlertDialog
import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.view.menu.ActionMenuItemView
import androidx.compose.material.AlertDialog
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import mad.project.gogoapp.ViewSingleJobDetailsActivity
import mad.project.gogoapp.databinding.DialogCommentAddBinding
import mad.project.gogoapp.databinding.RowCommentBinding
import mad.project.gogoapp.models.ModelComment

class AdapterComment:RecyclerView.Adapter<AdapterComment.HolderComment> {

    val context: Context


    //Array list to hold comment
    val commentArrayList: ArrayList<ModelComment>


    //view binding row_comment
    private lateinit var binding: RowCommentBinding

    private lateinit var firebaseAuth: FirebaseAuth

    //    constructor
    constructor(context: Context, commentArrayList: ArrayList<ModelComment>) {
        this.context = context
        this.commentArrayList = commentArrayList

        firebaseAuth = FirebaseAuth.getInstance()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): HolderComment {
//            bind/inflate row-comment.xml
        binding = RowCommentBinding.inflate(LayoutInflater.from(context), parent, false)

        return HolderComment(binding.root)
    }

    override fun getItemCount(): Int {
        return commentArrayList.size
    }

    override fun onBindViewHolder(holder: HolderComment, position: Int) {

//            get data,set data,handle click etc...

        val model = commentArrayList[position]

        val id = model.id
        val jobId = model.jobId
        val timestamp = model.timestamp
        val comment = model.comment
        val rating = model.rating
        val uid = model.uid

//            format timestamp
        val date = ViewSingleJobDetailsActivity.formatTimeStamp(timestamp.toLong())

//            set data

        holder.dateTv.text = date
        holder.commentTv.text = comment
        holder.ratingTv.text= rating


        loadUserDetails(model,holder)
//        handle click,show delete comment
        holder.itemView.setOnClickListener{

            if(firebaseAuth.currentUser !=null && firebaseAuth.uid == uid){
                deleteCommentDialog(model,holder)
            }

        }
    }

    private fun deleteCommentDialog(model: ModelComment, holder: HolderComment) {

        val builder = AlertDialog.Builder(context)
        builder.setTitle("Delete Comment")
            .setMessage("Are yousure you want to delete this comment?")
            .setPositiveButton("DELETE"){d,e->

                val jobId = model.jobId
                val commentId = model.id

//                delete comment
                val ref = FirebaseDatabase.getInstance().getReference("Job Data")
                ref.child(jobId).child("Comments").child(commentId)
                    .removeValue()
                    .addOnSuccessListener {

                        val context = context
                        val text = "Comment Deleted..."
                        val duration = Toast.LENGTH_SHORT

                        val toast = Toast.makeText(context, text, duration)
                        toast.show()

                    }
                    .addOnFailureListener{e->
//                        failed to delete
                        val context = context
                        val text = "Failed to delete comment due to ${e.message}"
                        val duration = Toast.LENGTH_SHORT

                        val toast = Toast.makeText(context, text, duration)
                        toast.show()

                    }

            }
            .setNegativeButton("CANCEL"){d,e->
                d.dismiss()
            }
            .show()

    }

    private fun loadUserDetails(model: ModelComment, holder: AdapterComment.HolderComment) {
        val uid = model.uid
        val ref = FirebaseDatabase.getInstance().getReference("Users")
        ref.child(uid)
            .addListenerForSingleValueEvent(object :ValueEventListener{
                override fun onDataChange(snapshot: DataSnapshot) {
//                    get name
                    val name = "${snapshot.child("name").value}"

//                    set data
                    holder.nameTv.text = name

                }

                override fun onCancelled(error: DatabaseError) {

                }
            })

    }


    inner class HolderComment(itemView: View) : RecyclerView.ViewHolder(itemView) {


        val nameTv = binding.nameTv
        val dateTv = binding.dateTv
        val ratingTv = binding.ratingTv
        val commentTv = binding.commentTv


    }
}