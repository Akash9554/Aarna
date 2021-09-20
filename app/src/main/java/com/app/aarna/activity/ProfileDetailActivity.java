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
import android.widget.EditText;
import android.widget.ImageView;

import com.app.aarna.R;
import com.app.aarna.constants.Constants;
import com.app.aarna.helper.FunctionHelper;
import com.app.aarna.helper.Helper;
import com.app.aarna.helper.IApiCallback;
import com.app.aarna.model.ProfileResponce;
import com.app.aarna.model.deliverylist.DeliveryBoyOrderResponce;
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
import retrofit2.Response;

public class ProfileDetailActivity extends AppCompatActivity implements IApiCallback {
    @BindView(R.id.etName)
    EditText etName;
    @BindView(R.id.etEmail)
    EditText etEmail;
    @BindView(R.id.etAddres)
    EditText etAddres;
    @BindView(R.id.etPhone)
    EditText etPhone;
    @BindView(R.id.iv_profile)
    ImageView iv_profile;
    public static int  PICK_IMAGE_FROM_CAMERA = 1010;
    private Uri fileUri;
    String url="";

    String[] permissions = new String[]{
            Manifest.permission.CAMERA,
            Manifest.permission.WRITE_EXTERNAL_STORAGE,
            Manifest.permission.READ_EXTERNAL_STORAGE};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile_detail);
        ButterKnife.bind(this);
        getprofile();
    }
    public void getprofile(){
        FunctionHelper.disable_user_Intration(this, getString(R.string.loading), getSupportFragmentManager());
        ApiCall.getInstance(this).get_profile("1", this);
    }

    @Override
    public void onSuccess(Object type, Object data, Object extraData) {
        FunctionHelper.enableUserIntraction(this);
        if (type.equals("profile")) {
            Response<ProfileResponce> response = (Response<ProfileResponce>) data;
            if (response.isSuccessful()) {
                if (response.body().getErrorCode().equals("0")) {
                    Glide.with(this).load(response.body().getData().get(0).getImage()).apply(new RequestOptions()).centerCrop().into(iv_profile);
                    etAddres.setText(response.body().getData().get(0).getAddress());
                    etName.setText(response.body().getData().get(0).getName());
                    etPhone.setText(response.body().getData().get(0).getPhone());

                }
            }
        }
    }

    @Override
    public void onFailure(Object data) {
        FunctionHelper.enableUserIntraction(this);
    }

    @OnClick(R.id.rlProfile)
    void get_profile(){
        selectImage();
    }

    private void selectImage() {
        if (checkPermissions()) {
            final CharSequence[] options = {"Take Photo", "Choose from Gallery", "Cancel"};
            AlertDialog.Builder builder = new AlertDialog.Builder(ProfileDetailActivity.this);
            builder.setTitle("Add Photo!");
            builder.setItems(options, new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int item) {
                    if (options[item].equals("Take Photo")) {
                        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                        fileUri = getOutputMediaFileUri(ProfileDetailActivity.this);
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
            Glide.with(this).load(url).apply(new RequestOptions()).centerCrop().into(iv_profile);
        } else if (requestCode == 2 && resultCode == RESULT_OK) {
            Uri selectedImage = data.getData();
            String[] filePath = { MediaStore.Images.Media.DATA };
            Cursor c = getContentResolver().query(selectedImage,filePath, null, null, null);
            c.moveToFirst();
            int columnIndex = c.getColumnIndex(filePath[0]);
            String picturePath = c.getString(columnIndex);
            c.close();
            url = picturePath;
            Glide.with(this).load(picturePath).apply(new RequestOptions()).centerCrop().into(iv_profile);
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



    @OnClick(R.id.iv_back)
    void get_back(){
        onBackPressed();
    }
}