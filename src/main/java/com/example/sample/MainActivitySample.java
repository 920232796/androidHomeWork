package com.example.sample;

import android.app.Person;
import android.content.Intent;
import android.database.Cursor;
import android.os.Handler;
import android.os.Looper;
import android.app.Fragment;
import android.renderscript.Sampler;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.ArrayMap;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.Toast;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;


import java.io.File;
import java.io.IOException;
import java.util.AbstractList;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class MainActivitySample extends AppCompatActivity implements OnItemClickListener, AbsListView.OnScrollListener {

    private ListView listView;

    private ArrayAdapter<String> arrayAdapter;

    String name = "xzh";

//    private String[]arr_data = {"mooc", "xzh", "ahhaha"};

    private List<String> arr_data = new ArrayList<>();

    private SimpleAdapter simpleAdapter;

    private List<Map<String, Object>> dataList;

    private SwipeRefreshLayout swiperereshlayout ;

    public void addStory(View view) {

//        File file = new File("res/drawable/my1.png");
//        File file = new File("../../my1.png");
//        Log.d("wwwwww", "" + file);
//        finish();
        Intent intent1 = new Intent(this, AddStory.class);
        startActivity(intent1);
    }


    public void toMy(View view) {

        Intent intent1 = new Intent(this, My.class);
       startActivity(intent1);
    }


    private void initView (){

        swiperereshlayout = (SwipeRefreshLayout) findViewById(R.id.swiperereshlayout);
        listView = (ListView) findViewById(R.id.listView);
        dataList = new ArrayList<Map<String, Object>>();
        dataList = getData();
//        simpleAdapter = new SimpleAdapter(this,dataList,R.layout.item,new String[]{ "pic","text1", "text2" }, new int[]{R.id.pic, R.id.text1, R.id.text2} );
        simpleAdapter = new SimpleAdapter(this,dataList,R.layout.item,new String[]{ "pic","text1", "text2" }, new int[]{R.id.pic, R.id.text1, R.id.text2} );
        listView.setAdapter(simpleAdapter);

        //给listView 设置点击事件！
        listView.setOnItemClickListener(this);


        try {
            Thread.sleep(1500);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        simpleAdapter.notifyDataSetChanged();

        swiperereshlayout.setColorSchemeResources(android.R.color.holo_blue_bright,
                android.R.color.holo_green_light, android.R.color.holo_orange_light);
        //给swipeRefreshLayout绑定刷新监听
        swiperereshlayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                //设置2秒的时间来执行以下事件
                new Handler().postDelayed(new Runnable() {
                    public void run() {
                        dataList.clear();
                        String url = "http://47.100.10.8:8080/mainPage/getStory";
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


//                                Toast.makeText(getApplicationContext(), e.getMessage(), Toast.LENGTH_LONG).show();
                                Toast.makeText(getApplicationContext(), e.getMessage(), Toast.LENGTH_LONG).show();



                                Looper.loop();
                            }

                            @Override
                            public void onResponse(Call call, Response response) throws IOException {

                                String returnString = response.body().string();
                                JSONArray returnArray = null;
                                JSONObject returnJson = null;
                                String msg = null;
                                if (ParseJson.getResponseRet(returnString).equals("success")) {
                                    //证明请求成功
                                    returnArray = ParseJson.getResponseArrays(returnString);
                                    for (Object each : returnArray) {
                                        Map<String, Object> map = new HashMap<String, Object>();
                                        Random random = new Random();
                                        int randomInt = random.nextInt(7);
                                        Integer pic = R.drawable.my1;
                                        if (randomInt == 0) {
                                            pic = R.drawable.my1;
                                        }
                                        if (randomInt == 1){
                                            pic = R.drawable.my2;
                                        }
                                        if (randomInt == 2){
                                            pic = R.drawable.my3;
                                        }
                                        if (randomInt == 3) {
                                            pic = R.drawable.my4;
                                        }
                                        if (randomInt == 4) {
                                            pic = R.drawable.my5;
                                        }
                                        if (randomInt == 5) {
                                            pic = R.drawable.my6;
                                        }
                                        if (randomInt == 6) {
                                            pic = R.drawable.my7;
                                        }
                                        map.put("pic", pic);
                                        map.put("text1", ((JSONObject)each).getString("story_title"));
                                        String content = ((JSONObject)each).getString("story_content");
                                        if (content.length() > 50) {
                                            String content1 = content.substring(0, 50);
//                                            map.put("text2", content1 + ".......");
                                             map.put("text2", content);
                                        }
                                        else {
                                            map.put("text2", content);
                                        }
                                        dataList.add(map);
                                        Log.d("22",((JSONObject)each).getString("story_title"));
                                        Log.d("223", returnString);
                                        Log.d("33", dataList + "");
                                    }



                                    Looper.prepare();


//                                    Toast.makeText(getApplicationContext(), "succeed", Toast.LENGTH_LONG).show();
                                    Toast.makeText(getApplicationContext(), "succeed", Toast.LENGTH_LONG).show();



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
//                        for(int i = 0; i < 10; i ++ ) {
//                            Map<String, Object> map = new HashMap<>();
//                            map.put("pic", R.drawable.mm);
//                            map.put("text1", "增加项"+ i);
//                            dataList.add(map);
//                        }
//                        simpleAdapter.notifyDataSetChanged();//通知UI线程 刷新界面！！！ 很重要  用于更新数据源
                        swiperereshlayout.setRefreshing(false);
                        try {
                            Thread.sleep(500);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                        simpleAdapter.notifyDataSetChanged();

                    }

                }, 2000);
            }
        });
    }


    @Override
    public void onScrollStateChanged(AbsListView absListView, int scrollState) {

        switch (scrollState){
            case  SCROLL_STATE_FLING:
                Log.i("Main", "用户在手指离开屏幕之前，由于用力滑了一下，视图仍靠惯性滑动！");
                Map<String,Object> map = new HashMap<>();
                map.put("pic", R.drawable.mm);
                map.put("text1", "增加项");
                dataList.add(map);
                simpleAdapter.notifyDataSetChanged();//通知UI线程 刷新界面！！！ 很重要  用于更新数据源
                break;
            case SCROLL_STATE_IDLE:
                Log.i("Main", "视图已经停止滑动");
                break;
            case SCROLL_STATE_TOUCH_SCROLL:
                Log.i("Main", "正在滑动，手指没有离开屏幕,视图正在滑动");
                break;
        }
    }

    @Override
    public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {

        if (firstVisibleItem == 0) {
            View firstVisibleItemView = listView.getChildAt(0);
            if (firstVisibleItemView != null && firstVisibleItemView.getTop() == 0) {
                Log.d("ListView", "##### 滚动到顶部 #####");
                //说明现在已经滑动到顶部 再次滑动的话 就是去刷新
            }
        }

        //判断滑动
        if (firstVisibleItem < 1) {
            // 上滑
            Log.d("ss", "上划！！");
        } else if (firstVisibleItem > visibleItemCount) {
            // 下滑
        }

    }

    /**
     * 主要是 position 参数！！ 表示了传过来的item的位置！！
     * @param parent
     * @param view
     * @param position
     * @param id
     */
    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id){

        HashMap<String, Object> map = (HashMap<String, Object>)parent.getItemAtPosition(position);
//        Toast.makeText(this,"position = "+position + "text = " + text1, Toast.LENGTH_SHORT).show();

        Intent intent1 = new Intent(this, OneStory.class);
        String title = (String) map.get("text1");
        String content = (String) map.get("text2");
        Integer pic = (Integer) map.get("pic");
//        intent1.putExtra("title",map.get("text1"));
        intent1.putExtra("title", title);
        intent1.putExtra("content", content);
        intent1.putExtra("pic", pic);
        intent1.putExtra("name",name);
        startActivityForResult(intent1, 3);//3 代表去跳到 一个动态界面！！
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_sample);




        Intent intent11 = getIntent();
        String username = intent11.getStringExtra("username");//得到用户名！！ 去获得name 评论的时候用
        String url = "http://47.100.10.8:8080/mainPage/getName?user_id=" + username;
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
                Log.d("wwwwwwwww", returnString);
                JSONArray returnArray = null;
                JSONObject returnJson = null;
                String msg = null;
                if (ParseJson.getResponseRet(returnString).equals("success")) {
                    //证明请求成功
//                    returnArray = ParseJson.getResponseArrays(returnString);
                    returnJson = ParseJson.getResponseObj(returnString);//得到user
                    name = returnJson.getString("name");//de得到name  评论的时候用
                }
            }

            });


