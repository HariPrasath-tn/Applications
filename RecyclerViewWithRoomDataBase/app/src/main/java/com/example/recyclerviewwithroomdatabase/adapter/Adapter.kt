//package com.example.recyclerviewwithroomdatabase.adapter
//
//import android.view.View
//import android.widget.ListAdapter
//import androidx.recyclerview.widget.DiffUtil
//import androidx.recyclerview.widget.RecyclerView
//import com.example.recyclerviewwithroomdatabase.database.AttendanceList
//
//class Adapter:ListAdapter<AttendanceList,Adapter.ItemViewHolder>(DiffUtilCall()) {
//    class ItemViewHolder(view: View):RecyclerView.ViewHolder(view) {
//
//    }
//}
//class DiffUtilCall:DiffUtil.ItemCallback<AttendanceList>(){
//    override fun areItemsTheSame(oldItem: AttendanceList, newItem: AttendanceList): Boolean {
//       return oldItem.userID == newItem.userID
//    }
//
//    override fun areContentsTheSame(oldItem: AttendanceList, newItem: AttendanceList): Boolean {
//        return oldItem==newItem
//    }
//}