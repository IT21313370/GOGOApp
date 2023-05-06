package mad.project.gogoapp

import android.widget.Filter

class FilterJob: Filter {
    //array list in which we want to search
    private var filterList: ArrayList<ModelJob>

    //adapter in which filter need to be implemented
    private var adapterJob: AdapterJob

    //constructor
    constructor(filterList: ArrayList<ModelJob>, adapterJob: AdapterJob) : super() {
        this.filterList = filterList
        this.adapterJob = adapterJob
    }

    override fun performFiltering(constraint: CharSequence?): Filter.FilterResults {
        var constraint = constraint
        val results = Filter.FilterResults()

        //value should be null and not empty
        if(constraint != null && constraint.isNotEmpty()){
            //searched value is nor null not empty


            //change to upper case, or lower case to avoid case
            constraint = constraint.toString().uppercase()
            val filteredModels:ArrayList<ModelJob> = ArrayList()
            for (i in 0 until filterList.size){
                //validate
                if (filterList[i].job.uppercase().contains(constraint)){
                    //add to filtered list
                    filteredModels.add(filterList[i])
                }
            }
            results.count = filteredModels.size
            results.values = filteredModels
        }
        else{
            //search value is either null or empty
            results.count = filterList.size
            results.values = filterList
        }
        return  results //don't miss it
    }

    override fun publishResults(constraint: CharSequence?, results: Filter.FilterResults) {
        //apply filter changes
        adapterJob.jobArrayList = results.values as ArrayList<ModelJob>

        //notify changes
        adapterJob.notifyDataSetChanged()
    }
}