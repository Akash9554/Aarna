package com.app.aarna.activity;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.core.content.FileProvider;
import androidx.fragment.app.FragmentManager;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.app.aarna.R;
import com.app.aarna.constants.Constants;
import com.app.aarna.dialog.ProductListDialog;
import com.app.aarna.helper.FunctionHelper;
import com.app.aarna.helper.Helper;
import com.app.aarna.helper.IApiCallback;
import com.app.aarna.helper.MyInterface;
import com.app.aarna.model.ProductTypeData;
import com.app.aarna.restapi.ApiCall;
import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Response;

public class AddNewProductActivity extends AppCompatActivity implements MyInterface, IApiCallback {
    @BindView(R.id.tv_product_name)
    TextView tv_product_name;
    @BindView(R.id.iv_product)
    ImageView iv_product;
    @BindView(R.id.cv_product)
    CardView cv_product;
    @BindView(R.id.et_product_description)
    EditText et_product_description;
    @BindView(R.id.et_product_qty)
    EditText et_product_qty;
    @BindView(R.id.tv_employe)
    TextView tv_employe;
    String product_type_name="";
    String product_type_image="";
    String product_name="";
    String product_image="";
    String product_id="";
    String product_type_id="";
    String type="";
    @BindView(R.id.tv_employee)
    TextView tv_title;
    String description="";
    String qty="";
    @BindView(R.id.iv_product_img)
    ImageView iv_product_img;
    @BindView(R.id.et_product_name)
    EditText et_product_name;
    private String url="";
    String user_id="1";

    public static int  PICK_IMAGE_FROM_CAMERA = 1010;
    private Uri fileUri;

