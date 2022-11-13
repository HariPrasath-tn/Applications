package com.example.retrofit.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import coil.load
import coil.transform.CircleCropTransformation
import com.example.retrofit.R
import com.example.retrofit.database.AnimeCharacter

/**
 *  ViewAdapter is an user defined adapter class that extends RecyclerView's Adapter class
 *
 *  info:
 *      Creates and bind the item view for the recycler view and return it to the Recyclerview
 *      with the layout we designed and provided
 *
 *  Methodology:
 *      -> it get the list of elements from the the Activity/Fragment and assign it to the
 *      instance variable items
 *      -> then it counts the number of items in the list (i.e items --> instance variable)
 *      -> Then it creates view for for the list of items(using View holder class) and return it for binding
 *      -> once the list creation is completed the values in the list of the elements are binded and
 *      returned to the recycler view
 *
 */
class ViewAdapter(private val interaction:Interaction? = null) :
RecyclerView.Adapter<RecyclerView.ViewHolder>(){

    private val DIFF_CALLBACK = object : DiffUtil.ItemCallback<AnimeCharacter>(){
        override fun areItemsTheSame(oldItem:AnimeCharacter, newITem:AnimeCharacter):Boolean {
            return oldItem.name == newITem.name
        }

        override fun areContentsTheSame(oldItem:AnimeCharacter, newITem:AnimeCharacter):Boolean {
            return oldItem == newITem
        }
    }

    private val differ = AsyncListDiffer(this, DIFF_CALLBACK)

    /**
     * onCreate is an override method of the class RecyclerView.Adapter
     *
     * Methodology:
     *      creates the instance of the viewHolder class
     *      finally ==> returns the object of the class ViewHolder
     *
     */
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return ViewHolder(
            LayoutInflater.from(parent.context).inflate(R.layout.recycler_view_item, parent, false),
            interaction
        )
    }

    /**
     * onBindViewHolder is an override method of the class RecyclerView.Adapter
     *
     * Methodology:
     *      -> binds the list of items with the viewHolders
     *      -> here holder's are verified for the user defined ViewHolder class
     *      as the parameter is of increased scope
     *      -> binds onClickListener to the view
     */
    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        if(holder is ViewHolder){
            holder.textView.text = differ.currentList[position].name
            holder.imageView.load(differ.currentList[position].image){
                transformations(CircleCropTransformation())
            }
            holder.bind(differ.currentList[position])
        }
    }

    /**
     * getItemCount is an override method of the class RecyclerView.Adapter
     *
     * returns the count of the elements in the items list
     */
    override fun getItemCount(): Int = differ.currentList.size


    /**
     * [submitList] is the function that submits the list to the Differ
     */
    fun submitList(list:List<AnimeCharacter>){
        differ.submitList(list)
    }

    /**
     * class ViewHolder is an user defined class that extends the ViewHolder class of RecyclerView
     *  Constructor of this class takes object of the class View
     * Methodology:
     *      it binds layout components with the instance variable
     */
    class ViewHolder(
        itemView: View,
        private val interaction: Interaction?
    ): RecyclerView.ViewHolder(itemView){
        /**
         * [bind] is the method that sets the onclick listener to the itemView
         * @param item of type AnimeCharacter representing current dataset in the list
         */
        fun bind(item:AnimeCharacter) {
            itemView.setOnClickListener {
                interaction?.onItemSelected(adapterPosition, item)
            }
        }
        val textView:TextView = itemView.findViewById(R.id.myTextView)
        val imageView:ImageView = itemView.findViewById(R.id.myImageView)
    }

    /**
     * [Interaction] is an interface providing method that are responsive to view onClick
     */
    interface  Interaction{
        fun onItemSelected(position:Int, item:AnimeCharacter)
    }
}