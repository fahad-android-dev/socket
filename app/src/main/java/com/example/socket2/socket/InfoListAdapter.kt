package com.example.socket2.socket

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.socket2.R
import com.example.socket2.databinding.LvItemInfoListBinding
import com.example.socket2.interfaces.CommonInterfaceClickEvent

class InfoListAdapter(): RecyclerView.Adapter<InfoListAdapter.MyViewHolder>() {

    var arrProductList: ArrayList<UserDataModel> = ArrayList()
    var onClickEvent: CommonInterfaceClickEvent? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val binding: LvItemInfoListBinding = DataBindingUtil.inflate(
            LayoutInflater.from(parent.context),
            R.layout.lv_item_info_list,
            parent,
            false
        )
        return MyViewHolder(binding)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {

        val a = arrProductList[position]

        holder.binding.txtName.text = a.name
        holder.binding.txtAge.text = a.age
        holder.binding.txtSalary.text = a.salary
        holder.binding.txtDesignation.text = a.designation

        holder.binding.rootLayout.setOnClickListener {
            onClickEvent?.onItemClick("itemClicked",position)
        }

    }

    override fun getItemCount(): Int {
        return arrProductList.size
    }

    class MyViewHolder(var binding: LvItemInfoListBinding) :
        RecyclerView.ViewHolder(binding.root)

    @SuppressLint("NotifyDataSetChanged")
    fun setData(data: ArrayList<UserDataModel>) {
        if (data.isNullOrEmpty()) {
            arrProductList = ArrayList()
        }
        arrProductList = data
        notifyDataSetChanged()
    }
}