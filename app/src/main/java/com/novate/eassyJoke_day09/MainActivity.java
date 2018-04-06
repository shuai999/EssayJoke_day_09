package com.novate.eassyJoke_day09;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.example.administrator.framelibrary.BaseSkinActivity;
import com.hc.baselibrary.http.EngineCallBack;
import com.hc.baselibrary.http.HttpUtils;

public class MainActivity extends BaseSkinActivity implements View.OnClickListener{


    @Override
    protected void initData() {
        HttpUtils.with(this).url("").addParam("" , "").get().execute(new EngineCallBack() {
            @Override
            public void onSuccess(String result) {
                Toast.makeText(MainActivity.this , "请求网络成功" , Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onError(Exception e) {

            }
        });
    }

    @Override
    protected void initView() {
        viewById(R.id.btn_test).setOnClickListener(this);
    }

    @Override
    protected void initTitle() {

    }

    @Override
    protected void setContentView() {
        setContentView(R.layout.activity_main);
    }

    @Override
    public void onClick(View v) {

    }
}
