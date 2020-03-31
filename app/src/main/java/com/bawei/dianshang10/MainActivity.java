package com.bawei.dianshang10;

import android.content.DialogInterface;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {
    //定义
    private EditText edit_search;
    private FlowLayout flowlay;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //初始化
        edit_search = findViewById(R.id.edit_search);
        flowlay = findViewById(R.id.flowlay);
        //点击事件
        flowlay.setOnFlowClickListener(new FlowLayout.OnFlowClickListener() {
            @Override
            public void onFlowClick(String wenben) {
                edit_search.setText(wenben);
            }
        });
    }
    //搜索框事件
    public void search(View view) {
        //获取搜索框文本
        String wenben = edit_search.getText().toString();
        //判断是否不为空
        if(TextUtils.isEmpty(wenben)){
            Toast.makeText(this,"搜索框不能为空！",Toast.LENGTH_LONG).show();
        } else {
            //添加文本到流式布局
            flowlay.addFlow(wenben);
        }
    }
    //清空历史事件
    public void clearSearchHistory(View view) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("提示");
        builder.setCancelable(false);
        builder.setMessage("是否清除搜索历史？");
        builder.setPositiveButton("是", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                //清空布局（清空所有子视图）
                flowlay.removeAllViews();
                Toast.makeText(MainActivity.this,"搜索历史已清空！",Toast.LENGTH_LONG).show();
            }
        });
        builder.setNegativeButton("否", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
            }
        });
        builder.create();
        builder.show();
    }
}