    String[] permissions = new String[]{
            Manifest.permission.CAMERA,
            Manifest.permission.WRITE_EXTERNAL_STORAGE,
            Manifest.permission.READ_EXTERNAL_STORAGE};





    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_new_product2);
        ButterKnife.bind(this);
        Intent intent=getIntent();
        type=intent.getStringExtra("type");
        if (type.equals("2")){
            tv_employe.setText("Save Change");
            tv_title.setText("Change Product Type");
            tv_product_name.setVisibility(View.VISIBLE);
            cv_product.setVisibility(View.VISIBLE);
            iv_product.setVisibility(View.VISIBLE);
            product_id=intent.getStringExtra("id");
            description=intent.getStringExtra("description");
            qty=intent.getStringExtra("qty");
            product_type_id=intent.getStringExtra("product_type_id");
            product_type_image=intent.getStringExtra("product_image");
            product_type_name=intent.getStringExtra("product_name");
            product_image=intent.getStringExtra("pro_iamge");
            product_name=intent.getStringExtra("pro_name");
            et_product_description.setText(description);
            et_product_qty.setText(qty);
            tv_product_name.setText(product_name);
            et_product_name.setText(product_name);
            Glide.with(this).load(product_image).apply(new RequestOptions()).centerCrop().into(iv_product_img);
            Glide.with(this).load(product_type_image).apply(new RequestOptions()).centerCrop().into(iv_product);
            et_product_description.setSelection(et_product_description. getText(). length());
            et_product_qty.setSelection(et_product_qty. getText(). length());
        }else {
            tv_product_name.setVisibility(View.GONE);
            cv_product.setVisibility(View.GONE);
            iv_product.setVisibility(View.GONE);
            tv_employe.setText("Save");
            tv_title.setText("Select Product Type");
        }

    }

    @OnClick(R.id.iv_product_img)
    void get_picture(){
        selectImage();
    }

    private void selectImage() {
        if (checkPermissions()) {
            final CharSequence[] options = {"Take Photo", "Choose from Gallery", "Cancel"};
            AlertDialog.Builder builder = new AlertDialog.Builder(AddNewProductActivity.this);
            builder.setTitle("Add Photo!");
            builder.setItems(options, new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int item) {
                    if (options[item].equals("Take Photo")) {
                        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                        fileUri = getOutputMediaFileUri(AddNewProductActivity.this);
                        intent.putExtra(MediaStore.EXTRA_OUTPUT, fileUri);
                        startActivityForResult(intent, PICK_IMAGE_FROM_CAMERA);
                    } else if (options[item].equals("Choose from Gallery")) {
                        Intent intent = new Intent(Intent.ACTION_PICK, android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                        intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
                        startActivityForResult(intent, 2);
                    } else if (options[item].equals("Cancel")) {
                        dialog.dismiss();
                    }
                }
            });
            builder.show();
        }else {

        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == PICK_IMAGE_FROM_CAMERA && resultCode == RESULT_OK) {
            url = Helper.getImagePathFromInputStreamUri(this, fileUri);
            Glide.with(this).load(url).apply(new RequestOptions()).centerCrop().into(iv_product_img);
        } else if (requestCode == 2 && resultCode == RESULT_OK) {
            Uri selectedImage = data.getData();
            String[] filePath = { MediaStore.Images.Media.DATA };
            Cursor c = getContentResolver().query(selectedImage,filePath, null, null, null);
            c.moveToFirst();
            int columnIndex = c.getColumnIndex(filePath[0]);
            String picturePath = c.getString(columnIndex);
            c.close();
            url = picturePath;
            Glide.with(this).load(picturePath).apply(new RequestOptions()).centerCrop().into(iv_product_img);
        }else if (resultCode == Activity.RESULT_CANCELED) {
        }
    }

    private boolean checkPermissions() {
        int result;
        List<String> listPermissionsNeeded = new ArrayList<>();
        for (String p : permissions) {
            result = ContextCompat.checkSelfPermission(this, p);
            if (result != PackageManager.PERMISSION_GRANTED) {
                listPermissionsNeeded.add(p);
            }
        }
        if (!listPermissionsNeeded.isEmpty()) {
            ActivityCompat.requestPermissions(this, listPermissionsNeeded.toArray(new String[listPermissionsNeeded.size()]), 10);
            return false;
        }
        return true;
    }

    public static Uri getOutputMediaFileUri(Context context) {
        Uri contentUri = FileProvider.getUriForFile(context, "app.aarna.fileprovider", getOutputMediaFile(context));
        return contentUri;
    }

    private static File getOutputMediaFile(Context context) {
        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss", Locale.getDefault()).format(new Date());
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
            File mediaStorageDir = new File(context.getExternalFilesDir(null), Constants.IMAGE_DIRECTORY_NAME);
            if (!mediaStorageDir.exists()) {
                if (!mediaStorageDir.mkdirs()) {
                    return null;
                }
            }
            return new File(mediaStorageDir.getPath() + File.separator + "IMG_" + timeStamp + ".jpg");
        } else {
            File mediaStorageDir = new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES), Constants.IMAGE_DIRECTORY_NAME);
            if (!mediaStorageDir.exists()) {
                if (!mediaStorageDir.mkdirs()) {
                    return null;
                }
            }
            File mediaFile;
            mediaFile = new File(mediaStorageDir.getPath() + File.separator + "IMG_" + timeStamp + ".jpg");
            return mediaFile;
        }
    }



    @OnClick(R.id.rl_login_btn)
    void getproducttype(){
        FragmentManager fm = getSupportFragmentManager();
        ProductListDialog editNameDialogFragment = ProductListDialog.newInstance(AddNewProductActivity.this,"1");
        editNameDialogFragment.show(fm, "fragment_edit_name");
    }

    @Override
    public void onSuccess(Object type, Object data, Object extraData) {
        FunctionHelper.enableUserIntraction(this);
        if (type.equals("product_add_edit")) {
            Response<ProductTypeData> response = (Response<ProductTypeData>) data;
            if (response.isSuccessful()) {
                if (response.body().getErrorCode().equals("0")) {
                    Toast.makeText(this, "" + response.body().getErrorMsg(), Toast.LENGTH_SHORT).show();
                    Intent resultIntent = new Intent();
                    setResult(RESULT_OK, resultIntent);
                    finish();
                }
            }
        }
    }

    @Override
    public void onFailure(Object data) {
        FunctionHelper.enableUserIntraction(this);
    }

    @Override
    public void oncheck(String name, String image,String id,String data, String type) {
        tv_product_name.setVisibility(View.VISIBLE);
        cv_product.setVisibility(View.VISIBLE);
        iv_product.setVisibility(View.VISIBLE);
        tv_product_name.setText(name);
        product_type_id=id;
        product_type_name=tv_product_name.getText().toString();
        product_type_image=image;
        Glide.with(this).load(image).apply(new RequestOptions()).centerCrop().into(iv_product);
    }


    public void setToast_message(String message){
        Toast.makeText(AddNewProductActivity.this,message,Toast.LENGTH_SHORT).show();
    }

    @OnClick(R.id.rl_save)
    void getsave(){
        addtrasactionimage();
    }

    public void addtrasactionimage() {
        if (TextUtils.isEmpty(tv_product_name.getText().toString())) {
            setToast_message("Please Select Product Type");
        } else if (TextUtils.isEmpty(et_product_description.getText().toString())) {
            setToast_message("Please Enter About Product");
        } else if (TextUtils.isEmpty(et_product_qty.getText().toString())) {
            setToast_message("Please Enter Product Quantity");
        } else {
            String pro_name = et_product_name.getText().toString();
            String pro_description = et_product_description.getText().toString();
            String pro_qty = et_product_qty.getText().toString();

            MultipartBody.Part imageData = null;
            if (!TextUtils.isEmpty(url)) {
                File file = new File(url);
                final RequestBody requestFile = RequestBody.create(MediaType.parse("multipart/form-data"), file);
                imageData = MultipartBody.Part.createFormData("image", file.getName(), requestFile);
            }
            RequestBody id = RequestBody.create(MediaType.parse("text/plain"), product_id);
            RequestBody name = RequestBody.create(MediaType.parse("text/plain"), pro_name);
            RequestBody product_type_ids = RequestBody.create(MediaType.parse("text/plain"), product_type_id);
            RequestBody product_descriptions = RequestBody.create(MediaType.parse("text/plain"), pro_description);
            RequestBody user_ids = RequestBody.create(MediaType.parse("text/plain"), user_id);
            RequestBody qtys = RequestBody.create(MediaType.parse("text/plain"), pro_qty);
            FunctionHelper.disable_user_Intration(this, "Loading..", getSupportFragmentManager());
            ApiCall.getInstance(this).product_add_edit(imageData, user_ids, id, name, product_type_ids, product_descriptions, qtys, this);
        }
    }


}