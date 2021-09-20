package com.app.aarna.helper;

import android.app.Activity;
import android.app.ActivityManager;
import android.content.ComponentName;
import android.content.Context;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.net.Uri;
import android.os.Build;
import android.os.Environment;
import android.provider.MediaStore;
import android.text.TextUtils;
import android.view.View;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;

import androidx.core.content.FileProvider;
import androidx.fragment.app.FragmentManager;

import com.app.aarna.constants.Constants;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.TimeZone;
import java.util.regex.Matcher;
import java.util.regex.Pattern;



public class Helper {

    private static CustomLoader animProgressDialog;
    public static void disable_user_Intration(Context context, String text, FragmentManager manager) {
        if (!isAppIsInBackground(context)) {
            if (animProgressDialog == null || animProgressDialog.getContext() != context) {
                try {
                    animProgressDialog = new CustomLoader();
                    animProgressDialog.show(manager, animProgressDialog.getTag());
                } catch (WindowManager.BadTokenException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    public static void enableUserIntraction(Context context) {
        if (context!=null) {
            if (!isAppIsInBackground(context)) {
                try {
                    if (animProgressDialog != null &&  animProgressDialog.isAdded()) {
                        animProgressDialog.dismiss();
                    }
                    animProgressDialog = null;
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
    }

    public static boolean isAppIsInBackground(Context context) {
        boolean isInBackground = true;
        ActivityManager am = (ActivityManager) context.getSystemService(Context.ACTIVITY_SERVICE);
        if (Build.VERSION.SDK_INT > Build.VERSION_CODES.KITKAT_WATCH) {
            List<ActivityManager.RunningAppProcessInfo> runningProcesses = am.getRunningAppProcesses();
            if (runningProcesses!=null){
                for (ActivityManager.RunningAppProcessInfo processInfo : runningProcesses) {
                    if (processInfo.importance == ActivityManager.RunningAppProcessInfo.IMPORTANCE_FOREGROUND) {
                        for (String activeProcess : processInfo.pkgList) {
                            if (activeProcess.equals(context.getPackageName())) {
                                isInBackground = false;
                            }
                        }
                    }
                }
            }
        } else {
            List<ActivityManager.RunningTaskInfo> taskInfo = am.getRunningTasks(1);
            ComponentName componentInfo = taskInfo.get(0).topActivity;
            if (componentInfo.getPackageName().equals(context.getPackageName())) {
                isInBackground = false;
            }
        }

        return isInBackground;
    }

    public final static boolean isNumberValid(String number){
        return (number.length()>=10 && number.length()<=10);

    }

    public final static boolean isNameValid(String number){
        return (number.length()>=3 && number.length()<=30);

    }

    public final static boolean isInstructionValid(String number){
        return (number.length()>=3 && number.length()<=100);

    }

    public static boolean isNameContainSpace(String name){
        char first = name.charAt(0);
        char last = name.charAt(name.length()-1);
        return (String.valueOf(first).equals(" ") || String.valueOf(last).equals(" "));
    }

    public static void showKeyboard(Context context){
        InputMethodManager imm = (InputMethodManager) context.getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.toggleSoftInput(InputMethodManager.SHOW_FORCED,0);
    }

    public static void hideKeyBoard(Context context, View editText) {
        InputMethodManager imm = (InputMethodManager) context.getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(editText.getWindowToken(), 0);
    }

   /* public static void showSnackMessage(@NonNull View view, String message) {
        try {


            Snackbar snackbar;
            snackbar = Snackbar.make(view, message, Snackbar.LENGTH_LONG);
            View snackBarView = snackbar.getView();
            snackBarView.setBackgroundColor(Color.parseColor("#000000"));
            TextView textView = (TextView) snackBarView.findViewById(R.id.snackbar_text);
            textView.setTextColor(Color.parseColor("#FFFFFF"));
            snackbar.show();


        } catch (Exception e) {
            e.printStackTrace();
        }
    }
*/
    public final static boolean isValidEmail(CharSequence target) {

        if (android.util.Patterns.EMAIL_ADDRESS.matcher(target).matches()){
            return true;
//            String providedText = String.valueOf(target);
//            String[] splittedText = providedText.split("@");
//            String afterAtRate = splittedText[1];

//            String[] splittedAfterDott = afterAtRate.split("\\.");
//            if (splittedAfterDott.length !=2){
//                return false;
//            }else {
//                return true;
//            }
        }
        return false;

    }


    public static Uri getOutputMediaFileUri(Context context) {

        Uri contentUri = FileProvider.getUriForFile(context, "app.docwin.fileprovider", getOutputMediaFile());
        return contentUri;
    }

    private static File getOutputMediaFile() {
        // External sdcard location
        File mediaStorageDir = new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES), Constants.IMAGE_DIRECTORY_NAME);

        // Create the storage directory if it does not exist
        if (!mediaStorageDir.exists()) {
            if (!mediaStorageDir.mkdirs()) {
                return null;
            }
        }
        // Create a media file name
        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss", Locale.getDefault()).format(new Date());
        File mediaFile;
        mediaFile = new File(mediaStorageDir.getPath() + File.separator + "IMG_" + timeStamp + ".jpg");

        return mediaFile;
    }

    public static String getRealPathForImagesURI(Uri contentUri, Activity context) {
        // can post image
        String[] proj={MediaStore.Images.Media.DATA};
        Cursor cursor = context.managedQuery( contentUri,
                proj, // Which columns to return
                null,       // WHERE clause; which rows to return (all rows)
                null,       // WHERE clause selection arguments (none)
                null); // Order-by clause (ascending by name)
        int column_index = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
        cursor.moveToFirst();

        if (!TextUtils.isEmpty(cursor.getString(column_index))){
            return cursor.getString(column_index);
        }else {
            return getImagePathFromInputStreamUri(context,contentUri);
        }

    }

    public static String getImagePathFromInputStreamUri(Activity context, Uri uri) {
        InputStream inputStream = null;
        String filePath = null;
        String fileName = "";

        if (uri.getAuthority() != null) {
            try {
                inputStream = context.getContentResolver().openInputStream(uri); // context needed


                String scheme = uri.getScheme();
                if (scheme.equals("file")) {
                    fileName = uri.getLastPathSegment();
                }
                else if (scheme.equals("content")) {
                    String splitableuri = String.valueOf(uri);
                    String[] fileSplit = splitableuri.split("/");

                    fileName = fileSplit[fileSplit.length-1];
                    fileName = fileName+".jpg";

                }

                File testFile = new File(context.getExternalCacheDir(),fileName);
                if (testFile.exists()){
                    testFile.delete();

                }
                File photoFile = createTemporalFileFrom(context,inputStream,fileName);

                filePath = photoFile.getPath();

            } catch (FileNotFoundException e) {
                // log
            } catch (IOException e) {
                // log
            }finally {
                try {
                    inputStream.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }

        return filePath;
    }

    public static File createTemporalFileFrom(Activity context, InputStream inputStream, String imageFileName) throws IOException {
        File targetFile = null;

        if (inputStream != null) {
            int read;
            byte[] buffer = new byte[50 * 1024];
            targetFile = createTemporalFile(context,imageFileName);
            OutputStream outputStream = new FileOutputStream(targetFile);

            while ((read = inputStream.read(buffer)) != -1) {
                outputStream.write(buffer, 0, read);
            }
            outputStream.flush();

            try {
                outputStream.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        return targetFile;
    }

    private static File createTemporalFile(Context context, String filename) {

        return new File(context.getExternalCacheDir(), filename); // context needed
    }

    public static boolean isNameContainOnlyCharacters(String text){
        Pattern ps = Pattern.compile("^[a-zA-Z ]+$");
        Matcher ms = ps.matcher(text);
        return ms.matches();
//       return true;
    }





    public static boolean isPasswordValid(String password){
        boolean upperCasePresent = false;
        boolean lowerCasePresent = false;
        boolean digitPresent = false;

        if (password.length()<8 ){
            return false;
        }else if(password.length()>20){
            return false;
        }else {
            for (int k = 0; k< password.length(); k++){
                char currentCharacter = password.charAt(k);
                if (Character.isUpperCase(currentCharacter)) {
                    upperCasePresent = true;
                } else if (Character.isLowerCase(currentCharacter)) {
                    lowerCasePresent = true;
                }else if (Character.isDigit(currentCharacter)){
                    digitPresent = true;
                }
            }

            return  upperCasePresent && lowerCasePresent && digitPresent;
        }

    }

    public static Bitmap loadBitmapFromView(View v) {
        if (v.getMeasuredHeight() <= 0) {
            v.measure(WindowManager.LayoutParams.WRAP_CONTENT, WindowManager.LayoutParams.WRAP_CONTENT);
            Bitmap b = Bitmap.createBitmap(v.getMeasuredWidth(), v.getMeasuredHeight(), Bitmap.Config.ARGB_8888);
            Canvas c = new Canvas(b);
            v.layout(0, 0, v.getMeasuredWidth(), v.getMeasuredHeight());
            v.draw(c);
            return b;
        }
        return null;
    }
    public static String changeDateformat(String date, String currentformat, String changeinformat){
        SimpleDateFormat dateFormat = new SimpleDateFormat(currentformat);
        dateFormat.setTimeZone(TimeZone.getTimeZone("UTC"));
        Date sourceDate = null;
        try {
            sourceDate = dateFormat.parse(date);
        } catch (ParseException e) {
            e.printStackTrace();
        }

        SimpleDateFormat targetFormat = new SimpleDateFormat(changeinformat);
        String targetdatevalue= targetFormat.format(sourceDate);
        return targetdatevalue;
    }


    public static String getDisplayableTime(long delta)
    {
        long difference=0;
        Long mDate = System.currentTimeMillis();

        if(mDate > delta)
        {
            difference= mDate - delta;
            final long seconds = difference/1000;
            final long minutes = seconds/60;
            final long hours = minutes/60;
            final long days = hours/24;
            final long weeks = (days / 7);
            final long months = days/31;
            final long years = days/365;

            if (seconds < 0)
            {
                return "not yet";
            }
            else if (seconds < 60)
            {
                return seconds == 1 ? "one second" : seconds + " seconds";
            }
            else if (seconds < 120)
            {
                return "a minute";
            }
            else if (seconds < 2700)
            {
                return minutes + " minutes";
            }
            else if (seconds < 5400)
            {
                return "an hour";
            }
            else if (seconds < 86400)
            {
                return hours + " hours";
            }
            else if (seconds < 172800)
            {
                return "yesterday";
            }
            else if (seconds < 2592000) // 30 * 24 * 60 * 60
            {
                if (days<7) {
                    return days + " days";
                }else {
                    return weeks +" week";

                }
            }
            else if (seconds < 31104000) // 12 * 30 * 24 * 60 * 60
            {

                return months <= 1 ? "one month" : months + " months";
            }
            else
            {
                return years <= 1 ? "one year" : years + " years";
            }
        }
        return null;
    }


    public static String timeFormatter(String providedDate, String expectedFormat, String oldFormat) {

        DateFormat formatter = new SimpleDateFormat(oldFormat, Locale.ENGLISH);

        try {

            formatter.setTimeZone(TimeZone.getTimeZone("UTC"));
            Date formattedDate = formatter.parse(providedDate);
            formatter.setTimeZone(TimeZone.getDefault());
            String cretaedDate = formatter.format(formattedDate);

            SimpleDateFormat dateFormat = new SimpleDateFormat(oldFormat, Locale.ENGLISH);
            Date date = null;
            String convertedDate = null;
            try {
                date = dateFormat.parse(cretaedDate);
                SimpleDateFormat simpleDateFormat = new SimpleDateFormat(expectedFormat);
                convertedDate = simpleDateFormat.format(date);
            } catch (Exception e) {

                e.printStackTrace();
                return convertedDate;
            }
            return  convertedDate;

        } catch (ParseException e) {
            e.printStackTrace();
            return providedDate;
        }
    }












}
