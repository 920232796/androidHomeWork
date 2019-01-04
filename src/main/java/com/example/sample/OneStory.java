package com.example.sample;

import android.app.ActionBar;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

public class OneStory extends AppCompatActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.one_story);



        Intent intent=getIntent();
        String title = intent.getStringExtra("title");
        String content = intent.getStringExtra("content");
        Integer pic = intent.getIntExtra("pic", R.drawable.meet);
        Log.d("22222", content);

        ImageView imageView = (ImageView) findViewById(R.id.pic_one_story);
        imageView.setImageResource(pic);

        TextView textViewTitle = (TextView) findViewById(R.id.text1_title);
        textViewTitle.setText(title);

        TextView textViewContent = (TextView) findViewById(R.id.text2_content);
        textViewContent.setText(content);

        String name = intent.getStringExtra("name");// 得到名字！！


    }

    public void clickCommentBtn(View view) {


        EditText editTextComment = new EditText(this);
        editTextComment.setHint("评论.....");
        editTextComment.setWidth(800);

        Button buttonComment = new Button(this);
        buttonComment.setWidth(150);
        buttonComment.setText("确定");
        buttonComment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getApplicationContext(), "hahah",Toast.LENGTH_LONG).show();
            }
        });
//        RelativeLayout.LayoutParams layout=(RelativeLayout.LayoutParams)buttonComment.getLayoutParams();
//        layout.addRule(RelativeLayout.ALIGN_RIGHT);
//        buttonComment.setLayoutParams(layout);
        LinearLayout linearLayout = (LinearLayout) findViewById(R.id.linelayout);
        linearLayout.addView(editTextComment);
        linearLayout.addView(buttonComment);
    }


//    @Override
//    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
//
//        if (requestCode == 3) {
//            //证明是跳到了一个动态界面 所以要显示一些信息
//            Log.d("ww", data.getDataString());
//            String title = data.getStringExtra("title");
//            String content = data.getStringExtra("content");
//            Integer pic = data.getIntExtra("pic", R.drawable.meet);
//            Log.d("22222", content);
//        }
//
//    }
}
