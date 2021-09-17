
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
import com.app.aarna.model.singledayorder.SelectedProductByOwner;
import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * For set all data in event list
 */
public class ProductpickedListAdapter extends RecyclerView.Adapter<ProductpickedListAdapter.ListViewHolder> {
    Context context;
    ArrayList<SelectedProductByOwner>selectedProductByOwners;
    IRecyclerClickListener clickListener;

    public ProductpickedListAdapter(Context context, ArrayList<SelectedProductByOwner>selectedProductByOwners, IRecyclerClickListener clickListener) {
        this.context = context;
        this.selectedProductByOwners=selectedProductByOwners;
        this.clickListener = clickListener;
    }
    @NonNull
    @Override
    public ListViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.selectedproductlayout, parent, false);
        return new ListViewHolder(view, clickListener);
    }
    @Override
    public void onBindViewHolder(@NonNull ListViewHolder holder, int position) {
        holder.tv_product_name.setText(selectedProductByOwners.get(position).getPro_name());
        holder.tv_price.setText(selectedProductByOwners.get(position).getPro_price()+""+"/per piece");
        holder.tv_qty.setText(selectedProductByOwners.get(position).getPro_qty());
        holder.tv_total.setText(selectedProductByOwners.get(position).getPro_total());
        Glide.with(context).load(selectedProductByOwners.get(position).getPro_image()).apply(new RequestOptions()).circleCrop().into(holder.iv_product);
    }

    @Override
    public int getItemCount() {
        return selectedProductByOwners.size();
    }
    class ListViewHolder extends RecyclerView.ViewHolder {
       @BindView(R.id.tv_product_name)
       TextView tv_product_name;
        @BindView(R.id.tv_price)
        TextView tv_price;
        @BindView(R.id.tv_qty)
        TextView tv_qty;
        @BindView(R.id.tv_total)
        TextView tv_total;
        IRecyclerClickListener clickListener;
        @BindView(R.id.iv_delete)
        ImageView iv_delete;
        @BindView(R.id.iv_product)
        ImageView iv_product;

        public ListViewHolder(@NonNull View itemView, IRecyclerClickListener clickListener) {
            super(itemView);
            this.clickListener = clickListener;
            ButterKnife.bind(this, itemView);
        }


        @OnClick(R.id.iv_delete)
        void  get_delete(){
            clickListener.clickListener(getAdapterPosition(),"delete","");
        }

    }
}
