package com.e.kotlin_notesapp

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.core.os.bundleOf
import androidx.navigation.NavDirections
import androidx.navigation.Navigation
import androidx.recyclerview.widget.RecyclerView
import com.e.database.Note


class RecyclerViewAdapter(var listview:List<Note>):RecyclerView.Adapter<RecyclerViewAdapter.MyViewHolder>() {
//    private lateinit var mListener:onItemClickListener
//

//    interface onItemClickListener{
//        fun onItemClick(position:Int)
//    }

//    fun setOnItemClickListener(listener: onItemClickListener){
//        mListener=listener
//    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        var view= LayoutInflater.from(parent.context).inflate(R.layout.note_layout,parent,false)
        return MyViewHolder(view)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {

        holder.title.text = listview[position].title
        holder.description.text = listview[position].note
        holder.itemView.setOnClickListener {
            val action = HomeFragmentDirections.addNote(noteargs = listview[position])
            Navigation.findNavController(it).navigate(action)





        }
    }
    override fun getItemCount(): Int {
        return listview.size
    }

    class MyViewHolder(itemView:View): RecyclerView.ViewHolder(itemView){
        var title: TextView =itemView.findViewById(R.id.item_title)
        var description: TextView =itemView.findViewById(R.id.item_des)



    }


}