package com.example.recyclerviewandroid.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import androidx.cardview.widget.CardView
import androidx.compose.ui.layout.Layout
import androidx.core.content.ContextCompat
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.recyclerview.widget.RecyclerView
import com.example.recyclerviewandroid.R
import com.example.recyclerviewandroid.ViewDataClass

class ViewAdapter(private val context: Context, private val items:ArrayList<ViewDataClass>) :
RecyclerView.Adapter<RecyclerView.ViewHolder>(){
    companion object{
        const val VIEW_TYPE_ONE = 1
        const val VIEW_TYPE_TWO = 2
    }
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        if(viewType == VIEW_TYPE_ONE) {
            return ViewHolder(
                LayoutInflater.from(context).inflate(
                    R.layout.recycler_view_item,
                    parent,
                    false
                )
            )
        }
        return AnotherViewHolder(
            LayoutInflater.from(context).inflate(
                R.layout.recycler_view_item2,
                parent,
                false
            )
        )
    }

    override fun getItemViewType(position: Int): Int {
        return items[position].viewType
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val item = items[position]
        if(holder is ViewHolder) {
            holder.tvItem.text = item.text

            if (position % 2 == 0) {
                holder.cardViewItem.setBackgroundColor(
                    ContextCompat.getColor(
                        context,
                        R.color.lightGray
                    )
                )
            } else {
                holder.cardViewItem.setBackgroundColor(
                    ContextCompat.getColor(
                        context,
                        R.color.white
                    )
                )
            }
        }else if(holder is AnotherViewHolder){
            holder.anotherTvItem.text = item.text

            if (position % 2 == 0) {
                holder.anotherCardViewItem.setBackgroundColor(
                    ContextCompat.getColor(
                        context,
                        R.color.lightGray
                    )
                )
            } else {
                holder.anotherCardViewItem.setBackgroundColor(
                    ContextCompat.getColor(
                        context,
                        R.color.white
                    )
                )
            }
        }
    }

    override fun getItemCount(): Int = items.size

    class ViewHolder(view: View):RecyclerView.ViewHolder(view){
        val tvItem: TextView = view.findViewById(R.id.tv_item_name)
        val cardViewItem: CardView = view.findViewById(R.id.myCardView)
    }

    class AnotherViewHolder(view: View):RecyclerView.ViewHolder(view){
        val anotherTvItem: TextView = view.findViewById(R.id.anotherTextView)
        val anotherCardViewItem: CardView = view.findViewById(R.id.myAnotherCarView)
    }
}