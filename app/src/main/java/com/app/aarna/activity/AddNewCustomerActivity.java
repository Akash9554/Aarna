package com.app.aarna.activity;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.core.content.FileProvider;

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
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.app.aarna.R;
import com.app.aarna.constants.Constants;
import com.app.aarna.helper.FunctionHelper;
import com.app.aarna.helper.Helper;
import com.app.aarna.helper.IApiCallback;
import com.app.aarna.model.CustomerResponce;
import com.app.aarna.model.DeliveryBoyResponce;
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


public class AddNewCustomerActivity extends AppCompatActivity implements IApiCallback {
    @BindView(R.id.iv_customer)
    ImageView iv_customer;
    @BindView(R.id.et_customer_name)
    EditText et_customer_name;
    @BindView(R.id.et_customer_number)
    EditText et_customer_number;
    @BindView(R.id.et_customer_address)
    EditText et_customer_address;
    @BindView(R.id.tv_employee)
    TextView tv_employee;
    private String url="";
    public static int  PICK_IMAGE_FROM_CAMERA = 1010;
    private Uri fileUri;
    String cus_name="";
    String cus_phone="";
    String cus_address="";
    String cus_type="0";
    String cus_id="";
    String type="";

    String[] permissions = new String[]{
            Manifest.permission.CAMERA,
            Manifest.permission.WRITE_EXTERNAL_STORAGE,
            Manifest.permission.READ_EXTERNAL_STORAGE};
    
    

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_customer);
        ButterKnife.bind(this);
        Intent intent=getIntent();
         type=intent.getStringExtra("type");
        if (type.equals("2")){
            tv_employee.setText("Save Change");
            cus_id=intent.getStringExtra("cus_id");
            et_customer_address.setText(intent.getStringExtra("cus_address"));
            et_customer_number.setText(intent.getStringExtra("cus_number"));
            et_customer_name.setText(intent.getStringExtra("cus_name"));
            Glide.with(this).load(intent.getStringExtra("cus_image")).apply(new RequestOptions()).centerCrop().into(iv_customer);
        }else {
            tv_employee.setText("Save");
        }
    }

    @OnClick(R.id.iv_back)
    void get_back(){
        onBackPressed();
    }

    @OnClick(R.id.iv_customer)
    void get_picture(){
        selectImage();
    }


    private void selectImage() {
        if (checkPermissions()) {
            final CharSequence[] options = {"Take Photo", "Choose from Gallery", "Cancel"};
            AlertDialog.Builder builder = new AlertDialog.Builder(AddNewCustomerActivity.this);
            builder.setTitle("Add Photo!");
            builder.setItems(options, new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int item) {
                    if (options[item].equals("Take Photo")) {
                        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                        fileUri = getOutputMediaFileUri(AddNewCustomerActivity.this);
                        intent.putExtra(MediaStore.EXTRA_OUTPUT, fileUri);
                        startActivityForResult(intent, PICK_IMAGE_FROM_CAMERA);
                    } else if (options[item].equals("Choose from Gallery")) {
                        Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
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
            Glide.with(this).load(url).apply(new RequestOptions()).centerCrop().into(iv_customer);
        } else if (requestCode == 2 && resultCode == RESULT_OK) {
            Uri selectedImage = data.getData();
            String[] filePath = { MediaStore.Images.Media.DATA };
            Cursor c = getContentResolver().query(selectedImage,filePath, null, null, null);
            c.moveToFirst();
            int columnIndex = c.getColumnIndex(filePath[0]);
            String picturePath = c.getString(columnIndex);
            c.close();
            url = picturePath;
            Glide.with(this).load(picturePath).apply(new RequestOptions()).centerCrop().into(iv_customer);

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


    @OnClick(R.id.rl_save)
    void get_add_product(){
        if (et_customer_name.getText().toString().equals("")){
            get_toast("Please Enter Customer Name");
        }else if (et_customer_number.getText().toString().equals("")){
            get_toast("Please Enter Customer Number");
        }else if(et_customer_address.getText().toString().equals("")){
            get_toast("Please Enter Customer Address");
        }else{
            cus_name = et_customer_name.getText().toString();
            cus_phone = et_customer_number.getText().toString();
            cus_address = et_customer_address.getText().toString();
            cus_type = "0";
            addtrasactionimage();
        }
    }

    public void addtrasactionimage() {
        MultipartBody.Part imageData = null;
        if (!TextUtils.isEmpty(url)) {
            File file = new File(url);
            final RequestBody requestFile = RequestBody.create(MediaType.parse("multipart/form-data"), file);
            imageData = MultipartBody.Part.createFormData("image", file.getName(), requestFile);
        }

        RequestBody name = RequestBody.create(MediaType.parse("text/plain"), cus_name);
        RequestBody phone = RequestBody.create(MediaType.parse("text/plain"), cus_phone);
        RequestBody address = RequestBody.create(MediaType.parse("text/plain"), cus_address);
        RequestBody type = RequestBody.create(MediaType.parse("text/plain"), cus_type);
        RequestBody id = RequestBody.create(MediaType.parse("text/plain"), cus_id);
        FunctionHelper.disable_user_Intration(this, "Loading..", getSupportFragmentManager());
        ApiCall.getInstance(this).addcustomerdata(imageData, name,phone,address,type,id, this);
    }

    @Override
    public void onSuccess(Object type, Object data, Object extraData) {
        FunctionHelper.enableUserIntraction(this);
        if (type.equals("customerdata")) {
            Response<CustomerResponce> response = (Response<CustomerResponce>) data;
            if (response.isSuccessful()) {
                if (response.body().getErrorCode().equals("0")) {
                    Toast.makeText(this, "" + "Successfully Uploaded", Toast.LENGTH_SHORT).show();
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

    public void get_toast(String message){
        Toast.makeText(this, "" + message, Toast.LENGTH_SHORT).show();
    }

    
}