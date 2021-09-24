package com.app.aarna.activity.order;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.DatePicker;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;
import com.app.aarna.R;
import com.app.aarna.adapter.ProductpickedListAdapter;
import com.app.aarna.dialog.AllProductListDialog;
import com.app.aarna.dialog.AlreadyPayByCustomerTypeDialogFragment;
import com.app.aarna.dialog.SelectProductDialog;
import com.app.aarna.helper.FunctionHelper;
import com.app.aarna.helper.IApiCallback;
import com.app.aarna.helper.IRecyclerClickListener;
import com.app.aarna.helper.MyInterface;
import com.app.aarna.model.orderlist.OrderListData;
import com.app.aarna.model.singledayorder.SelectedProductByOwner;
import com.app.aarna.model.singledayorder.Single_Day_Order_Place_Responce;
import com.app.aarna.restapi.ApiCall;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import retrofit2.Response;

public class AddSingleDayOrderActivity extends AppCompatActivity implements IRecyclerClickListener, MyInterface, IApiCallback {
    ProductpickedListAdapter productpickedListAdapter;
    @BindView(R.id.product_recycler)
    RecyclerView product_recycler;
    @BindView(R.id.tv_date)
    TextView tv_date;
    @BindView(R.id.tv_cus_name)
    TextView tv_cus_name;
    @BindView(R.id.tv_cus_number)
    TextView tv_cus_number;
    @BindView(R.id.tv_total)
    TextView tv_total;
    @BindView(R.id.ll_grand_total)
    LinearLayout ll_grand_total;
    SelectProductDialog selectProductDialog=new SelectProductDialog();
    private DatePicker datePicker;
    private Calendar calendar;
    private int year, month, day;
    ArrayList<SelectedProductByOwner>selectedProductByOwners=new ArrayList<>();
    String cus_id="";
    String cus_name="";
    String cus_number="";
    int total_price=0;
    String grand_total="";
    String order_date="";
    String order_type="";
    String order_from="";
    int position_data;
    String order_id="";
    int selected_pro;
    String type_build="edit";