//        listView = findViewById(R.id.listView);
        initView();
//        simpleAdapter.notifyDataSetChanged();
        //1 新建一个数据适配器
        //三个参数 1 上下文 this  2 当前ListView加载的每一个列表项对应的布局文件 3数据源
//        arr_data.add("hah");
//        arr_data.add("xixi");
//        arr_data.add("heihei");
        //2 适配器加载数据源
//        arrayAdapter = new ArrayAdapter<String>(this,android.R.layout.simple_list_item_1, arr_data );
        //simpleAdapter  五个参数 1 上下文 2 data 数据源 （List 里面嵌套Map）每一个map对应listView里面一行
        // 3 resource 列表项布局文件ID
        // 4 from Map中的键名
        // 5 to 绑定数据视图中的ID  与 from 对应
//        dataList = new ArrayList<Map<String, Object>>();
//        simpleAdapter = new SimpleAdapter(this,getData(),R.layout.item,new String[]{"pic", "text1"}, new int[]{R.id.pic,R.id.text1} );
        //3 使用视图加载的适配器
//        listView.setAdapter(arrayAdapter);
//        listView.setAdapter(simpleAdapter);

//        listView.setOnItemClickListener(this);
//        listView.setOnScrollListener(this);
    }

    private  List<Map<String, Object>> getData(){


        String url = "http://47.100.10.8:8080/mainPage/getStory";
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
                if (ParseJson.getResponseRet(returnString).equals("success")) {
                    //证明请求成功
                    returnArray = ParseJson.getResponseArrays(returnString);
                    for (Object each : returnArray) {
                        Map<String, Object> map = new HashMap<String, Object>();
                        Random random = new Random();
                        int randomInt = random.nextInt(7);
                        Integer pic = R.drawable.my1;
                        if (randomInt == 0) {
                            pic = R.drawable.my1;
                        }
                        if (randomInt == 1){
                            pic = R.drawable.my2;
                        }
                        if (randomInt == 2){
                            pic = R.drawable.my3;
                        }
                        if (randomInt == 3) {
                            pic = R.drawable.my4;
                        }
                        if (randomInt == 4) {
                            pic = R.drawable.my5;
                        }
                        if (randomInt == 5) {
                            pic = R.drawable.my6;
                        }
                        if (randomInt == 6) {
                            pic = R.drawable.my7;
                        }
                        map.put("pic", pic);
                        map.put("text1", ((JSONObject)each).getString("story_title"));
                        String content = ((JSONObject)each).getString("story_content");
                        if (content.length() > 50) {
                            String content1 = content.substring(0, 50);
                            map.put("text2", content);
                        }
                        else {
                            map.put("text2", content);
                        }
                        dataList.add(map);
                        Log.d("22",((JSONObject)each).getString("story_title"));
                        Log.d("223", returnString);
                        Log.d("33", dataList + "");
                    }


                    Looper.prepare();
                    Toast.makeText(getApplicationContext(), "succeed", Toast.LENGTH_LONG).show();
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

        return dataList;
    }





    public void doGet(View view) {

        List<String> arr_data1 = new ArrayList<>();
        arr_data1.add("xxx");
        arr_data1.add("yyy");
        arr_data1.add("xxxxxxx");
        arr_data1.add("hhhhh");
        arr_data = arr_data1;
        arrayAdapter = new ArrayAdapter<String>(this,android.R.layout.simple_list_item_1, arr_data );
        listView.setAdapter(arrayAdapter);
//        arr_data = {"hhhha", "xixi"};
//        String url = "http://www.baidu.com";
//        String url = "http://115.28.79.206:8084";
//        String url = "http://115.28.79.206:8080/testPaperManagement/getOnePagePaper";


//        String url = "http://47.100.10.8:8080/login/login?user_id=201611010128&password=123";
//        OkHttpClient okHttpClient = new OkHttpClient();
//        final Request request = new Request.Builder()
//                .url(url)
//                .get()//默认就是GET请求，可以不写
//                .build();
//        Call call = okHttpClient.newCall(request);
//        call.enqueue(new Callback() {
//            @Override
//            public void onFailure(Call call, IOException e) {
//                Log.d("123", "onFailure: " + e.getMessage());
//            }
//
//            @Override
//            public void onResponse(Call call, Response response) throws IOException {
//
//                String returnString = response.body().string();
//                JSONArray resultArray = null;
//                JSONObject resultJson = JSONObject.parseObject(returnString);
//                Log.d("33", ""+resultJson);
//                resultArray = resultJson.getJSONArray("arrays");
//                String str1 = resultJson.getString("msg");
//                Log.d("222", "" + resultArray);
//                Log.d("23", str1);
//
//            }
//        });

    }

}
