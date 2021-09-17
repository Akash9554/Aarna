
package com.app.aarna.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.app.aarna.R;
import com.app.aarna.helper.IRecyclerClickListener;
import com.app.aarna.model.DeliveryBoyData;
import com.app.aarna.model.ProductDataList;
import com.app.aarna.model.orderlist.OrderListData;
import com.app.aarna.model.orderlist.ProductOrder;
import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * For set all data in event list
 */
public class OrderedListAdapter extends RecyclerView.Adapter<OrderedListAdapter.ListViewHolder> implements IRecyclerClickListener {
    Context context;
    IRecyclerClickListener clickListener;
    ArrayList<OrderListData>orderListData;

    public OrderedListAdapter(Context context,ArrayList<OrderListData>orderListData, IRecyclerClickListener clickListener) {
        this.context = context;
        this.orderListData=orderListData;
        this.clickListener = clickListener;
    }
    @NonNull
    @Override
    public ListViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.orderdetaillayout, parent, false);
        return new ListViewHolder(view, clickListener);
    }
    @Override
    public void onBindViewHolder(@NonNull ListViewHolder holder, int position) {
          ArrayList<ProductOrder>productOrders=new ArrayList<>();
          productOrders.addAll(orderListData.get(position).getProductList());
          setcategoryadapter(holder.product_recycler,productOrders);
          holder.tvCusName.setText(orderListData.get(position).getCustomerDetail().getName());
          holder.tvCusAdd.setText(orderListData.get(position).getCustomerDetail().getAddress());
          holder.tvCusNum.setText(orderListData.get(position).getCustomerDetail().getPhone());
          holder.tvDevBoy.setText(orderListData.get(position).getDeliveryBoyDetail().getName());
          holder.tvdevboyNum.setText(orderListData.get(position).getDeliveryBoyDetail().getPhone());
          holder.tvOrderAmount.setText(orderListData.get(position).getGrandTotal());
          holder.tvorderdate.setText(orderListData.get(position).getOrderDate());
          Glide.with(context).load(orderListData.get(position).getDeliveryBoyDetail().getImage()).apply(new RequestOptions()).circleCrop().into(holder.iv_product);

    }

    @Override
    public int getItemCount() {
        return orderListData.size();
    }

    @Override
    public void clickListener(Object pos, Object data, Object extraData) {

    }

    class ListViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.tvCusName)
        TextView tvCusName;
        @BindView(R.id.tvCusNum)
        TextView tvCusNum;
        @BindView(R.id.tvCusAdd)
        TextView tvCusAdd;
        @BindView(R.id.tvOrderAmount)
        TextView tvOrderAmount;
        @BindView(R.id.tvDevBoy)
        TextView tvDevBoy;
        @BindView(R.id.tvdevboyNum)
        TextView tvdevboyNum;
        @BindView(R.id.tvorderdate)
        TextView tvorderdate;
        @BindView(R.id.iv_product)
        ImageView iv_product;
        IRecyclerClickListener clickListener;
        @BindView(R.id.product_recycler)
        public RecyclerView product_recycler;


        public ListViewHolder(@NonNull View itemView, IRecyclerClickListener clickListener) {
            super(itemView);
            this.clickListener = clickListener;
            ButterKnife.bind(this, itemView);
        }

    }

    public void setcategoryadapter(RecyclerView recyclerView, ArrayList<ProductOrder>productOrders) {
        recyclerView.setLayoutManager(new LinearLayoutManager(context,LinearLayoutManager.VERTICAL,false ){
            @Override
            public boolean canScrollVertically() {
                return false;
            }
        });
        OrderedProductListAdapter orderedProductListAdapter = new OrderedProductListAdapter(context,productOrders,this);
        recyclerView.setAdapter(orderedProductListAdapter);
    }
    @OnClick(R.id.iv_edit)
    void getedit(){

    }

    @OnClick(R.id.iv_delete)
    void getdelete(){

    }



}
