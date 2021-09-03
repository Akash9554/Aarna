
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

/**
 * For set all data in event list
 */
public class AddProductListAdapter extends RecyclerView.Adapter<AddProductListAdapter.ListViewHolder> {
    Context context;
    IRecyclerClickListener clickListener;
    ArrayList<ProductDataList>productDataLists;

    public AddProductListAdapter(Context context,ArrayList<ProductDataList>productDataLists, IRecyclerClickListener clickListener) {
        this.context = context;
        this.productDataLists=productDataLists;
        this.clickListener = clickListener;
    }
    @NonNull
    @Override
    public ListViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.product_list_view, parent, false);
        return new ListViewHolder(view, clickListener);
    }
    @Override
    public void onBindViewHolder(@NonNull ListViewHolder holder, int position) {
        holder.tv_product_name.setText(productDataLists.get(position).getName());
        holder.tvAmount.setText((productDataLists.get(position).getQty().toString())+" Liter");
        holder.tv_description.setText(productDataLists.get(position).getDescription());
        Glide.with(context).load(productDataLists.get(position).getImage()).apply(new RequestOptions()).centerCrop().into(holder.iv_product);
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

        public ListViewHolder(@NonNull View itemView, IRecyclerClickListener clickListener) {
            super(itemView);
            this.clickListener = clickListener;
            ButterKnife.bind(this, itemView);
        }

       /* @OnClick(R.id.cardView)
        void getclick() {
            clickListener.clickListener(getAdapterPosition(), "detail", "");
        }
   */ }
}
