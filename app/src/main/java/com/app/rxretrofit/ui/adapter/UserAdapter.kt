package com.app.rxretrofit.ui.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView

import com.app.rxretrofit.apiservices.responsebean.UserResponseBean
import com.app.rxretrofit.databinding.RowUserBinding
import com.app.rxretrofit.utils.loadImg


class UserAdapter(private val paymentList: List<UserResponseBean.DataEntity>) :
    RecyclerView.Adapter<UserAdapter.PaymentHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PaymentHolder {
        val itemBinding =
            RowUserBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return PaymentHolder(itemBinding)
    }

    override fun onBindViewHolder(holder: PaymentHolder, position: Int) {
        val paymentBean: UserResponseBean.DataEntity = paymentList[position]
        holder.bind(paymentBean)
    }

    override fun getItemCount(): Int = paymentList.size

    class PaymentHolder(private val itemBinding: RowUserBinding) :
        RecyclerView.ViewHolder(itemBinding.root) {
        fun bind(userBean: UserResponseBean.DataEntity) {
            itemBinding.tvName.text = "${userBean.first_name} ${userBean.last_name}"
            itemBinding.tvEmail.text = userBean.email
            itemBinding.ivUser.loadImg(userBean.avatar)
        }
    }

}