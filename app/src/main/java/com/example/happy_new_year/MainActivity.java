package com.example.happy_new_year;

import androidx.appcompat.app.AppCompatActivity;

import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class MainActivity extends AppCompatActivity {

    private TextView tv_show;
    private EditText et_money;
    private AutoCompleteTextView actv_family;
    private RadioGroup rg_family;
    private Button bt_check;
    public SQLiteDatabase db;
    public DataBase DataBaseHelper;

    private static final int
            it_title = Menu.FIRST,
            it_record = Menu.FIRST+1,
            it_main = Menu.FIRST+2,
            it_random_line = Menu.FIRST+3;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        FindRes();

        String [] location = {"叔父","嬸母" , "堂兄","堂姐" , "公公","婆婆" , "姑母","姑丈" , "外祖父","外祖母" , "舅父","舅母" , "姨母","姨丈"};
        ArrayAdapter adapter_location = new ArrayAdapter( this,android.R.layout.simple_dropdown_item_1line,location);
        actv_family.setThreshold(1);
        actv_family.setAdapter(adapter_location);
        actv_family.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                actv_family.showDropDown();
            }
        });


        //輸入後RadioGroup就不可使用
        actv_family.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {

                if(actv_family.getText().toString().isEmpty()){
                    for (int i = 0; i < rg_family.getChildCount(); i++) {
                        ((RadioButton) rg_family.getChildAt(i)).setEnabled(true);
                    }
                }else {
                    for (int i = 0; i < rg_family.getChildCount(); i++) {
                        ((RadioButton) rg_family.getChildAt(i)).setEnabled(false);
                    }
                }
            }
        });


        bt_check.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (et_money.getText().toString().isEmpty()) {
                    Toast.makeText(MainActivity.this, "請輸入金額!", Toast.LENGTH_SHORT).show();
                    return;
                }
                if ( isNumeric(et_money.getText().toString())) {
                    Toast.makeText(MainActivity.this, "請勿輸入小數!", Toast.LENGTH_SHORT).show();
                    return;
                }

                if (actv_family.getText().toString().isEmpty() && rg_family.getCheckedRadioButtonId() == -1) {

                    Toast.makeText(MainActivity.this, "請輸入或選取親戚!", Toast.LENGTH_SHORT).show();
                    return;
                }

                DataBaseHelper = new DataBase(MainActivity.this);
                db = DataBaseHelper.getWritableDatabase();
                ContentValues value = new ContentValues();
                value.put("MONEY", Integer.parseInt(et_money.getText().toString()));
                if (!actv_family.getText().toString().isEmpty()) {
                    value.put("FAMILY", actv_family.getText().toString());
                } else {
                    switch (rg_family.getCheckedRadioButtonId()) {
                        case R.id.rb_1:
                            value.put("FAMILY", "父親");
                            break;
                        case R.id.rb_2:
                            value.put("FAMILY", "母親");
                            break;
                        case R.id.rb_3:
                            value.put("FAMILY", "祖父");
                            break;
                        case R.id.rb_4:
                            value.put("FAMILY", "祖母");
                            break;
                        case R.id.rb_5:
                            value.put("FAMILY", "伯父");
                            break;
                        case R.id.rb_6:
                            value.put("FAMILY", "伯母");
                            break;
                    }
                }
                db.insert("MoneyTable", null, value);
                tv_show.setText("成功");
                tv_show.setText(ShowData());
            }
        });



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
                intent = new Intent(MainActivity.this, index.class);
                startActivity(intent);
                mes = "前往首頁";
                this.finish();
                break;
            case it_main:

                intent = new Intent(MainActivity.this, MainActivity.class);
                startActivity(intent);
                mes = "前往紀錄紅包";
                this.finish();
                break;
            case it_record:
                intent = new Intent(MainActivity.this, record.class);
                startActivity(intent);
                mes = "前往紅包紀錄";
                this.finish();
                break;
            case it_random_line:
                intent = new Intent(MainActivity.this, RandomLine.class);
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





    public String ShowData(){

        Cursor c = db.rawQuery("SELECT * FROM MoneyTable", null);    // 查詢tb_name資料表中的所有資料

        if (c.getCount()>0){    // 若有資料
            String str="總共有 "+c.getCount()+"筆紅包錢\n";
            str+="-----\n";

            c.moveToFirst();    // 移到第 1 筆資料
            do{        // 逐筆讀出資料
                str+="Money:"+c.getInt(1)+"\n";
                str+="Family:"+c.getString(2)+"\n";
                str+="-----\n";
            } while(c.moveToNext());
            return str;
        }
        return "no data";
    }


    public boolean isNumeric(String str) {
        Pattern pattern = Pattern.compile("[0-9]+");
        Matcher isNum = pattern.matcher(str);
        if (isNum.matches()) {
            return false;
        }
        return true;
    }


        public void FindRes(){
        et_money = findViewById(R.id.et_money);
        tv_show = findViewById(R.id.tv_show);
        actv_family = findViewById(R.id.actv_family);
        rg_family = findViewById(R.id.rg_family);
        bt_check = findViewById(R.id.bt_check);

    }


}
