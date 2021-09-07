
package com.app.aarna.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.app.aarna.R;
import com.app.aarna.helper.IRecyclerClickListener;
import com.app.aarna.model.CustomerData;
import com.app.aarna.model.DeliveryBoyData;
import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * For set all data in event list
 */
public class AddCustomerListAdapter extends RecyclerView.Adapter<AddCustomerListAdapter.ListViewHolder> {
    Context context;
    IRecyclerClickListener clickListener;
    ArrayList<CustomerData>customerData;

    public AddCustomerListAdapter(Context context, ArrayList<CustomerData>customerData, IRecyclerClickListener clickListener) {
        this.context = context;
        this.customerData=customerData;
        this.clickListener = clickListener;
    }
    @NonNull
    @Override
    public ListViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.customer_list_view, parent, false);
        return new ListViewHolder(view, clickListener);
    }
    @Override
    public void onBindViewHolder(@NonNull ListViewHolder holder, int position) {
        holder.tv_delivery_man_name.setText(customerData.get(position).getName());
        holder.tv_number.setText(customerData.get(position).getPhone());
        holder.tv_email.setText(customerData.get(position).getEmail());
        holder.tv_password.setText(customerData.get(position).getAddress());
        Glide.with(context).load(customerData.get(position).getImage()).apply(new RequestOptions()).centerCrop().into(holder.iv_delivery_man);
    }

    @Override
    public int getItemCount() {
        return customerData.size();
    }
    class ListViewHolder extends RecyclerView.ViewHolder {
       @BindView(R.id.tv_delivery_man_name)
       TextView tv_delivery_man_name;
        @BindView(R.id.tv_number)
        TextView tv_number;
        @BindView(R.id.tv_email)
        TextView tv_email;
        @BindView(R.id.tv_password)
        TextView tv_password;
        @BindView(R.id.iv_delivery_man)
        ImageView iv_delivery_man;
        IRecyclerClickListener clickListener;
        @BindView(R.id.iv_edit)
        ImageView iv_edit;
        @BindView(R.id.iv_delete)
        ImageView iv_delete;

        public ListViewHolder(@NonNull View itemView, IRecyclerClickListener clickListener) {
            super(itemView);
            this.clickListener = clickListener;
            ButterKnife.bind(this, itemView);
        }

        @OnClick(R.id.iv_edit)
        void get_click_edit() {
            clickListener.clickListener(getAdapterPosition(), "edit", "");
        }

        @OnClick(R.id.iv_delete)
        void  get_delete(){
            clickListener.clickListener(getAdapterPosition(),"delete","");
        }
    }
}
