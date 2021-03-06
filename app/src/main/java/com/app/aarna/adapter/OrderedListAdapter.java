
package com.app.aarna.adapter;

import android.content.Context;
import android.media.Image;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
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

public class OrderedListAdapter extends RecyclerView.Adapter<OrderedListAdapter.ListViewHolder> implements IRecyclerClickListener {
    Context context;
    IRecyclerClickListener clickListener;
    ArrayList<OrderListData>orderListData;
    String order_type;

    public OrderedListAdapter(Context context,ArrayList<OrderListData>orderListData,String type, IRecyclerClickListener clickListener) {
        this.context = context;
        this.orderListData=orderListData;
        this.clickListener = clickListener;
        this.order_type=type;
    }

    @NonNull
    @Override
    public ListViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.orderdetaillayout, parent, false);
        return new ListViewHolder(view, clickListener);
    }

    @Override
    public void onBindViewHolder(@NonNull ListViewHolder holder, int position) {
        if (order_type.equals("1")){
            if (orderListData.get(position).getStatus().equals("0")){
                holder.cv_product.setVisibility(View.VISIBLE);
                holder.iv_delete.setVisibility(View.VISIBLE);
                holder.iv_edit.setVisibility(View.VISIBLE);
            }else if (orderListData.get(position).getStatus().equals("1")){
                holder.cv_product.setVisibility(View.GONE);
            }
        }else {
            if (orderListData.get(position).getStatus().equals("0")){
                holder.cv_product.setVisibility(View.GONE);
            }else if (orderListData.get(position).getStatus().equals("1")){
                holder.cv_product.setVisibility(View.VISIBLE);
                holder.iv_delete.setVisibility(View.GONE);
                holder.iv_edit.setVisibility(View.GONE);
            }
        }
        if ((orderListData.get(position).getStatus().equals("0"))||(orderListData.get(position).getStatus().equals("1"))){
            ArrayList<ProductOrder> productOrders = new ArrayList<>();
            productOrders.addAll(orderListData.get(position).getProductList());
            setcategoryadapter(holder.product_recycler, productOrders);
            holder.tvCusName.setText(orderListData.get(position).getCustomerDetail().getName());
            holder.tvCusAdd.setText(orderListData.get(position).getCustomerDetail().getAddress());
            holder.tvCusNum.setText(orderListData.get(position).getCustomerDetail().getPhone());
            holder.tvOrderAmount.setText(orderListData.get(position).getGrandTotal());
            holder.tvorderdate.setText(orderListData.get(position).getOrderDate());
            if (orderListData.get(position).getDeliveryBoyDetail()==null){
                holder.iv_product.setVisibility(View.GONE);
                holder.rl_assigned.setVisibility(View.GONE);
                holder.rl_order_data.setVisibility(View.GONE);
                holder.rl_boy_number.setVisibility(View.GONE);
                holder.tv_change_deleviry_text.setText("Assign Delivery Boy");
            }else {
                holder.iv_product.setVisibility(View.VISIBLE);
                holder.rl_assigned.setVisibility(View.VISIBLE);
                holder.rl_order_data.setVisibility(View.VISIBLE);
                holder.rl_boy_number.setVisibility(View.VISIBLE);
                holder.tv_change_deleviry_text.setText("Change Delivery Boy");
                holder.tvDevBoy.setText(orderListData.get(position).getDeliveryBoyDetail().getName());
                holder.tvdevboyNum.setText(orderListData.get(position).getDeliveryBoyDetail().getPhone());
                Glide.with(context).load(orderListData.get(position).getDeliveryBoyDetail().getImage()).placeholder(R.drawable.place_holder).apply(new RequestOptions()).circleCrop().into(holder.iv_product);
            }
        }else {
            holder.cv_product.setVisibility(View.GONE);
        }
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
        @BindView(R.id.cv_product)
        CardView cv_product;
        @BindView(R.id.iv_delete)
        ImageView iv_delete;
        @BindView(R.id.iv_edit)
        ImageView iv_edit;
        @BindView(R.id.rl_assigned)
        RelativeLayout rl_assigned;
        @BindView(R.id.rl_boy_number)
        RelativeLayout rl_boy_number;
        @BindView(R.id.rl_order_data)
        RelativeLayout rl_order_data;
        @BindView(R.id.rl_change_delivery_boy)
        RelativeLayout rl_change_delivery_boy;
        @BindView(R.id.tv_change_deleviry_text)
        TextView tv_change_deleviry_text;




        public ListViewHolder(@NonNull View itemView, IRecyclerClickListener clickListener) {
            super(itemView);
            this.clickListener = clickListener;
            ButterKnife.bind(this, itemView);
        }

        @OnClick(R.id.iv_edit)
        void edit(){
            clickListener.clickListener(getAdapterPosition(),"edit","");
        }

        @OnClick(R.id.iv_delete)
        void getdelete(){
            clickListener.clickListener(getAdapterPosition(),"delete","");
        }

        @OnClick(R.id.rl_change_delivery_boy)
        void getchange_delivery(){
            clickListener.clickListener(getAdapterPosition(),"change_delivery_boy","");
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




}
