package com.rio.worldweather.view.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import coil.load
import com.rio.worldweather.R
import com.rio.worldweather.model.network.new_news.Article

class NewsListAdapter(private val interaction: NewsListInteraction? = null) :
    RecyclerView.Adapter<RecyclerView.ViewHolder>(){

    private val DIFF_CALLBACK = object : DiffUtil.ItemCallback<Article>(){
        override fun areItemsTheSame(oldItem: Article, newITem: Article):Boolean {
            return oldItem.title == newITem.title
        }

        override fun areContentsTheSame(oldItem: Article, newITem: Article):Boolean {
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
            LayoutInflater.from(parent.context).inflate(R.layout.news_recycler_view_item, parent, false),
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
    fun submitList(list:List<Article>){
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
        private val interaction: NewsListInteraction?
    ): RecyclerView.ViewHolder(itemView){
        /**
         * [bind] is the method that sets the onclick listener to the itemView
         * @param item of type Article representing current dataset in the list
         */
        fun bind(item: Article) {
            var headLineString = ""
            for(character in item.title.toCharArray()){
                if(character != '\n'){
                    headLineString += character
                }
            }
            item.title = headLineString
            newsImage.load(item.urlToImage)
            headline.text = item.title
            source.text = item.source.name
            language.text = if(item.author in listOf("", " ", null)){
                "No author"
            }else{
                item.author
            }
            // load news button will redirect the application to the news webView
            loadNewsButton.setOnClickListener {
                interaction?.onLoadNewsClicked(it, item)
            }
            // on click listener for the view which will redirect it the complete weather view
            itemView.setOnClickListener {
                interaction?.onViewClicked(it, item)
            }
        }
        val newsImage: ImageView = itemView.findViewById(R.id.newsImage)
        val headline: TextView = itemView.findViewById(R.id.headLine)
        val source: TextView = itemView.findViewById(R.id.source)
        val language: TextView = itemView.findViewById(R.id.language)
        val loadNewsButton: ImageView = itemView.findViewById(R.id.loadNews)
    }

    /**
     * [NewsListInteraction] is an interface providing method that are responsive to view onClick
     */
    interface  NewsListInteraction{
        fun onLoadNewsClicked(view:View, item: Article)
        fun onViewClicked(view: View, item: Article)
    }
}