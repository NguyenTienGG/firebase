package com.example.b231.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.b231.EmployeeModel
import com.example.b231.R


class EmpAdapter(private val ds: ArrayList<EmployeeModel>) :
    RecyclerView.Adapter<EmpAdapter.EmpViewHolder>() {
    private lateinit var mListener: OnItemClickListener

    interface OnItemClickListener {
        fun onItemClick(position: Int)
    }

    fun setOnClickListener(clickListener: OnItemClickListener) {
        mListener = clickListener
    }


    class EmpViewHolder(item: View,clickListener: OnItemClickListener) : RecyclerView.ViewHolder(item) {
        init {
            itemView.setOnClickListener {
                clickListener.onItemClick(adapterPosition)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): EmpViewHolder {
        val itemView =
            LayoutInflater.from(parent.context).inflate(R.layout.emp_list_item, parent, false)
        return EmpViewHolder(itemView, mListener)
    }

    override fun getItemCount(): Int {
        return ds.size
    }

    override fun onBindViewHolder(holder: EmpViewHolder, position: Int) {
        holder.itemView.apply {

            findViewById<TextView>(R.id.tvEmpName).text = ds[position].empName

        }
    }
}