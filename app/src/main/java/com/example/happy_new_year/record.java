package com.example.happy_new_year;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Color;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;


import lecho.lib.hellocharts.model.PieChartData;
import lecho.lib.hellocharts.model.SliceValue;
import lecho.lib.hellocharts.view.PieChartView;

public class record extends AppCompatActivity {

    private TextView tv_show;
    private Button bt_dele;
    private static final int
            it_title = Menu.FIRST,
            it_record = Menu.FIRST+1,
            it_main = Menu.FIRST+2,
            it_random_line = Menu.FIRST+3;

    public SQLiteDatabase db;
    public DataBase DataBaseHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_record);

        bt_dele = findViewById(R.id.bt_dele);
        tv_show = findViewById(R.id.tv_show);
        int MoneySum = 0;

        DataBaseHelper = new DataBase(record.this);
        db = DataBaseHelper.getWritableDatabase();

        Cursor c = db.rawQuery("SELECT * FROM MoneyTable", null);    // 查詢tb_name資料表中的所有資料

        int[] money = new int[c.getCount()];
        String[] family = new String[c.getCount()];

        PieChartView pieChartView = findViewById(R.id.chart);
        List<SliceValue> pieData = new ArrayList<>();


        if (c.getCount()>0){    // 若有資料

            c.moveToFirst();    // 移到第 1 筆資料

            do{        // 逐筆讀出資料
                MoneySum += c.getInt(1);

            } while(c.moveToNext());

            c.moveToFirst();
            do{
                Random rnd = new Random();
                float m = c.getInt(1);
                //增加區塊與隨機顏色
                pieData.add(new SliceValue( (m/ MoneySum),Color.argb(255,
                                                                                rnd.nextInt(256),
                                                                                rnd.nextInt(256),
                                                                                rnd.nextInt(256)) )
                .setLabel(c.getString(2)+ ":" +  (int)m +"元" ));

            }while (c.moveToNext());

        }
        PieChartData pieChartData = new PieChartData(pieData);
        pieChartData.setHasLabels(true);
        pieChartData.setHasCenterCircle(true).setCenterText1("紅包錢錢!");
        pieChartView.setPieChartData(pieChartData);

        tv_show.setText(ShowData());


        bt_dele.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                AlertDialog.Builder adB = new AlertDialog.Builder(record.this){};
                adB.setTitle("警告");
                adB.setMessage("你確定要刪除所有資料嗎?");
                adB.setCancelable(false);
                adB.setNegativeButton("確定", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        db.execSQL("delete from MoneyTable" );
                        Toast.makeText(record.this , "已刪除",  Toast.LENGTH_SHORT).show();
                        Intent intent = new Intent(record.this, record.class);
                        startActivity(intent);
                    }
                });
                adB.setPositiveButton("否定" , adBListener);
                adB.show();
            }
        });

    }
    public String ShowData(){

        Cursor c = db.rawQuery("SELECT * FROM MoneyTable", null);    // 查詢tb_name資料表中的所有資料
        int sum = 0;

        if (c.getCount()>0){    // 若有資料
            String str="總共有 "+c.getCount()+"筆紅包錢\n";
            str+="-----\n";

            c.moveToFirst();    // 移到第 1 筆資料
            do{        // 逐筆讀出資料
                int i = c.getInt(1);
                sum+=i;
                str+="Money:"+i+"\t";
                str+="Family:"+c.getString(2)+"\n";
                str+="-----\n";
            } while(c.moveToNext());
            return "全額:"+sum+"\t\t"+str;
        }
        return "no data";
    }


    public boolean onCreateOptionsMenu(Menu menu){


        menu.add(0  , it_title , Menu.NONE , "首頁");
        menu.add(0  , it_main , Menu.NONE , "紀錄");
        menu.add(0  , it_record , Menu.NONE , "顯示");
        menu.add(0  , it_random_line , Menu.NONE , "祝賀話!");

        return super.onCreateOptionsMenu(menu);

    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item){
        String mes = "";
        Intent intent;

        switch (item.getItemId()){

            case it_title:
                intent = new Intent(record.this, index.class);
                startActivity(intent);
                mes = "前往首頁";
                this.finish();
                break;
            case it_main:

                intent = new Intent(record.this, MainActivity.class);
                startActivity(intent);
                mes = "前往紀錄紅包";
                this.finish();
                break;
            case it_record:
                intent = new Intent(record.this, record.class);
                startActivity(intent);
                mes = "前往紅包紀錄";
                this.finish();
                break;
            case it_random_line:
                intent = new Intent(record.this, RandomLine.class);
                startActivity(intent);
                mes = "前往隨機祝賀話";
                this.finish();
                break;

            default:
                return super.onOptionsItemSelected(item);

        }

        Toast.makeText(this , mes,  Toast.LENGTH_SHORT).show();
        return true;
    }

    private DialogInterface.OnClickListener adBListener = new DialogInterface.OnClickListener() {
        @Override
        public void onClick(DialogInterface dialog, int which) {

        }
    };
}
