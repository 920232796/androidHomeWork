package com.example.sample;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.os.Looper;
import android.provider.MediaStore;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.Random;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class AddStory extends AppCompatActivity {


    private Context that = this;

    File file = null;
    private static final MediaType FROM_DATA = MediaType.parse("multipart/form-data");

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.add_story);

    }

    public void selectPhoto(View view) {
        // 创建Intent，用于打开手机本地图库选择图片
        Intent intent1 = new Intent(Intent.ACTION_PICK,
                android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        // 启动intent打开本地图库
        Log.d("222", "hello world11");
        startActivityForResult(intent1, 1);
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {

        if (requestCode == 1) {
            try{
                Uri uri = data.getData();
                Log.d("11", "" + uri);
                ImageView getPhoto = (ImageView)findViewById(R.id.getPhoto);
                try {
                    Bitmap  photoBmp = getThumbnail(uri, 240);
                    getPhoto.setImageBitmap(photoBmp);

                    file = uri2File(uri); // 将uri 转成 file 然后 等着上传到服务器上

                } catch (Exception e) {
                    e.printStackTrace();
                }
            } catch (Exception e) {
                Log.e("error", e.getMessage());
            }

        }
        else {
            Log.d("22", "hello world");
        }

    }

    public void submitStory(View view) {


        TextView textViewTitle = (TextView) findViewById(R.id.title_add_story);
        String titleText =  textViewTitle.getText().toString();

        TextView textViewContent = (TextView) findViewById(R.id.content_add_story);

        String contentText =  textViewContent.getText().toString();

        Log.d("wwwwww", contentText);


        String url = "http://47.100.10.8:8080/mainPage/addStory?story_title="+ titleText + "&story_content=" + contentText;
        OkHttpClient okHttpClient = new OkHttpClient();
        final Request request = new Request.Builder()
                .url(url)
                .get()//默认就是GET请求，可以不写
                .build();
        Call call = okHttpClient.newCall(request);
        call.enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                Log.d("123", "onFailure: " + e.getMessage());
                Looper.prepare();
                Toast.makeText(getApplicationContext(), e.getMessage(), Toast.LENGTH_LONG).show();
                Looper.loop();
            }
            @Override
            public void onResponse(Call call, Response response) throws IOException {

                String returnString = response.body().string();

//                Toast.makeText(getApplicationContext(), "发表成功！", Toast.LENGTH_LONG).show();
//                try {
//                    Thread.sleep(1000);
//                } catch (InterruptedException e) {
//                    e.printStackTrace();
//                }

//                finish();
                Log.d("hah", "success");

                new Thread(){
                    @Override
                    public void run() {
                        //子线程中执行
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                //Ui线程中执行
                                Toast.makeText(getApplicationContext(), "发表成功！", Toast.LENGTH_LONG).show();
                            }
                        });
                    }
                }.start();


                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                finish();
//                Intent intent1 = new Intent(that, MainActivitySample.class);
//                startActivity(intent1);




//                Looper.prepare();
//                Toast.makeText(getApplicationContext(), "发表成功！", Toast.LENGTH_LONG).show();
////                try {
////                    Thread.sleep(1000);
////                } catch (InterruptedException e) {
////                    e.printStackTrace();
////                }
////                finish();
//                Intent intent1 = new Intent(that, MainActivitySample.class);
//                startActivity(intent1);
//                Looper.loop();



//                that.fin


            }
        });





//        //d点击发表 然后 调用url  然后加上file !!
//        Random random = new Random();
//        String url = "http://47.100.10.8:8080/upload/uploadImage";
//        String typeName = "image" + random.nextInt(1000);
//        RequestBody fileBody = RequestBody.create(MediaType.parse("application/octet-stream"),file);
//        MultipartBody body = new MultipartBody.Builder()
//                .setType(FROM_DATA)
//                .addFormDataPart(typeName,"2.png",fileBody)
//                .build();
//        Request request = new Request.Builder()
//                .post(body)
//                .url(url)
//                .build();
//
//
//        OkHttpClient okHttpClient = new OkHttpClient();
//        Call call = okHttpClient.newCall(request);
//        call.enqueue(new Callback() {
//            @Override
//            public void onFailure(Call call, IOException e) {
//                Log.d("123", "onFailure: " + e.getMessage());
//                Looper.prepare();
//                Toast.makeText(getApplicationContext(), e.getMessage(), Toast.LENGTH_LONG).show();
//                Looper.loop();
//            }
//
//            @Override
//            public void onResponse(Call call, Response response) throws IOException {
//
//                String returnString = response.body().string();
//
//            }
//        });

    }



    public File uri2File(Uri uri) {
        String img_path;
        String[] proj = {MediaStore.Images.Media.DATA};
        Cursor actualimagecursor = managedQuery(uri, proj, null,
                null, null);
        if (actualimagecursor == null) {
            img_path = uri.getPath();
        } else {
            int actual_image_column_index = actualimagecursor
                    .getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
            actualimagecursor.moveToFirst();
            img_path = actualimagecursor
                    .getString(actual_image_column_index);
        }
        File file = new File(img_path);
        return file;
    }


    public  Bitmap getThumbnail(Uri uri,int size) throws FileNotFoundException, IOException {
        InputStream input = this.getContentResolver().openInputStream(uri);
        BitmapFactory.Options onlyBoundsOptions = new BitmapFactory.Options();
        onlyBoundsOptions.inJustDecodeBounds = true;
        onlyBoundsOptions.inDither=true;//optional
        onlyBoundsOptions.inPreferredConfig=Bitmap.Config.ARGB_8888;//optional
        BitmapFactory.decodeStream(input, null, onlyBoundsOptions);
        input.close();
        if ((onlyBoundsOptions.outWidth == -1) || (onlyBoundsOptions.outHeight == -1))
            return null;
        int originalSize = (onlyBoundsOptions.outHeight > onlyBoundsOptions.outWidth) ? onlyBoundsOptions.outHeight : onlyBoundsOptions.outWidth;
        double ratio = (originalSize > size) ? (originalSize / size) : 1.0;
        BitmapFactory.Options bitmapOptions = new BitmapFactory.Options();
        bitmapOptions.inSampleSize = getPowerOfTwoForSampleRatio(ratio);
        bitmapOptions.inDither=true;//optional
        bitmapOptions.inPreferredConfig=Bitmap.Config.ARGB_8888;//optional
        input = this.getContentResolver().openInputStream(uri);
        Bitmap bitmap = BitmapFactory.decodeStream(input, null, bitmapOptions);
        input.close();
        return bitmap;
    }
    private static int getPowerOfTwoForSampleRatio(double ratio){
        int k = Integer.highestOneBit((int)Math.floor(ratio));
        if(k==0) return 1;
        else return k;
    }

    public void startPhotoZoom(Uri uri) {
        Intent intent = new Intent("com.android.camera.action.CROP");
        intent.setDataAndType(uri, "image/*");
        //下面这个crop=true是设置在开启的Intent中设置显示的VIEW可裁剪
        intent.putExtra("crop", "true");
        // aspectX aspectY 是宽高的比例
        intent.putExtra("aspectX", 1);
        intent.putExtra("aspectY", 1);
        // outputX outputY 是裁剪图片宽高
        intent.putExtra("outputX", 150);
        intent.putExtra("outputY", 150);
        intent.putExtra("return-data", true);
        startActivityForResult(intent, 3);
    }
}