    ArrayList<OrderListData>orderListData=new ArrayList<>();
    AlreadyPayByCustomerTypeDialogFragment alreadyPayByCustomerTypeDialogFragment= new AlreadyPayByCustomerTypeDialogFragment();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_single_day_order);
        ButterKnife.bind(this);
        setcategoryadapter();
        Intent intent=getIntent();
        order_from=intent.getStringExtra("order_type");
        if (order_from.equals("add")){
            type_build="add";
            ll_grand_total.setVisibility(View.GONE);
            cus_id=intent.getStringExtra("id");
            cus_name=intent.getStringExtra("name");
            cus_number=intent.getStringExtra("number");
            order_type=intent.getStringExtra("type");
            tv_cus_name.setText(cus_name);
            tv_cus_number.setText(cus_number);

        }else {
            type_build="edit";
            Gson gson = new Gson();
            String stringLocation = intent.getStringExtra("mylist");
            if(stringLocation != null) {
                Type type = new TypeToken<ArrayList<OrderListData>>() {
                }.getType();
                orderListData = gson.fromJson(stringLocation, type);
                position_data = Integer.parseInt(intent.getStringExtra("position"));
                cus_id = orderListData.get(position_data).getCustomerId();
                cus_name = orderListData.get(position_data).getCustomerDetail().getName();
                cus_number = orderListData.get(position_data).getCustomerDetail().getPhone();
                order_type = orderListData.get(position_data).getOrderType();
                tv_cus_name.setText(cus_name);
                tv_cus_number.setText(cus_number);
                tv_total.setText(orderListData.get(position_data).getGrandTotal());
                order_id = orderListData.get(position_data).getId();
                String date=orderListData.get(position_data).getOrderDate();
                ll_grand_total.setVisibility(View.VISIBLE);

                for (int i = 0; i < orderListData.get(position_data).getProductList().size(); i++) {
                    SelectedProductByOwner selectedProductByOwner = new SelectedProductByOwner();
                    selectedProductByOwner.setPro_id(orderListData.get(position_data).getId());
                    selectedProductByOwner.setPro_total(orderListData.get(position_data).getProductList().get(i).getTotalPrice());
                    selectedProductByOwner.setPro_price(orderListData.get(position_data).getProductList().get(i).getPrice());
                    selectedProductByOwner.setPro_qty(orderListData.get(position_data).getProductList().get(i).getQty());
                    selectedProductByOwner.setPro_image(orderListData.get(position_data).getProductList().get(i).getProductDetail().getImage());
                    selectedProductByOwner.setPro_name(orderListData.get(position_data).getProductList().get(i).getProductDetail().getName());
                    selectedProductByOwners.add(selectedProductByOwner);
                }
            }
            productpickedListAdapter.notifyDataSetChanged();
            total_price = 0;
            if (!(selectedProductByOwners.isEmpty())) {
                for (int i = 0; i < selectedProductByOwners.size(); i++) {
                    int single_price = Integer.parseInt(selectedProductByOwners.get(i).getPro_total());
                    total_price = total_price + single_price;
                }
                ll_grand_total.setVisibility(View.VISIBLE);
                grand_total = String.valueOf(total_price);
                tv_total.setText(grand_total);
            }
        }

        calendar = Calendar.getInstance();
        year = calendar.get(Calendar.YEAR);
        month = calendar.get(Calendar.MONTH);
        day = calendar.get(Calendar.DAY_OF_MONTH);
        showDate(year, month+1, day);
    }

    public void setcategoryadapter() {
        product_recycler.setLayoutManager(new LinearLayoutManager(this,LinearLayoutManager.VERTICAL,false ){
            @Override
            public boolean canScrollVertically() {
                return false;
            }
        });
        productpickedListAdapter = new ProductpickedListAdapter(this,selectedProductByOwners,this);
        product_recycler.setAdapter(productpickedListAdapter);
    }

    @OnClick(R.id.iv_add_product)
    void getadd(){
        order_from="add";
        SelectedProductByOwner selectedProductByOwner=new SelectedProductByOwner();
        FragmentManager fm = getSupportFragmentManager();
        selectProductDialog = SelectProductDialog.newInstance(AddSingleDayOrderActivity.this,"add",selectedProductByOwner);
        selectProductDialog.show(fm, "fragment_edit_name");
    }
    @Override
    public void clickListener(Object pos, Object data, Object extraData) {
        if(data.equals("delete")){
            order_from="edit";
            int position= (int) pos;
            selectedProductByOwners.remove(position);
            productpickedListAdapter.notifyDataSetChanged();
            total_price = 0;
            if (!(selectedProductByOwners.isEmpty())) {
                for (int i = 0; i < selectedProductByOwners.size(); i++) {
                    int single_price = Integer.parseInt(selectedProductByOwners.get(i).getPro_total());
                    total_price = total_price + single_price;
                }
                ll_grand_total.setVisibility(View.VISIBLE);
                grand_total = String.valueOf(total_price);
                tv_total.setText(grand_total);
            }
        }else if(data.equals("edit")){
            order_from="edit";
            int position= (int) pos;
            selected_pro=position;
            SelectedProductByOwner selectedProductByOwner=new SelectedProductByOwner();
            selectedProductByOwner=selectedProductByOwners.get(position);
            FragmentManager fm = getSupportFragmentManager();
            selectProductDialog = SelectProductDialog.newInstance(AddSingleDayOrderActivity.this,"edit",selectedProductByOwner);
            selectProductDialog.show(fm, "fragment_edit_name");
        }

    }
    @OnClick(R.id.rl_change_date)
    void getdate(){
        showDialog(999);
    }
    @Override
    public void oncheck(String name, String image, String id, String qty, String price) {
        if (qty.equals("selectpro")) {
            selectProductDialog.oncheck(id, name, image, "", "");
        } else if(order_from.equals("add")){
            if (type_build.equals("edit")){
                order_from="edit";
            }else {
                order_from="add";
            }
            String product_id = name;
            String product_name = image;
            String product_image = id;
            String product_qty = qty;
            String product_total = price;
            String product_price = String.valueOf((Integer.parseInt(product_total) / Integer.parseInt(product_qty)));
            SelectedProductByOwner pro_list = new SelectedProductByOwner();
            pro_list.setPro_id(product_id);
            pro_list.setPro_name(product_name);
            pro_list.setPro_image(product_image);
            pro_list.setPro_qty(product_qty);
            pro_list.setPro_price(product_price);
            pro_list.setPro_total(product_total);
            selectedProductByOwners.add(pro_list);
            productpickedListAdapter.notifyDataSetChanged();
            total_price = 0;
            if (!(selectedProductByOwners.isEmpty())) {
                for (int i = 0; i < selectedProductByOwners.size(); i++) {
                    int single_price = Integer.parseInt(selectedProductByOwners.get(i).getPro_total());
                    total_price = total_price + single_price;
                }
            }
            ll_grand_total.setVisibility(View.VISIBLE);
            grand_total = String.valueOf(total_price);
            tv_total.setText(grand_total);
        }else {
            String product_name = image;
            String product_image = id;
            String product_id = name;
            String product_qty = qty;
            String product_total = price;
            String product_price = String.valueOf((Integer.parseInt(product_total) / Integer.parseInt(product_qty)));
            SelectedProductByOwner pro_list=new SelectedProductByOwner();
            if (selectedProductByOwners.isEmpty()){
                if (!product_image.equals("")) {
                    //pro_list.setPro_name(product_name);
                    //pro_list.setPro_image(product_image);
                }
                pro_list.setPro_id(product_id);
                pro_list.setPro_qty(product_qty);
                pro_list.setPro_total(product_total);
                pro_list.setPro_price(product_price);
                selectedProductByOwners.add(pro_list);
                productpickedListAdapter.notifyDataSetChanged();
            }else {
                pro_list = selectedProductByOwners.get(selected_pro);
                if (!product_image.equals("")) {
                    //pro_list.setPro_name(product_name);
                    //pro_list.setPro_image(product_image);
                }
                pro_list.setPro_id(product_id);
                pro_list.setPro_qty(product_qty);
                pro_list.setPro_total(product_total);
                pro_list.setPro_price(product_price);
                selectedProductByOwners.set(selected_pro, pro_list);
                productpickedListAdapter.notifyDataSetChanged();
            }
                total_price = 0;
                if (!(selectedProductByOwners.isEmpty())) {
                    for (int i = 0; i < selectedProductByOwners.size(); i++) {
                        int single_price = Integer.parseInt(selectedProductByOwners.get(i).getPro_total());
                        total_price = total_price + single_price;
                    }
                }
                ll_grand_total.setVisibility(View.VISIBLE);
                grand_total = String.valueOf(total_price);
                tv_total.setText(grand_total);
            }
        }
    private void showDate(int year, int month, int day) {
        tv_date.setText(new StringBuilder().append(day).append("-").append(month).append("-").append(year));
        order_date= String.valueOf(new StringBuilder().append(year).append("-").append(month).append("-").append(day));
    }
    @Override
    protected Dialog onCreateDialog(int id) {
        // TODO Auto-generated method stub
        if (id == 999) {
            return new DatePickerDialog(this,
                    myDateListener, year, month, day);
        }
        return null;
    }
    private DatePickerDialog.OnDateSetListener myDateListener = new
            DatePickerDialog.OnDateSetListener() {
                @Override
                public void onDateSet(DatePicker arg0, int arg1, int arg2, int arg3) {
                    showDate(arg1, arg2+1, arg3);
                }
            };
    @OnClick(R.id.rl_save)
    public void getsave(){
        if (selectedProductByOwners.isEmpty()){
            Toast.makeText(AddSingleDayOrderActivity.this, "Please select product first", Toast.LENGTH_SHORT).show();
        }else {
            Type type = new TypeToken<List<SelectedProductByOwner>>() {
            }.getType();
            String product = new Gson().toJson(selectedProductByOwners, type);
            FunctionHelper.disable_user_Intration(this, getString(R.string.loading), getSupportFragmentManager());
            if (order_type.equals("1")){
                ApiCall.getInstance(this).singleDay_place_order(  cus_id,grand_total,order_date,product,order_id,this);
            }else {
                ApiCall.getInstance(this).multiDay_place_order(  cus_id,grand_total,order_date,product,order_id,this);
            }
        }
    }
    @Override
    public void onSuccess(Object type, Object data, Object extraData) {
        FunctionHelper.enableUserIntraction(this);
        if (type.equals("singleDay_place_order")){
            Response<Single_Day_Order_Place_Responce> response = (Response<Single_Day_Order_Place_Responce>) data;
            if (response.isSuccessful()) {
                if (response.body().getErrorCode().equals("0")) {
                    String id=response.body().getData().get(0).getId();
                    Toast.makeText(this, "" + "Order placed successfully", Toast.LENGTH_SHORT).show();
                    if (order_from.equals("edit")){
                        Intent resultIntent = new Intent();
                        setResult(RESULT_OK, resultIntent);
                        finish();
                    }else {
                        FragmentManager fm = getSupportFragmentManager();
                        alreadyPayByCustomerTypeDialogFragment = alreadyPayByCustomerTypeDialogFragment.newInstance(AddSingleDayOrderActivity.this, id, cus_id, order_type, grand_total);
                        alreadyPayByCustomerTypeDialogFragment.show(fm, "fragment_edit_name");
                    }
                }
            }
        }
    }
    @Override
    public void onFailure(Object data) {
        FunctionHelper.enableUserIntraction(this);
    }

    @Override
    public void onBackPressed() {
        Intent resultIntent = new Intent();
        setResult(RESULT_OK, resultIntent);
        finish();
    }
}