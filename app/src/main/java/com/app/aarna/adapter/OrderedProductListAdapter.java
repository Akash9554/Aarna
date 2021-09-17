
package com.app.aarna.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.app.aarna.R;
import com.app.aarna.helper.IRecyclerClickListener;
import com.app.aarna.model.orderlist.ProductOrder;
import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
/**
 * For set all data in event list
 */
public class OrderedProductListAdapter extends RecyclerView.Adapter<OrderedProductListAdapter.ListViewHolder> {
    Context context;
    ArrayList<ProductOrder> productOrders;
    IRecyclerClickListener clickListener;

    public OrderedProductListAdapter(Context context,  ArrayList<ProductOrder>productOrders, IRecyclerClickListener clickListener) {
        this.context = context;
        this.productOrders=productOrders;
        this.clickListener = clickListener;
    }
    @NonNull
    @Override
    public ListViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.orderproductitemlayout, parent, false);
        return new ListViewHolder(view, clickListener);
    }
    @Override
    public void onBindViewHolder(@NonNull ListViewHolder holder, int position) {
        holder.tvProname.setText(productOrders.get(position).getProductDetail().getName());
        holder.tvprice.setText(productOrders.get(position).getPrice());
        holder.tvqty.setText(productOrders.get(position).getQty());
        holder.tvtotal.setText(productOrders.get(position).getTotalPrice());
        Glide.with(context).load(productOrders.get(position).getProductDetail().getImage()).apply(new RequestOptions()).circleCrop().into(holder.iv_product);

    }

    @Override
    public int getItemCount() {
        return productOrders.size();
    }
    class ListViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.tvProname)
        TextView tvProname;
        @BindView(R.id.tvqty)
        TextView tvqty;
        @BindView(R.id.tvprice)
        TextView tvprice;
        @BindView(R.id.tvtotal)
        TextView tvtotal;
        @BindView(R.id.iv_product)
        ImageView iv_product;
        IRecyclerClickListener clickListener;

        public ListViewHolder(@NonNull View itemView, IRecyclerClickListener clickListener) {
            super(itemView);
            this.clickListener = clickListener;
            ButterKnife.bind(this, itemView);
        }

    }
}
