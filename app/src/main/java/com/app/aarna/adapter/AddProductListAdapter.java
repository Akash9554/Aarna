
package com.app.aarna.adapter;

import android.content.Context;
import android.media.Image;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.app.aarna.R;
import com.app.aarna.helper.IRecyclerClickListener;
import com.app.aarna.model.ProductDataList;
import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;

import org.w3c.dom.Text;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * For set all data in event list
 */
public class AddProductListAdapter extends RecyclerView.Adapter<AddProductListAdapter.ListViewHolder> {
    Context context;
    IRecyclerClickListener clickListener;
    ArrayList<ProductDataList>productDataLists;
    String type;

    public AddProductListAdapter(Context context,ArrayList<ProductDataList>productDataLists, String type,IRecyclerClickListener clickListener) {
        this.context = context;
        this.productDataLists=productDataLists;
        this.clickListener = clickListener;
        this.type=type;
    }
    @NonNull
    @Override
    public ListViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.product_list_view, parent, false);
        return new ListViewHolder(view, clickListener);
    }
    @Override
    public void onBindViewHolder(@NonNull ListViewHolder holder, int position) {
        if (type.equals("2")){
            holder.iv_delete.setVisibility(View.GONE);
            holder.iv_edit.setVisibility(View.GONE);
        }else {
            holder.iv_delete.setVisibility(View.VISIBLE);
            holder.iv_edit.setVisibility(View.VISIBLE);
        }
        holder.tv_product_name.setText(productDataLists.get(position).getName());
        holder.tvAmount.setText((productDataLists.get(position).getQty().toString())+" Liter");
        holder.tv_description.setText(productDataLists.get(position).getDescription());
        Glide.with(context).load(productDataLists.get(position).getProductType().getImage()).placeholder(R.drawable.place_holder).apply(new RequestOptions()).centerCrop().into(holder.iv_product_type);
        holder.tv_type.setText(productDataLists.get(position).getProductType().getName());
        Glide.with(context).load(productDataLists.get(position).getImage()).apply(new RequestOptions()).centerCrop().placeholder(R.drawable.place_holder).into(holder.iv_product);
    }

    @Override
    public int getItemCount() {
        return productDataLists.size();
    }
    class ListViewHolder extends RecyclerView.ViewHolder {
       @BindView(R.id.tv_product_name)
       TextView tv_product_name;
        @BindView(R.id.tvAmount)
        TextView tvAmount;
        @BindView(R.id.tv_description)
        TextView tv_description;
        @BindView(R.id.iv_product)
        ImageView iv_product;
        IRecyclerClickListener clickListener;
        @BindView(R.id.iv_edit)
        ImageView iv_edit;
        @BindView(R.id.iv_delete)
        ImageView iv_delete;
        @BindView(R.id.tv_type)
        TextView tv_type;
        @BindView(R.id.iv_product_type)
        ImageView iv_product_type;

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

        @OnClick(R.id.cv_product)
        void getclick() {
            clickListener.clickListener(getAdapterPosition(), "selected_product", "");
        }
    }
}
