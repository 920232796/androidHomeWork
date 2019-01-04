package com.example.sample;

import android.content.Intent;
import android.graphics.drawable.AnimationDrawable;
import android.os.Bundle;
import android.os.Looper;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.view.animation.Animation;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;

import java.io.IOException;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class Login extends AppCompatActivity {


//    private Intent intent = new Intent(this, MainActivitySample.class);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login);
        LinearLayout linearLayout = findViewById(R.id.back);
        linearLayout.setBackgroundResource(R.drawable.animation_list);
        AnimationDrawable _animation = (AnimationDrawable)linearLayout.getBackground();
        _animation.setOneShot(false);

        _animation.start();//启动

    }

    public void loginBtn(View view){

//        Intent intent = new Intent(this, MainActivitySample.class);
        EditText userView = (EditText)findViewById(R.id.userIdEdit);
        EditText passwordView = (EditText)findViewById(R.id.passwordEdit);
        String userId = userView.getText().toString();
        String password = passwordView.getText().toString();
        String url = "http://47.100.10.8:8080/login/login?user_id="+ userId + "&password="+ password;
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
                JSONArray returnArray = null;
                JSONObject returnJson = null;
                String msg = null;
                if (ParseJson.getResponseRet(returnString).equals( "success")) {
                    //证明登陆成功
                    Intent intent = new Intent(getApplicationContext(), MainActivitySample.class);
                    TextView textView = (TextView) findViewById(R.id.userIdEdit);//把用户名带到主页面 然后查出来name 评论的时候用
                    intent.putExtra("username", textView.getText().toString());
                    startActivity(intent);
                    Looper.prepare();
                    Toast.makeText(getApplicationContext(), "login succeed", Toast.LENGTH_LONG).show();
                    Looper.loop();
                }
                else {
                    msg = ParseJson.getResponseMsg(returnString);
                    Looper.prepare();
                    Toast.makeText(getApplicationContext(), msg, Toast.LENGTH_LONG).show();
                    Looper.loop();
                }

            }
        });

    }
}
