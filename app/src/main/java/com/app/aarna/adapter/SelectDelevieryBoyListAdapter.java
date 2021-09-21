
package com.app.aarna.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.app.aarna.R;
import com.app.aarna.helper.IRecyclerClickListener;
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
public class SelectDelevieryBoyListAdapter extends RecyclerView.Adapter<SelectDelevieryBoyListAdapter.ListViewHolder> {
    Context context;
    IRecyclerClickListener clickListener;
    ArrayList<DeliveryBoyData>deliveryBoyData;
    String type_text;
    public SelectDelevieryBoyListAdapter(Context context, ArrayList<DeliveryBoyData>deliveryBoyData, String type, IRecyclerClickListener clickListener) {
        this.context = context;
        this.deliveryBoyData=deliveryBoyData;
        this.clickListener = clickListener;
        this.type_text=type;
    }
    @NonNull
    @Override
    public ListViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.selectboylayout, parent, false);
        return new ListViewHolder(view, clickListener);
    }
    @Override
    public void onBindViewHolder(@NonNull ListViewHolder holder, int position) {
        if (deliveryBoyData.get(position).isChecked()){
            holder.rl_checked.setVisibility(View.VISIBLE);
        }else {
            holder.rl_checked.setVisibility(View.GONE);
        }
        holder.tv_delivery_man_name.setText(deliveryBoyData.get(position).getName());
        holder.tv_number.setText(deliveryBoyData.get(position).getPhone());
        holder.tv_email.setText(deliveryBoyData.get(position).getEmail());
        holder.tv_password.setText(deliveryBoyData.get(position).getAddress());
        Glide.with(context).load(deliveryBoyData.get(position).getImage()).apply(new RequestOptions()).centerCrop().placeholder(R.drawable.place_holder).into(holder.iv_delivery_man);
    }

    @Override
    public int getItemCount() {
        return deliveryBoyData.size();
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
        @BindView(R.id.rl_checked)
        RelativeLayout rl_checked;

        public ListViewHolder(@NonNull View itemView, IRecyclerClickListener clickListener) {
            super(itemView);
            this.clickListener = clickListener;
            ButterKnife.bind(this, itemView);
        }
        @OnClick(R.id.cv_product)
        void getclicked(){
            clickListener.clickListener(getAdapterPosition(),"selected_boy","");
        }
    }
}
