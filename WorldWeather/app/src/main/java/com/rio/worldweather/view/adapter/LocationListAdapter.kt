package com.rio.worldweather.view.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageButton
import android.widget.TextView
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.rio.worldweather.R
import com.rio.worldweather.model.database.DatabaseLocation

class LocationListAdapter(private val interaction:LocationListInteraction? = null) :
    RecyclerView.Adapter<RecyclerView.ViewHolder>(){

    private val DIFF_CALLBACK = object : DiffUtil.ItemCallback<DatabaseLocation>(){
        override fun areItemsTheSame(oldItem:DatabaseLocation, newITem:DatabaseLocation):Boolean {
            return oldItem.cityName == newITem.cityName
        }

        override fun areContentsTheSame(oldItem:DatabaseLocation, newITem:DatabaseLocation):Boolean {
            return oldItem == newITem
        }
    }

    private val differ = AsyncListDiffer(this, DIFF_CALLBACK)

    /**
     * [onCreateViewHolder] is an override method of the class RecyclerView.Adapter
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
     * [onBindViewHolder] is an override method of the class RecyclerView.Adapter
     *
     * Methodology:
     *      -> binds the list of items with the viewHolders
     *      -> here holder's are verified for the user defined ViewHolder class
     *      as the parameter is of increased scope
     *      -> binds onClickListener to the view
     */
    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        if(holder is ViewHolder){

            holder.bind(differ.currentList[position])
        }
    }

    /**
     * [getItemCount] is an override method of the class RecyclerView.Adapter
     *
     * returns the count of the elements in the items list
     */
    override fun getItemCount(): Int = differ.currentList.size


    /**
     * [submitList] is the function that submits the list to the Differ
     */
    fun submitList(list:List<DatabaseLocation>){
        differ.submitList(list)
    }

    /**
     * class [ViewHolder] is an user defined class that extends the ViewHolder class of RecyclerView
     *  Constructor of this class takes object of the class View
     * Methodology:
     *      it binds layout components with the instance variable
     */
    class ViewHolder(
        itemView: View,
        private val interaction: LocationListInteraction?
    ): RecyclerView.ViewHolder(itemView){
        /**
         * [bind] is the method that sets the onclick listener to the itemView
         * @param item of type DatabaseLocation representing current dataset in the list
         */
        fun bind(item:DatabaseLocation) {
            cityNameTextView.text = item.cityName
            coordinateTextView.text = "(${item.lat}, ${item.lon})"


            // on click listener for the view which will redirect it the complete weather view
            itemView.setOnClickListener {
                interaction?.onViewClicked(it, item)
            }

            // onclickListener for the star button used to unStar the location (remove from the database)
            removeFromStaredItemsButton.setOnClickListener {
                interaction?.onStarClicked(absoluteAdapterPosition, item)
            }
        }
        private var cityNameTextView : TextView = itemView.findViewById(R.id.rCityName)
        private var coordinateTextView : TextView = itemView.findViewById(R.id.rCoordinates)
        var removeFromStaredItemsButton : ImageButton = itemView.findViewById(R.id.rStarButton)
    }

    /**
     * [LocationListInteraction] is an interface providing method that are responsive to view onClick
     */
    interface  LocationListInteraction{
        fun onStarClicked(position:Int, item: DatabaseLocation)
        fun onViewClicked(view:View, item: DatabaseLocation)
    }
}