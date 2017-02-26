package com.example.administrator.okhttp;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.okhttp.Call;
import com.squareup.okhttp.Callback;
import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.Response;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;

public class MainActivity extends AppCompatActivity {

    private ImageView iv;
    private EditText et_number;
    private Handler handler=new Handler(){

        @Override
        public void handleMessage(Message msg) {

            Bitmap kb= (Bitmap) msg.obj;

            iv.setImageBitmap(kb);

        }
    };



    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initView();
    }

    private void initView() {
        et_number= (EditText) findViewById(R.id.et_number);
        iv= (ImageView) findViewById(R.id.iv);

    }

    public void click(View v){
        final String path=et_number.getText().toString().trim();
        if(TextUtils.isEmpty(path)){
            Toast.makeText(getApplicationContext(),"路径不能为空！！！",Toast.LENGTH_SHORT).show();
        }else{
            new Thread(){
             public void run() {
                 try {
                     URL url=new URL("http://d.5857.com/pyy_160718/001.jpg");
                     //创建okHttpClient对象
                     OkHttpClient mOkHttpClient = new OkHttpClient();
                     //创建一个Request
                     final Request request = new Request.Builder().url("http://d.5857.com/pyy_160718/001.jpg").build();
                     //new call
                     Call call = mOkHttpClient.newCall(request);
                     call.enqueue(new Callback() {
                         @Override
                         public void onFailure(Request request, IOException e) {

                         }

                         @Override
                         public void onResponse(Response response) throws IOException {
                             byte[] kb = response.body().bytes();
                             Bitmap bitmap = BitmapFactory.decodeByteArray(kb,0,kb.length);

                             Message msg=new Message();
                             msg.obj=bitmap;
                             handler.sendMessage(msg);
                         }
                     });

                 } catch (MalformedURLException e) {
                     e.printStackTrace();
                 }
             }
            }.start();
        }
    }
}
