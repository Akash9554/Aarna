
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
import com.app.aarna.model.ProductTypeDataList;
import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * For set all data in event list
 */
public class AddProductListTypeAdapter extends RecyclerView.Adapter<AddProductListTypeAdapter.ListViewHolder> {
    Context context;
    IRecyclerClickListener clickListener;
    ArrayList<ProductTypeDataList>productTypeDataLists;

    public AddProductListTypeAdapter(Context context, ArrayList<ProductTypeDataList>productTypeDataLists, IRecyclerClickListener clickListener) {
        this.context = context;
        this.productTypeDataLists=productTypeDataLists;
        this.clickListener = clickListener;
    }
    @NonNull
    @Override
    public ListViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.productalllistlayout, parent, false);
        return new ListViewHolder(view, clickListener);
    }
    @Override
    public void onBindViewHolder(@NonNull ListViewHolder holder, int position) {
        holder.tv_product_name.setText(productTypeDataLists.get(position).getName());
        Glide.with(context).load(productTypeDataLists.get(position).getImage()).apply(new RequestOptions()).centerCrop().into(holder.iv_product);


      /*  holder.tvId.setText(eventLists.get(position).getId());
        holder.tvzipcode.setText(eventLists.get(position).getZipcode());*/
    }

    @Override
    public int getItemCount() {
        return productTypeDataLists.size();
    }

    /**
     * for finding all view in adapter
     */
    class ListViewHolder extends RecyclerView.ViewHolder {
       @BindView(R.id.tv_product_name)
       TextView tv_product_name;
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
